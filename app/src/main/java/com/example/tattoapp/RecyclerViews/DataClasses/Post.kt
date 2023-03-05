package com.example.tattoapp.RecyclerViews.DataClasses

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("_id")
    var id:String?=null,
    @SerializedName("user")
    var userid:String?=null,
    @SerializedName("description")
    var description:String?=null,
    @SerializedName("local")
    var localid:String?=null,
    @SerializedName("img")
    var img:String?=null
)
