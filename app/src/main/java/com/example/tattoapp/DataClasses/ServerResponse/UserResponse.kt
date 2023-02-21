package com.example.tattoapp.DataClasses.ServerResponse

import com.example.tattoapp.DataClasses.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("user")
    var user:User?=null,
    @SerializedName("users")
    var users:Array<User>?=null
)
