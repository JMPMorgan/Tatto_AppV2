package com.example.tattoapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import org.json.JSONObject
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
        val btnSendMessage = findViewById<Button>(R.id.btnSendMessageLocals)
        val posts= Posts()
        idLocal= getIntent().getStringExtra("LocalID").toString();
        posts.loadPostsPerLocal(this,findViewById(R.id.recyclerviewpost),idLocal)
        btnSendMessage.setOnClickListener {
            sendMessage()
        }
        if(!isInternetConnected(this)){
            showToast("No hay conexion a internet, por lo tanto no se puede conseguir la informacion del Local")
            return
        }
        getLocal()



    }

    fun sendMessage() {
        if(isInternetConnected(this )){
            showToast("No hay conexion a internet, por lo tanto no se enviar la informacion al Local")
            return
        }
        val message: Message= Message()
        message.message="Hola que tal quisiera informacion de tus servicios"
        val info = SQLUser.getInformation()
        val infoReceiver=SQLLocal.getLocalPerID(idLocal)
        message.idsender=info[0].toString()
        message.idreceiver=infoReceiver.toString()
        if(message.idsender==message.idreceiver){
            showToast("No puedes enviar mensajes a tu propio Local")
            Log.e("MESSAGE","No puedes enviar mensajes a tu propio Local")
            return
        }
        val result = message.sendMessage()
        result.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
               showToast("Mensaje Enviado con Exito.")
                val launch = Intent(this@PostsLocal, MainActivity::class.java)
                startActivity(launch)
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
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
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

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}

