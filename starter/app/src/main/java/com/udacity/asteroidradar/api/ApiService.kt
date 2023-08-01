package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.network.NetworkPictureContainer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the [Retrofit.Builder] to build a retrofit object using a [MoshiConverterFactory]
 */
private val retrofitMoshi = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(
        // Code from: https://stackoverflow.com/questions/66733053/retrofit-default-query-parameter
        OkHttpClient.Builder().addInterceptor { chain ->
            val url = chain
                .request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }.build()
    )
    .build()

/**
 * Use the [Retrofit.Builder] to build a retrofit object using a [ScalarsConverterFactory]
 */
private val retrofitScalar = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .client(
        // Code from: https://stackoverflow.com/questions/66733053/retrofit-default-query-parameter
        OkHttpClient.Builder().addInterceptor { chain ->
            val url = chain
                .request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }.build()
    )
    .build()

interface ApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") startDate: String, @Query("end_date") endDate: String?): String
    @SuppressWarnings("SpellCheckingInspection")
    @GET("planetary/apod")
    suspend fun getPicture(): NetworkPictureContainer
}

object Api {
    val retrofitServiceMoshi : ApiService by lazy {
        retrofitMoshi.create(ApiService::class.java)
    }
    val retrofitServiceScalar : ApiService by lazy {
        retrofitScalar.create(ApiService::class.java)
    }
}
