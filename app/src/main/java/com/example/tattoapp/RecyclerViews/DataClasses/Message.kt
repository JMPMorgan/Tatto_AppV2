package com.example.tattoapp.RecyclerViews.DataClasses

import com.example.tattoapp.ApiRequests.MessageServices
import com.example.tattoapp.Models.ApiEngine
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class Message(
    @SerializedName("_id")
    var id:String?=null,
    @SerializedName("situation")
    var situation:Number ?=null,
    @SerializedName("creation_date")
    var creationDate: String ?=null,
    @SerializedName("sender")
    var sender:User ?=null,
    @SerializedName("receiver")
    var receiver:User?=null,
    @SerializedName("conversation")
    var conversation:String ?=null,
    @SerializedName("message")
    var message:String ?=null,
    @SerializedName("idsender")
    var idsender:String ?=null,
    @SerializedName("idreceiver")
    var idreceiver: String ?=null
){
    private val messgeServices:MessageServices=ApiEngine.getApi().create(MessageServices::class.java)
    fun sendMessage(): Call<MessageResponse> {
        return messgeServices.sendMessage(this)
    }

    fun getConversation(idConversation:String):Call<MessageResponse>{
        return messgeServices.getConversation(idConversation)
    }
}
