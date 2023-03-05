package com.example.tattoapp.ApiRequests

import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @Headers("Content-Type: application/json")
    @POST("/api/user/")
    fun createNewUser(@Body user: User): Call<UserResponse>
    @GET("/api/user/{id}")
    fun getUserInfo(@Path("id")id:String): Call<UserResponse>
    @PUT("/api/user/{id}")
    fun updateUser(@Path("id")id:String, @Body user: User): Call<UserResponse>
    @DELETE("/api/user/{id}")
    fun deleteUser(@Path("id")id:String): Call<UserResponse>
    @GET("/api/user/")
    fun getUsers(): Call<UserResponse>
}