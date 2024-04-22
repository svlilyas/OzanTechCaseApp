package com.ozantech.ozantechcaseapp.core.network.di

import com.ozantech.ozantechcaseapp.core.network.BuildConfig
import com.ozantech.ozantechcaseapp.core.network.interceptor.ApiRequestInterceptor
import com.ozantech.ozantechcaseapp.core.network.service.CoinService
import com.ozantech.ozantechcaseapp.core.network.utils.DEFAULT_CALL_TIMEOUT_MILLIS
import com.ozantech.ozantechcaseapp.core.network.utils.DEFAULT_CONNECT_TIMEOUT_MILLIS
import com.ozantech.ozantechcaseapp.core.network.utils.DEFAULT_READ_TIMEOUT_MILLIS
import com.ozantech.ozantechcaseapp.core.network.utils.DEFAULT_WRITE_TIMEOUT_MILLIS
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(apiRequestInterceptor: ApiRequestInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiRequestInterceptor)
            .addInterceptor(provideLoggingInterceptor())
            .callTimeout(DEFAULT_CALL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.SERVICE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideCoinService(retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
}
