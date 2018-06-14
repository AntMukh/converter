package com.converter.anmu.converter;

import android.util.Log;

import com.converter.anmu.converter.data.NetworkInteractorImpl;
import com.converter.anmu.converter.data.beans.SymbolsList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class NetworkInteractorTest {

    NetworkInteractorImpl networkInteractor;
    private final CountDownLatch latch = new CountDownLatch(1);


    boolean failed;

    SymbolsList sList;
    boolean symolsListSuccess;
    String API_KEY = BuildConfig.API_KEY;

    @Before
    public void initNetworkInteractorTest(){
        networkInteractor = new NetworkInteractorImpl();
        failed = true;
        sList = null;
        symolsListSuccess = false;
    }

    @Test
    public void checkNetworkInteractorNotNullTest(){
        assertNotNull(networkInteractor);
    }

    @Test
    public void  latestApiRequestTest() throws Exception{

        networkInteractor.getFixerioAPI()
                .loadLatestData(API_KEY)
               // .subscribeOn(Schedulers.io())
                .subscribe(
                        ratesResult -> {

                                assertNotNull(ratesResult);
                                Assert.assertTrue(ratesResult.getSuccess());
                                assertNotNull(ratesResult.getRates());
                                failed = false;
                            latch.countDown();
                            }
                            , e -> {e.printStackTrace();
                            failed = true;
                            latch.countDown();

                        }
                );
        latch.await();
        assertFalse(failed);
    }

    @Test
    public void  latestApiRequestWrongTokenTest() throws Exception{

        networkInteractor.getFixerioAPI()
                .loadLatestData("faketoken")
                .subscribeOn(Schedulers.io())
                .subscribe(
                        ratesResult -> {
                            assertNotNull(ratesResult);
                            Assert.assertTrue(ratesResult.getSuccess());
                            assertNotNull(ratesResult.getRates());
                            failed = false;
                            latch.countDown();
                        }
                        , e -> {e.printStackTrace();
                            failed = true;
                            latch.countDown();

                        }
                );
        latch.await();
        assertTrue(failed);
    }

    @Test
    public void  listApiRequestTest() throws Exception{

        networkInteractor.getFixerioAPI()
                .loadSymbols(API_KEY)
                // .subscribeOn(Schedulers.io())
                .subscribe(
                        symbolsList -> {
                            sList = symbolsList;
                            symolsListSuccess = sList.getSuccess();

                            failed = false;
                            latch.countDown();
                        }
                        , e -> {e.printStackTrace();
                            failed = true;
                            latch.countDown();

                        }
                );
        latch.await();
        assertFalse(failed);
        assertNotNull(sList);
        assertTrue(symolsListSuccess);
    }

    @Test
    public void  listApiRequestWrongTokenTest() throws Exception{

        networkInteractor.getFixerioAPI()
                .loadSymbols("aaaaaaaaaaaaaaa")
                // .subscribeOn(Schedulers.io())
                .subscribe(
                        symbolsList -> {
                            sList = symbolsList;
                            symolsListSuccess = sList.getSuccess();
                            latch.countDown();
                        }
                        , e -> {e.printStackTrace();
                            failed = true;
                            latch.countDown();

                        }
                );
        latch.await();
        assertFalse(symolsListSuccess);
    }

    @Test
    public void  listApiRequestWrongURLTest() throws Exception{
        networkInteractor.BASE_URL = "sdasd";
        networkInteractor.getFixerioAPI()
                .loadSymbols("aaaaaaaaaaaaaaa")
                // .subscribeOn(Schedulers.io())
                .subscribe(
                        symbolsList -> {
                            sList = symbolsList;
                            symolsListSuccess = sList.getSuccess();
                            latch.countDown();
                        }
                        , e -> {e.printStackTrace();
                            failed = true;
                            latch.countDown();

                        }
                );
        latch.await();
        assertFalse(symolsListSuccess);
    }


    @After
    public void afterTest(){
    failed = true;
    }
}
