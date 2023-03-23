package com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse

import com.example.tattoapp.RecyclerViews.DataClasses.Chats
import com.google.gson.annotations.SerializedName

data class ChatsResponse(
    @SerializedName("msg")
    var msg:String,
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("conversations")
    var chats:List<Chats>?=null
)
