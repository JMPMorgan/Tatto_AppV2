package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.tattoapp.Models.Posts
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
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
        val btnSendMessage = findViewById<Button>(R.id.btnSendMessage)
        val posts= Posts()
        idLocal= getIntent().getStringExtra("LocalID").toString();
        posts.loadPostsPerLocal(this,findViewById(R.id.recyclerviewpost),idLocal)
        getLocal()

        btnSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    fun sendMessage() {
        val message: Message= Message()
        message.message="Hola que tal quisiera informacion de tus servicios"
        message.idsender="6431c75ee12f94212a63c0ef"
        message.idreceiver="641b619dac5f89b8ad46f7fa"
        val result = message.sendMessage()
        result.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
               Log.e("Mensaje de response",response.body().toString())
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e("ERROR",t.toString())
            }

        })
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