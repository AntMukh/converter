package com.converter.anmu.converter.currencyListFragment;

import android.util.Log;

import com.converter.anmu.converter.ConverterApplication;
import com.converter.anmu.converter.Currency;
import com.converter.anmu.converter.MainActivity.MainConverterActivityPresenter;
import com.converter.anmu.converter.data.RatesRepo;
import com.converter.anmu.converter.utils.MyConsumer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CurrencyListPresenter implements CurrencyListContract.Presenter{

    private CurrencyListContract.View view;
    MainConverterActivityPresenter mainConverterActivityPresenter;
    private RatesRepo ratesRepo;

    public CurrencyListPresenter(RatesRepo ratesRepo){
        this.ratesRepo = ratesRepo;
    }
    public void bindView(CurrencyListContract.View view){
        this.view = view;
        mainConverterActivityPresenter = ((ConverterApplication)view.getContext().getApplicationContext()).provideMainActivityPresenter();

        ratesRepo.getCurrencyList(new MyConsumer<List<Currency>>() {
            @Override
            public void accept(List<Currency> list) {
                if(list.size() == 0){
                }
                Observable.just(1)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe( a -> view.setAdapter(list));
            }
        }) ;
    }

    public void unBindView(){
        this.view = null;
    }

    public void onItemClick(Currency clickedCurrency){
        mainConverterActivityPresenter.detachBottomFragment();
        mainConverterActivityPresenter.passCode(clickedCurrency.getCode());
    }
}
