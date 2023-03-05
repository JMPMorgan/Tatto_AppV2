package com.example.tattoapp.RecyclerViews.DataClasses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Local(
    @SerializedName("_id")
    var id:String?=null,
    @SerializedName("user")
    var userCreator:String?=null,
    @SerializedName("status")
    var status:Boolean?=null,
    @SerializedName("location")
    var location:String?=null,
    @SerializedName("img")
    var img:String?=null,
    @SerializedName("weekdays")
    var weekdays:String?=null,
    @SerializedName("schedule")
    var schedule:String?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("msg")
    var msg:String?=null,
    @SerializedName("local")
    @Expose
    var local: Local?=null,
    @SerializedName("locals")
    var locals:List<Local>?=null
)
