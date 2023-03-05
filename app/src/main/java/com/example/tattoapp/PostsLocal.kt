package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tattoapp.Models.Posts

class PostsLocal : AppCompatActivity() {
    private var idLocal:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_local)
        val posts= Posts()
        idLocal= getIntent().getStringExtra("LocalID").toString();
        posts.loadPostsPerLocal(this,findViewById(R.id.recyclerviewpost),idLocal)
    }
}