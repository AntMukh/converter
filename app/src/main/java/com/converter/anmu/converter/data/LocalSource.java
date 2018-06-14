package com.converter.anmu.converter.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.converter.anmu.converter.data.beans.RatesResult;
import com.google.gson.Gson;
import com.google.gson.JsonParser;


public class LocalSource {

    String TAG = LocalSource.class.getSimpleName();
    public String RATES_NAME = "rates";
    public String SUPPORTD_CUR_NAME = "supported";
    Context context;


    public LocalSource(Context context) {
        this.context = context;
    }

    public void saveData(RatesResult ratesResult) {
        Gson gson = new Gson();
        String ratesJsonString = gson.toJson(ratesResult);
        Log.e(TAG, "saveData: " + ratesJsonString);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putString(RATES_NAME, ratesJsonString)
                .apply();

    }

    public RatesResult restoreData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String ratesJsonString = sharedPreferences.getString(RATES_NAME,"");
        if(ratesJsonString.equals(""))
            return null;
        Gson gson = new Gson();
        RatesResult ratesResult = gson.fromJson(ratesJsonString, RatesResult.class);
        String json = gson.toJson(ratesResult.getRates());
        JsonParser parcer = new JsonParser();
        parcer.parse(json);
       return ratesResult;
    }

    public String getCurrencyList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String currencies = sharedPreferences.getString( SUPPORTD_CUR_NAME, "");
        if (currencies.equals("")) {
            return null;
        } else{
           return currencies;
        }
    }

    public void saveCurrencyList(String str){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putString( SUPPORTD_CUR_NAME, str)
                .apply();
    }
}
