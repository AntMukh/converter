package com.converter.anmu.converter.converterFragment;

import android.content.Context;

import com.converter.anmu.converter.utils.BasePresenter;
import com.converter.anmu.converter.utils.BaseView;

public interface ConverterContract {
    interface View extends BaseView<Presenter> {
        Context getContext();

        String getFromValue();
        void setFromValue(String value);
        void setToValue(String value);
        void setFromCode(String code);
        void setToCode(String code);
    }

    interface Presenter extends BasePresenter<View> {
        void onConvertClick();
        void selectCode(String code);
        void onFromClick();
        void onToClick();
        void onExchangeClick();

    }
}
