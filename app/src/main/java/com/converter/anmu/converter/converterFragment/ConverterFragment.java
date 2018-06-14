package com.converter.anmu.converter.converterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.converter.anmu.converter.ConverterApplication;
import com.converter.anmu.converter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class ConverterFragment extends Fragment implements ConverterContract.View {
    private String TAG = ConverterFragment.class.getSimpleName();

    @BindView(R.id.from_et)      EditText fromEditText;
    @BindView(R.id.to_et)        EditText toEditText;

    @BindView(R.id.fromCode_tv)  TextView fromCodeTextView;
    @BindView(R.id.toCode_tv)    TextView toCodeTextView;

    @BindView(R.id.fromFlag_iv)  ImageView fromImageView;
    @BindView(R.id.to_flag_iv)   ImageView toImageView;

    @BindView(R.id.convert_bt)   Button convertButton;
    @BindView(R.id.change_iv)    ImageView exchangeImageView;

    private ConverterContract.Presenter presenter;

    public ConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        ButterKnife.bind(this, view);

        convertButton.setOnClickListener(v -> presenter.onConvertClick());
        fromImageView.setOnClickListener(v -> presenter.onFromClick());
        fromCodeTextView.setOnClickListener(v -> presenter.onFromClick());

        toImageView.setOnClickListener(v -> presenter.onToClick());
        toCodeTextView.setOnClickListener(v -> presenter.onToClick());

        exchangeImageView.setOnClickListener(v -> presenter.onExchangeClick());

        convertButton.setOnClickListener(v -> presenter.onConvertClick());

        presenter.bindView(this);

        return view;
    }


    public void onDestroyView(){
        super.onDestroyView();
        presenter.unBindView();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // this is in fact injection
        presenter = ((ConverterApplication) getActivity().getApplication()).provideConverterPresenter();
    }


    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach() called");
        super.onDetach();
    }

    public String getFromValue(){return fromEditText.getText().toString();}
    public void setFromValue(String value){ fromEditText.setText(value);}

    public void setFromCode(String code){
        fromCodeTextView.setText(code);
        if(code.equals("SEK"))
            fromImageView.setImageResource(R.drawable.se);
        else if(code.equals("USD"))
            fromImageView.setImageResource(R.drawable.us);
        else if(code.equals("RUR"))
            fromImageView.setImageResource(R.drawable.ru);
        else fromImageView.setImageResource(R.drawable.df);
    }
    public void setToCode(String code){
        toCodeTextView.setText(code);
        if(code.equals("SEK"))
            toImageView.setImageResource(R.drawable.se);
        else if(code.equals("USD"))
            toImageView.setImageResource(R.drawable.us);
        else if(code.equals("RUR"))
            toImageView.setImageResource(R.drawable.ru);
        else toImageView.setImageResource(R.drawable.df);
    }
    public void setToValue(String value){
        Observable.just(1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(a ->
        toEditText.setText(value));
    }


    public void selectCode(String code){
        Log.e(TAG, "selectCode() called with: code = [" + code + "]");
        presenter.selectCode(code);
    }
}
