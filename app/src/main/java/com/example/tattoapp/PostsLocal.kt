package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tattoapp.DB.SQLLocal
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.Models.Posts
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.Post
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsLocal : AppCompatActivity() {
    private var idLocal:String =""
    val local: Local = Local()
    val posts: Post= Post()

    val SQLUser=SQLUser(this)
    val SQLLocal= SQLLocal(this)

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
        val info = SQLUser.getInformation()
        val infoLocal=SQLLocal.getLocalPerUser(info[0].toString())
        message.idsender=infoLocal[infoLocal.size-2].toString()
        message.idreceiver=info[0].toString()
        val result = message.sendMessage()
        result.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
               showToast("Mensaje Enviado con Exito.")
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


    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}

