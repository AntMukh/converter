package com.converter.anmu.converter.currencyListFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.converter.anmu.converter.ConverterApplication;
import com.converter.anmu.converter.Currency;
import com.converter.anmu.converter.R;
import com.converter.anmu.converter.data.RatesRepo;
import com.converter.anmu.converter.utils.MyConsumer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.support.v4.util.Preconditions.checkNotNull;


public class CurrencyListFragment extends Fragment
implements CurrencyListContract.View{
    String TAG = CurrencyListFragment.class.getSimpleName();


    private CurrencyListAdapter mAdapter;
    private CurrencyListContract.Presenter mPresenter;
    private ListView listView;

    CurrencyItemListener mItemListener;


    public CurrencyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_currency_list, container, false);

        mItemListener = new CurrencyItemListener() {
            @Override
            public void onClick(Currency clickedCurrency) {

                Log.e(TAG, "onClick: "+ clickedCurrency.getCode()+ " : "+ clickedCurrency.getDescription());
                mPresenter.onItemClick(clickedCurrency);
            }
        };
        listView = root.findViewById(R.id.currency_list);
        mPresenter = ((ConverterApplication)getActivity().getApplication()).provideCurrencyListPresenter();
        mPresenter.bindView(this);
   return root;
    }

    public void onDestroyView(){
        super.onDestroyView();
        mPresenter.unBindView();
    }

    public void setAdapter(List<Currency> list){
        mAdapter = new CurrencyListAdapter(list, mItemListener);
        listView.setAdapter(mAdapter);
    }
    public interface CurrencyItemListener {

        void onClick(Currency clickedTask);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
   }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach() called");
        super.onDetach();

    }

    private static class CurrencyListAdapter extends BaseAdapter {

        private List<Currency> mCurrencys;
        private CurrencyItemListener mItemListener;

        public CurrencyListAdapter(List<Currency> currencies, CurrencyItemListener itemListener) {
            setList(currencies);
            mItemListener = itemListener;
        }

        private void setList(List<Currency> currencies) {
            mCurrencys = currencies;
        }

        @Override
        public int getCount() {
            return mCurrencys.size();
        }

        @Override
        public Currency getItem(int i) {
            return mCurrencys.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.currency_item, viewGroup, false);
            }

            final Currency currency = getItem(i);

            TextView code = rowView.findViewById(R.id.code_tv);
            code.setText(currency.getCode());
            ImageView iv = rowView.findViewById(R.id.flag_iv);
            String currencyCodecode = currency.getCode();
            if(currencyCodecode.equals("SEK"))
                iv.setImageResource(R.drawable.se);
            else if(currencyCodecode.equals("USD"))
                iv.setImageResource(R.drawable.us);
            else if(currencyCodecode.equals("RUR"))
                iv.setImageResource(R.drawable.ru);
            else iv.setImageResource(R.drawable.df);
            TextView name = rowView.findViewById(R.id.name_tv);
            name.setText(currency.getDescription());

            rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mItemListener.onClick(currency);
                }
            });
            return rowView;
        }
    }


}
