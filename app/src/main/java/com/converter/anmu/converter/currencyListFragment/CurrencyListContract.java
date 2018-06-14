package com.converter.anmu.converter.currencyListFragment;

import android.content.Context;

import com.converter.anmu.converter.utils.BasePresenter;
import com.converter.anmu.converter.utils.BaseView;
import com.converter.anmu.converter.Currency;

import java.util.List;

public interface CurrencyListContract {

        interface View extends BaseView<Presenter> {
            Context getContext();
            void setAdapter(List<Currency> list);
        }

        interface Presenter extends BasePresenter<View> {
           void onItemClick(Currency clickedCurrency);

        }
}
