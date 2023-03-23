package com.example.tattoapp.RecyclerViews.DataClasses

import com.example.tattoapp.ApiRequests.ChatsServices
import com.example.tattoapp.Models.ApiEngine
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.ChatsResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import java.util.Date

data class Chats(
    @SerializedName("_id")
    var id:String?=null,
    @SerializedName("status")
    var status:Boolean?=null,
    @SerializedName("creation_date")
    var creationDate:Date?=null,
    @SerializedName("user")
    var user:User?=null,
    @SerializedName("artist")
    var artist:User?=null,
    @SerializedName("last_message")
    var lastMessage:String?=null
){
    private val chatsServices:ChatsServices=ApiEngine.getApi().create(ChatsServices::class.java)

    fun getConversations(id: String): Call<ChatsResponse> {
        return this.chatsServices.getConversations(id)
    }
}
