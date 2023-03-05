package com.example.tattoapp.ApiRequests

import retrofit2.Call
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface PostsServices {
    @Headers("Content-Type:application/json")
    @GET("/api/posts")
    fun getAllPosts():Call<PostResponse>
    @GET("/api/posts/{id}")
    fun getPost(@Path("id")id:String):Call<PostResponse>
    @GET("/api/local/post/{id}")
    fun getPostsPerLocal(@Path("id") id: String):Call<PostResponse>

}