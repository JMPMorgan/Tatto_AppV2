package com.example.tattoapp.RecyclerViews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.MessagesChatActivity
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.DataClasses.Chats
import com.squareup.picasso.Picasso

class ChatsRecyclerView(var chats:List<Chats>):RecyclerView.Adapter<ChatsRecyclerView.ViewHolder>(),Filterable {
   lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_chats,parent,false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats.get(position)
        Picasso.get()
            .load(chat.user!!.file.toString())
            .into(holder.chatImage)
        holder.chatUsername!!.text="${chat.user!!.name.toString()} ${chat.user!!.lastname.toString()}"
        holder.chatPosition=position
        holder.chatMessage!!.text=chat.lastMessage.toString()
        holder.chatID=chat.id.toString()
        return
    }

    override fun getItemCount(): Int {
       return chats.size
    }

    fun add(chatsAdd:List<Chats>){
        chats=chatsAdd
        notifyItemInserted(chats.size)
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val chatImage = itemView?.findViewById<ImageView>(R.id.imageConversations)
        val chatUsername = itemView?.findViewById<TextView>(R.id.vtUser)
        val chatMessage= itemView?.findViewById<TextView>(R.id.chatMessage)
        var chatPosition:Int=0
        var chatID:String=""
        init {
            itemView.setOnClickListener (this)
        }
        override fun onClick(view: View?) {
            when(view!!.id){
                R.id.chats_info->{
                    val activity= Intent(context, MessagesChatActivity::class.java)
                    activity.putExtra("chatID",chatID)
                    context.startActivity(activity)
                }
            }
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}