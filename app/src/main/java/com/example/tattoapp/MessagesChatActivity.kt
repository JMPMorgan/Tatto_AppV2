package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.DB.SQLMessages
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.example.tattoapp.RecyclerViews.MessagesRecyclerView
import org.json.JSONObject
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
    val SQLMessages=SQLMessages(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_chat)
        val btnSendMessage = findViewById<Button>(R.id.buttonSndMessage)
        idConverstion= getIntent().getStringExtra("chatID").toString()
        btnSendMessage.setOnClickListener {
            sendMessage()
        }
        val data = SQLUser.getInformation()
        Log.e("DATAAAAAAAAAA",data.toString())
        nameSender=data[1].toString()
        lastNameSender= data[2].toString()
        idSender=data[0].toString()
        val dataSQL= SQLMessages.getMessages()
        Log.e("dataSQL",dataSQL.toString())
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
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                SQLMessages.deleteInformationMessagew(SQLMessages.writableDatabase)
                SQLMessages.onCreate(SQLMessages.writableDatabase)
                listMessages= response.body()!!.messages!!
                SQLMessages.insertMessages(listMessages)
                adapter.add(listMessages)
                if(response.body()!!.messages!![0].sender!!.userid!!.equals(idSender)){
                    idReceiver=response.body()!!.messages!![0].receiver!!.userid!!
                }else{
                    idReceiver=response.body()!!.messages!![0].sender!!.userid!!
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                showToast("Error al cargar los mensajes del servidor")
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
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                txtMessage.setText("")
                SQLMessages.newUserMessage(message)
                val newList = listMessages.toMutableList()
                newList.add(listMessages.size, message)
                adapter.add(newList)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                showToast("Error al cargar los mensajes del servidor")
            }

        })

    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}