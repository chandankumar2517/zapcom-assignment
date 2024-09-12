package com.sample.zap.core.bases

import com.sample.zap.core.models.Output
import com.sample.zap.core.source.network.RemoteSourceManager
import com.sample.zap.data.remote.ErrorResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call

abstract class BaseRepository : KoinComponent {

    val remoteDataSource: RemoteSourceManager by inject()

    fun <T, R : Any> safeApiCall(
        call: Call<T>, transform: (T) -> R, default: T,
        error: String
    ): Output<R> {

        var statusCode: Int
        var errorResponse: ErrorResponse

        return try {
            val response = call.execute()

            statusCode = response.code()


            val errorMessage = "Oops .. Something went wrong due to $error" // Example error message
            errorResponse = ErrorResponse(message = errorMessage)

            if (response.isSuccessful)
                Output.Success(transform((response.body() ?: default)))
            else
                Output.Error(statusCode = statusCode, output = errorResponse)

        } catch (exception: Throwable) {
            statusCode = -1
            errorResponse = ErrorResponse(message = exception.message ?: "Unknown error")

            Output.Error(statusCode = statusCode, output = errorResponse)
        }
    }
}