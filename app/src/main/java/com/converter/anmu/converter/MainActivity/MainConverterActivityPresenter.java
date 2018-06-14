package com.converter.anmu.converter.MainActivity;

import com.converter.anmu.converter.converterFragment.ConverterContract;

public class MainConverterActivityPresenter implements MainConverterActivityContract.Presenter {

    MainConverterActivityContract.View view;
    ConverterContract.Presenter converterPresenter;

    public MainConverterActivityPresenter(ConverterContract.Presenter presenter){
        converterPresenter = presenter;
    }
    /*
   Binds presenter with its view.
    */

    public void bindView( MainConverterActivityContract.View view){
        this.view = view;
        view.attachTopFragment();
    }

    /*
    unbinds view and nulls view reference
     */
    public void unBindView(){
        view = null;
    }

    public void attachBottomFragment(){
        if(view!=null) {
            view.attachBottomFragment();
        }
    }
    public void detachBottomFragment(){
        if(view!=null) {
            view.detachBottomFragment();
        }
    }

    public void passCode(String code){
        converterPresenter.selectCode(code);
    }

}
