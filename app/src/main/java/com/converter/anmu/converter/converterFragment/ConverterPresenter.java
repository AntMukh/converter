package com.converter.anmu.converter.converterFragment;

import android.support.annotation.Nullable;
import android.util.Log;

import com.converter.anmu.converter.ConverterApplication;
import com.converter.anmu.converter.Currency;
import com.converter.anmu.converter.MainActivity.MainConverterActivityPresenter;
import com.converter.anmu.converter.data.RatesRepo;
import com.converter.anmu.converter.utils.Converter;
import com.converter.anmu.converter.utils.MyConsumer;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ConverterPresenter implements ConverterContract.Presenter {
    String TAG = ConverterPresenter.class.getSimpleName();
    @Nullable
    ConverterContract.View view;
    MainConverterActivityPresenter mainConverterActivityPresenter;
    RatesRepo ratesRepo;

    String fromCode = "USD";
    String toCode = "SEK";
    Double fromValue = 1d;

    // shows which currency is going to be changed,  source currency or destination currency
    boolean expectSource;

    public ConverterPresenter(RatesRepo ratesRepo){
        this.ratesRepo = ratesRepo;
    }

    public void bindView(ConverterContract.View view){
        this.view = view;
        //injection
        mainConverterActivityPresenter = ((ConverterApplication)view.getContext().getApplicationContext()).provideMainActivityPresenter();
        performInitialSetup();
    }

    public void unBindView(){
        this.view = null;
    }

    public void performInitialSetup(){
        view.setFromCode(fromCode);
        view.setToCode(toCode);
        view.setFromValue(fromValue+"");
        onConvertClick();
    }

    public void onConvertClick() throws NumberFormatException, NullPointerException{

        ratesRepo.getLastRatesResult(new MyConsumer<Map<String,String>>() {
            @Override
            public void accept(Map<String,String> ratesResult) {
                Log.e(TAG, "accept: ratesResult" + ratesResult.size() );
                double rate1 = Double.parseDouble(ratesResult.get(fromCode));
                double rate2 = Double.parseDouble(ratesResult.get(toCode));
                Log.e(TAG, "accept: rate1"+ rate1 +" rate2:"+rate2 );
                double crossRate = Converter.calculateCrossRate(rate1, rate2);
                double from = 1;// default in case of parsong error
                try {
                    from = Double.parseDouble(view.getFromValue());
                }catch (NumberFormatException numberFormatException){
                    view.setFromValue(from+""); // set default in case of parsing error
                    numberFormatException.printStackTrace();
                }
                Log.e(TAG, "accept: cross: "+ crossRate + " result: "+ crossRate * from );
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                    view.setToValue(numberFormat.format(crossRate * from) + "");
                ;
            }
        });

        //this will cash currency list in case its empty
        ratesRepo.getCurrencyList(new MyConsumer<List<Currency>>() {
            @Override
            public void accept(List<Currency> list) {
                Observable.just(1)
                        .subscribe( a -> {});
            }
        }) ;
    }
    public void selectCode(String code){
        Log.e(TAG, "selectCode() called with: code = [" + code + "]");
        if(expectSource) {
            fromCode = code;
            view.setFromCode(fromCode);
        }else{
            toCode = code;
            view.setToCode(toCode);
        }
    }

    public void onFromClick(){
        expectSource = true;
        mainConverterActivityPresenter.attachBottomFragment();
    }
    public void onToClick(){
        expectSource = false;
        mainConverterActivityPresenter.attachBottomFragment();
    }

    public void onExchangeClick(){
        String temp = fromCode;
        fromCode = toCode;
        toCode = temp;

       view.setFromValue("");
       view.setToValue("");
       view.setFromCode(fromCode);
       view.setToCode(toCode);
    }
}
