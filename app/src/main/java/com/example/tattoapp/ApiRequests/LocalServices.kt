package com.example.tattoapp.ApiRequests

import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import retrofit2.Call
import retrofit2.http.*

interface LocalServices {

    @GET("/api/local")
    fun getLocals(): Call<LocalResponse>
    @GET("/api/local/{id}")
    fun getLocal(@Path("id")id:String):Call<LocalResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/local/")
    fun createLocal(@Body local: Local):Call<LocalResponse>
    @GET("/api/local/user/{id}")
    fun getLocalPerUser(@Path("id")id:String):Call<LocalResponse>

    @PUT("/api/local/{id}")
    fun updateLocal(@Path("id")id:String,@Body local:Local):Call<LocalResponse>

}