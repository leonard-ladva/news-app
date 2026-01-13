package com.example.newsapp.data.remote

import com.example.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://newsapi.org/v2/"

   private val apiKeyInterceptor = Interceptor { chain ->
       val originalRequest = chain.request()
       val originalUrl = originalRequest.url

       val newUrl = originalUrl.newBuilder()
           .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
           .build()

       val newRequest = originalRequest.newBuilder()
           .url(newUrl)
           .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val api: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}