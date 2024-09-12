package com.sample.zap.data.remote

import retrofit2.Response
import retrofit2.http.GET


interface RemoteDataSource {

    @GET("/b/5BEJ")
   suspend fun productslist(): Response<List<ProductResponses>>

}