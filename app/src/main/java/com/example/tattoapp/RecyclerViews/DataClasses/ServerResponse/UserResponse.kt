package com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse

import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("user")
    var user: User?=null,
    @SerializedName("users")
    var users:Array<User>?=null
)
