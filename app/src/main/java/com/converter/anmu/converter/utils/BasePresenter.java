package com.converter.anmu.converter.utils;

public interface BasePresenter<T> {

    /*
    Binds presenter with its view.
     */
    void bindView(T view);

    /*
    unbinds view and nulls view reference
     */
    void unBindView();
}
