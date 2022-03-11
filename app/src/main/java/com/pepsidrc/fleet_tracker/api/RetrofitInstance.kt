package com.pepsidrc.fleet_tracker.api


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//        private const val BASE_URL = "https://drcadjust.pepsidrc.ae/api/v1/"
          private const val BASE_URL ="http://drccustsales.pepsidrc.ae:3000/api/v1/"


//    private val client = OkHttpClient.Builder()
//        .addInterceptor(HeaderInterceptor())
//        .build()

//    private val moshi = Moshi.Builder()
//        .add(Date::class.java, Rfc3339DateJsonAdapter())
//        .addLast(KotlinJsonAdapterFactory())
//        .build()

//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }

        private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

//    private val moshi = Moshi.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val api: FleetApi by lazy {
        retrofit.create(FleetApi::class.java)
    }


}
