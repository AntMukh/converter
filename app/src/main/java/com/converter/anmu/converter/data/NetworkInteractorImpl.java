package com.converter.anmu.converter.data;

import com.converter.anmu.converter.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkInteractorImpl {
    String TAG = NetworkInteractorImpl.class.getSimpleName();
    public String BASE_URL = BuildConfig.BASE_URL;

    private Retrofit retrofit;
    private FixerioAPI fixerioAPI;

    public NetworkInteractorImpl() {
        initRetrofitClient();
    }

    private void initRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        fixerioAPI = retrofit.create(FixerioAPI.class);
    }

    public FixerioAPI getFixerioAPI() {
        return fixerioAPI;
    }

}
