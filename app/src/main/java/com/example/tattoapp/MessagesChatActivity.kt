package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.example.tattoapp.RecyclerViews.MessagesRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesChatActivity : AppCompatActivity() {
    private var imageSender :String=""
    private var lastNameSender:String=""
    private  var nameSender:String=""
    private  var idReceiver:String=""
    private  var idSender:String=""
    private var idConverstion:String =""
    lateinit var adapter:MessagesRecyclerView
     lateinit var  recyclerView: RecyclerView
    var listMessages:List<Message> = listOf()
    val SQLUser= SQLUser(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_chat)
        val btnSendMessage = findViewById<Button>(R.id.buttonSndMessage)
        idConverstion= getIntent().getStringExtra("chatID").toString()
        btnSendMessage.setOnClickListener {
            sendMessage()
        }
        val data = SQLUser.getInformation()
        nameSender=data[2].toString()
        lastNameSender= data[3].toString()
        idSender=data[0].toString()
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
                listMessages= response.body()!!.messages!!
                adapter.add(listMessages)
                if(response.body()!!.messages!![0].sender!!.userid!!.equals(idSender)){
                    idReceiver=response.body()!!.messages!![0].receiver!!.userid!!
                }else{
                    idReceiver=response.body()!!.messages!![0].sender!!.userid!!
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun sendMessage(){
        val txtMessage = findViewById<EditText>(R.id.editTextMessage)
        if(txtMessage.text.isEmpty()){
            return;
        }
        val message=Message(null,null,null,User(null,nameSender,lastNameSender),null,null,txtMessage.text.toString(),idSender,idReceiver)
        val request= message.sendMessage()
        request.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                txtMessage.setText("")
                Log.e("listMessages",listMessages.toString())
                val prueba = listMessages.toMutableList()
                prueba.add(listMessages.size, message)
                adapter.add(prueba)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}