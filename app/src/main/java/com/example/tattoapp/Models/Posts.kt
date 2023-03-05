package com.example.tattoapp.Models

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.ApiRequests.PostsServices
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import com.example.tattoapp.RecyclerViews.PostsRecyclerView
import retrofit2.*


class Posts {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter:PostsRecyclerView
    private val postsServices:PostsServices=ApiEngine.getApi().create(PostsServices::class.java)

    public fun loadPostsPerLocal(context: Context,idRVPosts:RecyclerView,idLocal:String){
        val response:Call<PostResponse> = this.postsServices.getPostsPerLocal(idLocal)
        response.enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val posts=response.body()!!.posts!!
                adapter = PostsRecyclerView(context,posts)
                recyclerView=idRVPosts
                recyclerView.layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                recyclerView.adapter=adapter
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("Hola 2",t.toString())
            }

        })
    }
}