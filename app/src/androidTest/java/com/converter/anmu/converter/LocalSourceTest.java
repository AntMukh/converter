package com.converter.anmu.converter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.converter.anmu.converter.data.LocalSource;
import com.converter.anmu.converter.data.beans.RatesResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class LocalSourceTest {
    LocalSource localSource;
    @Before
    public void before() {
        Context context = InstrumentationRegistry.getTargetContext();
        localSource = new LocalSource(context);

    }

    @Test
    public void WriteAndReadRatesTest(){
        RatesResult ratesResult = new RatesResult();
        ratesResult.setBase("USD");
        ratesResult.setDate("today");
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse("{\"a\": 23}").getAsJsonObject();
        ratesResult.setRates(o);
        localSource.saveData(ratesResult);

        RatesResult restoredRatesResult = localSource.restoreData();
        Assert.assertEquals(ratesResult.getBase(), restoredRatesResult.getBase());
        Assert.assertEquals(ratesResult.getDate(), restoredRatesResult.getDate());
        Assert.assertEquals(ratesResult.getRates(), restoredRatesResult.getRates());

    }

    @Test
    public void WriteAndReadCurrencyListTest(){
       String strToSave = "Json String Of Supported Currenies";
       localSource.saveCurrencyList(strToSave);

       String readString = localSource.getCurrencyList();
       Assert.assertEquals(strToSave, readString);
    }

    @Test
    public void WriteAndReadCurrencyListFailedTest(){
        String strToSave = "Json String Of Supported Currenies";
        localSource.saveCurrencyList(strToSave);

        String readString = localSource.getCurrencyList();
        Assert.assertNotEquals(strToSave+"12", readString);
    }
}
