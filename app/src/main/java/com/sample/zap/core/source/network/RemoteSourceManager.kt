package com.sample.zap.core.source.network

import retrofit2.Retrofit

class RemoteSourceManager(private val retrofit: Retrofit) {
    fun <T> makeRemoteCall(apiSource: Class<out T>): T {
        return retrofit.create(apiSource)
    }
}