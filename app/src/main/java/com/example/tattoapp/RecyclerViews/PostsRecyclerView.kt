package com.example.tattoapp.RecyclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tattoapp.DataClasses.Post
import com.example.tattoapp.R


//TODO: Put LocalsRecylcerView

class PostsRecyclerView(val context:Context, val posts:List<Post>):RecyclerView.Adapter<PostsRecyclerView.ViewHolder>(),Filterable {
    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ViewHolder{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_posts,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder,position:Int) {
        val post = posts.get(position)
        holder.itemId

        return;
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
//        val localName=itemView?.findViewById<>()
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}