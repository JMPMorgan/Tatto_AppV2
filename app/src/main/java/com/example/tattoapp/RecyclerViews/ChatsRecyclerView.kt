package com.example.tattoapp.RecyclerViews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.DataClasses.Chats

class ChatsRecyclerView(var chats:List<Chats>):RecyclerView.Adapter<ChatsRecyclerView.ViewHolder>(),Filterable {
   lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_chats,parent,false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return
    }

    override fun getItemCount(): Int {
       return chats.size
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        init {
            itemView.setOnClickListener (this)
        }
        override fun onClick(view: View?) {
           Log.e("Prueba","Pru=eba")
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}