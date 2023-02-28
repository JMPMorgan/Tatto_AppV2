package com.example.tattoapp.DataClasses.ServerResponse

import com.example.tattoapp.DataClasses.Local
import com.google.gson.annotations.SerializedName

data class LocalResponse(
    @SerializedName("success")
    var success:Boolean,
    @SerializedName("msg")
    var msg:String,
    @SerializedName("local")
    var local: Local?=null,
    @SerializedName("locals")
    var locals:List<Local> ?=null
)
