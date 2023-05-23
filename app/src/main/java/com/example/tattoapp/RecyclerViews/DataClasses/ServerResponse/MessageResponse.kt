package com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse

import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("messages")
    var messages : List<Message> ?=null
)
