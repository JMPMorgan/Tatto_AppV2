package com.example.tattoapp.RecyclerViews
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.DB.SQLMessages
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.MessageResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesRecyclerView(var messages: List<Message>): RecyclerView.Adapter<MessagesRecyclerView.ViewHolder>(), Filterable {
    lateinit var context: Context


    fun getMessages(idChat:String) {
        val message = Message()
        message.id=idChat
        val result = message.sendMessage()
        result.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                val messageResponse=response.body()!!.msg
                //            val messageDataSQL=SQLMessages(this@ConRecyclerView)
                //                   messageDataSQL.deleteInformationMessagew()
                //             if(!messageDataSQL.isTableExists("MESSAJE",messageDataSQL.writableDatabase)){
                //                messageDataSQL.onCreate(messageDataSQL.writableDatabase)
                //                messageDataSQL.newUserMessage(
                //                   message.id!!.toInt(),
                //                   message.receiver.toString(),
                //                   message.sender.toString(),
                //                  message.message.toString(),
                //               message.creationDate.toString(),
                //                 message.situation.toString(),
                //              message.conversation.toString(),



                //       )
                //        }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

            }
        })




    }

    fun add(messagesList:List<Message>){
        messages=messagesList
        notifyItemInserted(messages.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_messages,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages.get(position)
        holder.message!!.text = message.message!!
        holder.userName!!.text= "${message.sender!!.name} ${message.sender!!.lastname}"
//        Picasso.get()
//            .load(message.sender!!.file)
//            .into(holder.profileImage)
        holder.messagePosition=position
        return
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val userName = itemView?.findViewById<TextView>(R.id.userNameMessage)
        val message= itemView?.findViewById<TextView>(R.id.messageUser)
        var messagePosition: Int=0
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?) {

        }

    }


}