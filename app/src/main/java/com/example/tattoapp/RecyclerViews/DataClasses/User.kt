package com.example.tattoapp.RecyclerViews.DataClasses

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id", alternate = ["_id"])
    var  userid:String?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("lastname")
    var lastname:String?=null,
    @SerializedName("email")
    var email:String?=null,
    @SerializedName("password")
    var password:String?=null,
    @SerializedName("status")
    var status:Boolean?=null,
    @SerializedName("birthday")
    var birthday:String?=null,
    @SerializedName("username")
    var username:String?=null,
    @SerializedName("token")
    var jwt:String?=null,
    @SerializedName("img")
    var file:String?=null
)
