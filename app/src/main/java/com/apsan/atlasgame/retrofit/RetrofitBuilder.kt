package com.apsan.atlasgame.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.eu/rest/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val APIClass = retrofit.create(APIMethods::class.java)
}