package com.converter.anmu.converter.data;

import com.converter.anmu.converter.data.beans.RatesResult;
import com.converter.anmu.converter.data.beans.SymbolsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerioAPI {

    @GET("api/latest")
    Observable<RatesResult> loadLatestData(@Query("access_key") String accessToken );

    @GET("api/symbols")
    Observable<SymbolsList> loadSymbols(@Query("access_key") String accessToken );
}
