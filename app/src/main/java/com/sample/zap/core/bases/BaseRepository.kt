package com.sample.zap.core.bases

import com.sample.zap.core.models.Output
import com.sample.zap.data.remote.ErrorResponse
import com.sample.zap.data.remote.RemoteDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException


abstract class BaseRepository : KoinComponent {

    val remoteDataSource: RemoteDataSource by inject()

    suspend fun <T, R : Any> safeApiCall(
        call: suspend () -> Response<T>,
        transform: (T) -> R,
        default: T,
        error: String
    ): Output<R> {

        return try {
            val response = call()  // Call the suspend function

            if (response.isSuccessful) {
                val body = response.body() ?: default
                Output.Success(transform(body))
            } else {
                val errorResponse = ErrorResponse(message = "Oops .. Something went wrong due to $error")
                Output.Error(statusCode = response.code(), output = errorResponse)
            }
        } catch (exception: IOException) {
            val errorResponse = ErrorResponse(message = "Network error: ${exception.message}")
            Output.Error(statusCode = -1, output = errorResponse)
        } catch (exception: HttpException) {
            val errorResponse = ErrorResponse(message = "HTTP error: ${exception.message()}")
            Output.Error(statusCode = exception.code(), output = errorResponse)
        } catch (exception: Exception) {
            val errorResponse = ErrorResponse(message = "Unknown error: ${exception.message}")
            Output.Error(statusCode = -1, output = errorResponse)
        }
    }
}
