package com.converter.anmu.converter;

import android.app.Application;
import android.util.Log;


import com.converter.anmu.converter.MainActivity.MainConverterActivityPresenter;
import com.converter.anmu.converter.converterFragment.ConverterPresenter;
import com.converter.anmu.converter.currencyListFragment.CurrencyListPresenter;
import com.converter.anmu.converter.data.LocalSource;
import com.converter.anmu.converter.data.NetworkInteractorImpl;
import com.converter.anmu.converter.data.RatesRepo;

public class ConverterApplication extends Application {
    String TAG = ConverterApplication.class.getSimpleName();

    MainConverterActivityPresenter mainActivityPresenter;
    ConverterPresenter converterPresenter;
    CurrencyListPresenter currencyListPresenter;
    RatesRepo ratesRepo;
    NetworkInteractorImpl networkInteractor;
    LocalSource localSource;

    @Override
    public void onCreate() {
        super.onCreate();

        networkInteractor = new NetworkInteractorImpl();
        localSource = new LocalSource(getApplicationContext());
        ratesRepo = new RatesRepo(networkInteractor, localSource, getApplicationContext());
        converterPresenter = new ConverterPresenter(ratesRepo);
        currencyListPresenter = new CurrencyListPresenter(ratesRepo);
        mainActivityPresenter = new MainConverterActivityPresenter(converterPresenter);
        ;

    }

    public CurrencyListPresenter provideCurrencyListPresenter(){ return currencyListPresenter;}

    public ConverterPresenter provideConverterPresenter() { return converterPresenter;}

    public MainConverterActivityPresenter provideMainActivityPresenter() { return mainActivityPresenter; }

    public RatesRepo provideRatesRepo() { return ratesRepo; }
}
