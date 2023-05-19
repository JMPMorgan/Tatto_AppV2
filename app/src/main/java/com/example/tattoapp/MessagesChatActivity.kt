package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.example.tattoapp.RecyclerViews.MessagesRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesChatActivity : AppCompatActivity() {
    private var idConverstion:String =""
    lateinit var adapter:MessagesRecyclerView
     lateinit var  recyclerView: RecyclerView
    var listMessages:List<Message> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_chat)
        idConverstion= getIntent().getStringExtra("chatID").toString()
        loadMessage()
        setUpRecyclerView()
    }

    fun setUpRecyclerView(){
        adapter= MessagesRecyclerView(listMessages)
        recyclerView=findViewById(R.id.conRecycler)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter=adapter
    }

    fun loadMessage(){
        val messages= Message()
        val result = messages.getConversation(idConverstion)
        result.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}