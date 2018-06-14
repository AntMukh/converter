package com.converter.anmu.converter.MainActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.converter.anmu.converter.ConverterApplication;
import com.converter.anmu.converter.R;
import com.converter.anmu.converter.converterFragment.ConverterFragment;
import com.converter.anmu.converter.currencyListFragment.CurrencyListFragment;


public class MainConverterActivity extends AppCompatActivity
implements MainConverterActivityContract.View {
    String TAG = MainConverterActivity.class.getSimpleName();
    MainConverterActivityContract.Presenter mainConverterActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_converter);
        mainConverterActivityPresenter = ((ConverterApplication)getApplication()).provideMainActivityPresenter();

    }

    public void onResume(){
       super.onResume();
       mainConverterActivityPresenter.bindView(this);
    }

    public void onPause(){
        super.onPause();
        mainConverterActivityPresenter.unBindView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(TAG, "onDestroy() called");
    }

    public void attachTopFragment(){
        ConverterFragment converterFragment =
                (ConverterFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (converterFragment == null) {
            // Create a converterFragnemt instance
            converterFragment = new ConverterFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragmentContainer, converterFragment);
            transaction.commit();
        }
    }

    public void attachBottomFragment(){
        Log.e(TAG, "attachBottomFragment() called");
        detachBottomFragment();
        CurrencyListFragment currencyListFragment =
                (CurrencyListFragment) getSupportFragmentManager().findFragmentById(R.id.bottomFragmentContainer);
        if(currencyListFragment == null){
            // Create a currencyListFragnemt instance
            currencyListFragment = new CurrencyListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.bottomFragmentContainer, currencyListFragment);
            transaction.commit();
        }
    }

    public void detachBottomFragment(){
        Log.e(TAG, "detachBottomFragment() called");
        CurrencyListFragment currencyListFragment =
                (CurrencyListFragment) getSupportFragmentManager().findFragmentById(R.id.bottomFragmentContainer);
        if(currencyListFragment != null){
            // Create a currencyListFragnemt instance
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(currencyListFragment);
            transaction.commit();
        }
    }

}
