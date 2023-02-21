package com.example.tattoapp.DataClasses.ServerResponse

import com.example.tattoapp.DataClasses.Post
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("post")
    var post:Post ?=null,
    @SerializedName("posts")
    var posts:Array<Post> ?=null
)
