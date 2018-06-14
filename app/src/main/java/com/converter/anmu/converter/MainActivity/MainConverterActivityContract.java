package com.converter.anmu.converter.MainActivity;


import com.converter.anmu.converter.utils.BasePresenter;
import com.converter.anmu.converter.utils.BaseView;

public interface MainConverterActivityContract {

    interface View extends BaseView<Presenter> {
        void attachTopFragment();

        void attachBottomFragment();

        void detachBottomFragment();
    }

    interface Presenter extends BasePresenter<View> {

        void attachBottomFragment();
        void detachBottomFragment();
        }

}
