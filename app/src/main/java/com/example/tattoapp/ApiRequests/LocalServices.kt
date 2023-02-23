package com.example.tattoapp.ApiRequests

import com.example.tattoapp.DataClasses.Local
import com.example.tattoapp.DataClasses.ServerResponse.LocalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LocalServices {

    @GET("/api/local")
    fun getLocals(): Call<LocalResponse>
    @GET("/api/local/{id}")
    fun getLocal(@Path("id")id:String):Call<LocalResponse>
    @POST("/api/local/")
    fun createLocal(@Body local: Local):Call<LocalResponse>
    @GET("/api/local/user/{id}")
    fun getLocalPerUser(@Path("id")id:String):Call<LocalResponse>

}