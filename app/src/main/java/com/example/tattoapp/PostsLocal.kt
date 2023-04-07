package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.tattoapp.Models.Posts
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsLocal : AppCompatActivity() {
    private var idLocal:String =""
    val local: Local = Local()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_local)
        val posts= Posts()
        idLocal= getIntent().getStringExtra("LocalID").toString();
        posts.loadPostsPerLocal(this,findViewById(R.id.recyclerviewpost),idLocal)
        getLocal()
    }

    fun getLocal(){
        local.id=idLocal
        val result = local.getLocal()
        result.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                Log.e("PRUEBA GET LOCAL",response.body().toString())
                val imageLocal = findViewById<ImageView>(R.id.imageLocal)
                val titleLocal = findViewById<TextView>(R.id.title_local)
                Picasso.get()
                    .load(response.body()!!.local!!.img.toString())
                    .into(imageLocal)
                titleLocal.setText(response.body()!!.local!!.name.toString())
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}