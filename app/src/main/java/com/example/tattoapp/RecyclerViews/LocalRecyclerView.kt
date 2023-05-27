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
import com.example.tattoapp.PostsLocal
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.R
import com.squareup.picasso.Picasso

class LocalRecyclerView (var locals:List<Local>):RecyclerView.Adapter<LocalRecyclerView.ViewHolder>(),Filterable{
    public lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_local,parent,false   )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val local = locals.get(position)
        holder.localDescription!!.text="-Localizacion: ${local.location} " +
                "-Dias de la Semana: ${local.weekdays} " +
                "-Horario:${local.schedule}"
        holder.localName!!.text=local.name
        holder.localID=local.id.toString()
        Picasso.get()
            .load(local.img.toString())
            .into(holder.localImage)
        holder.localPosition=position;
        return
    }

    override fun getItemCount(): Int= locals.size


    fun add(localsAdd: List<Local>){
        locals=localsAdd
        notifyItemInserted(locals.size)
    }


    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val localName = itemView?.findViewById<TextView>(R.id.title_local)
        val localImage = itemView?.findViewById<ImageView>(R.id.imagePost)
        val localDescription = itemView?.findViewById<TextView>(R.id.description_post)
        var localPosition:Int=0
        var localID:String=""
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.local_info->{
                    val activity = Intent(context,PostsLocal::class.java)
                    activity.putExtra("LocalID",this.localID)
                    context.startActivity(activity)
                }
            }
        }

    }



}