package com.example.tattoapp.ApiRequests

import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageServices {
    @Headers("Content-Type:application/json")
    @POST("/api/message")
    fun sendMessage(@Body message:Message):Call<MessageResponse>

    @GET("/api/message/{id}")
    fun getConversation(@Path("id")idConversation:String) : Call<MessageResponse>
}