package com.example.tattoapp.ApiRequests

import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.ChatsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ChatsServices {
    @Headers("Content-Type: application/json")
    @GET("/api/conversations/{id}")
    fun getConversations(@Path("id")id:String): Call<ChatsResponse>

}