package com.converter.anmu.converter.data;

import android.content.Context;
import android.widget.Toast;

import com.converter.anmu.converter.BuildConfig;
import com.converter.anmu.converter.Currency;
import com.converter.anmu.converter.data.beans.RatesResult;
import com.converter.anmu.converter.utils.MyConsumer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RatesRepo {
    Long lastUpdate;
    RatesResult lastRatesResult;
    LocalSource localSource;
    NetworkInteractorImpl networkInteractor;
    Context context;

    List<Currency> currencyList;
    String API_KEY = BuildConfig.API_KEY;
    public RatesRepo(NetworkInteractorImpl networkInteractor , LocalSource localSource, Context context){
        this.localSource = localSource;
        this.networkInteractor = networkInteractor;
        this.context = context;
   }

    public void getCurrencyList(MyConsumer consumer) {
        if(currencyList!=null){
            consumer.accept(currencyList);
        }
        if(currencyList==null){
            //try localSource
            String currencies = localSource.getCurrencyList();
            if(currencies != null) {
                List<Currency> list = new ArrayList<>();
                JsonObject currenciesJson = new JsonParser().parse(currencies).getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = currenciesJson.entrySet();
                System.out.print(currenciesJson.entrySet());
                for (Map.Entry<String, JsonElement> entry: entries) {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue().getAsString());
                    list.add(new Currency(entry.getKey(), entry.getValue().getAsString()));
                }
                currencyList = list;
               consumer.accept(currencyList);
            }else
                networkInteractor.getFixerioAPI().loadSymbols(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( symbolsList -> {
                    JsonObject currenciesJson = symbolsList.getSymbols();
                    localSource.saveCurrencyList(symbolsList.getSymbols().toString());
                    Set<Map.Entry<String, JsonElement>> entries = currenciesJson.entrySet();
                    List<Currency> list = new ArrayList<>();
                    System.out.print(currenciesJson.entrySet());
                    for (Map.Entry<String, JsonElement> entry: entries) {
                        System.out.println(entry.getKey());
                        System.out.println(entry.getValue().getAsString());
                        list.add(new Currency(entry.getKey(), entry.getValue().getAsString()));
                    }
                    currencyList = list;
                    consumer.accept(list);
                }, e-> {
                    Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                        }
                );

            }
        }


    public void getLastRatesResult(MyConsumer consumer) {
        long currentTime = System.currentTimeMillis();
        //cashed result
        if(lastRatesResult!=null){
            JsonObject rates = lastRatesResult.getRates();
            Map<String , String> map = new HashMap<>();
            Set<Map.Entry<String, JsonElement>> entries = rates.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue().getAsString());
                map.put(entry.getKey(),entry.getValue().getAsString());
            }

            consumer.accept(map);
            return;
        }else
        // if no cashed data availible ask local repository
        if (lastRatesResult == null) {
            lastRatesResult = localSource.restoreData();
            if (lastRatesResult != null) {
                lastUpdate = lastRatesResult.getLocalUpdateTime();
            }else{
                // cashed lastresult is null
                lastUpdate = new Long(0);
            }
        }
            //if data outdated ask remote
        if (currentTime - lastUpdate > 60 * 60 * 1000) {
                networkInteractor.getFixerioAPI()
                        .loadLatestData(API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ratesResult -> {
                           lastRatesResult = ratesResult;
                           ratesResult.setLocalUpdateTime(System.currentTimeMillis());
                           localSource.saveData(ratesResult);
                          JsonObject rates = ratesResult.getRates();
                          Map<String , String> map = new HashMap<>();
                          Set<Map.Entry<String, JsonElement>> entries = rates.entrySet();
                          for (Map.Entry<String, JsonElement> entry: entries) {
                               System.out.println(entry.getKey());
                               System.out.println(entry.getValue().getAsString());
                                 map.put(entry.getKey(),entry.getValue().getAsString());
                               }


                           consumer.accept(map);
                        }
                        ,e -> {
                            e.printStackTrace();
                            Toast.makeText(context, "network error", Toast.LENGTH_SHORT).show();
                                });

        }
    }
}
