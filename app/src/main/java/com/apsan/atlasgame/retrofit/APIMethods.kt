package com.apsan.atlasgame.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIMethods {

    @GET("name/{name}?fullText=true")
    fun checkingIfCountryIsRight(@Path("name") input:String): Call<List<LocationObj>>

    @GET("name/{name}")
    fun searchCountry(@Path("name") char: Char): Call<List<LocationObj>>
}