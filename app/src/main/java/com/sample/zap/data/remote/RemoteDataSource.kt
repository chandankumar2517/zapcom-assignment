package com.sample.zap.data.remote

import retrofit2.Call
import retrofit2.http.GET


interface RemoteDataSource {

    @GET("/b/5BEJ")
    fun productslist(): Call<List<ProductResponses>>

}