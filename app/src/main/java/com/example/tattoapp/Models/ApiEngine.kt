package com.example.tattoapp.Models

import com.example.tattoapp.utilis.ApiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiEngine {
    companion object{
        fun getApi(): Retrofit {
            val retrofit= Retrofit
                .Builder()
                .baseUrl(ApiConstants.BASEURL_DEV)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
    }
}