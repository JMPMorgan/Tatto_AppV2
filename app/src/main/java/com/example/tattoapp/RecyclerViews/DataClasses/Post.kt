package com.example.tattoapp.RecyclerViews.DataClasses

import com.example.tattoapp.ApiRequests.PostsServices
import com.example.tattoapp.Models.ApiEngine
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call

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
){

    private val postsServices:PostsServices=ApiEngine.getApi().create(PostsServices::class.java)
    fun createPost(): Call<PostResponse> {
        return postsServices.createPost(this)
    }
}
