package com.example.tattoapp.DataClasses.ServerResponse

import com.example.tattoapp.DataClasses.Local
import com.google.gson.annotations.SerializedName

data class LocalResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("local")
    var post: Local?=null,
    @SerializedName("posts")
    var posts:Array<Local> ?=null
)
