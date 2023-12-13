package com.example.test.API

import android.telecom.Call
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class News(
        var id : Int,
        var name : String?,
        var description : String?,
        var price : Int,
        var image : String?
        )

interface Interface
{
    @GET("api/News")
    fun GetNews() : retrofit2.Call<MutableList<News>>
}

public object RetrofitConnection
{
    fun getRetrofit() : Interface
    {
        return Retrofit.Builder()
            .baseUrl("https://iis.ngknn.ru/NGKNN/МамшеваЮС/MedicMadlab/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(Interface::class.java)
    }
}