package com.sample.zap.core.di

import ProductListViewModel
import com.sample.zap.core.Configuration
import com.sample.zap.core.source.network.RemoteSourceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sample.zap.data.repository.ProductRepositoryImpl
import com.sample.zap.domain.repository.ProductRepository
import com.sample.zap.domain.usecase.ProductUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val repoModule = module {
    single<ProductRepository> { ProductRepositoryImpl() }
}


val viewModelModule = module {
    viewModel { ProductListViewModel(productUseCase = get()) }
}


    val useCaseModule: Module = module {
        single { ProductUseCase(productRepository = get()) }
    }


val networkModule = module {

    single { provideMoshi() } // Add this line

    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Configuration.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun provideClient(interceptor: Interceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder().build()
            val request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single { provideRetrofit(get(), get()) } // Pass Moshi as dependency
    single { provideClient(get(), get()) }
    single { provideInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { RemoteSourceManager(get()) }
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

val databaseModule = module {

}

val sharedPrefModule = module {
    single {
        androidContext().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
    }
}