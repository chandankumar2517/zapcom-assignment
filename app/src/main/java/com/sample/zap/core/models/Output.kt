package com.sample.zap.core.models

import com.sample.zap.data.remote.ErrorResponse


sealed class Output<out T> {

    data class Success<out T : Any>(val output: T) : Output<T>()
    data class Error(val statusCode: Int, val output: ErrorResponse) : Output<Nothing>()
    data class Exception(val e: kotlin.Exception): Output<Nothing>()
    class Loading() : Output<Nothing>()

}

