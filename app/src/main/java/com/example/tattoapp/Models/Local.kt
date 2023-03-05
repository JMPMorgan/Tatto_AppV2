package com.example.tattoapp.Models

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.ApiRequests.LocalServices
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.example.tattoapp.RecyclerViews.LocalRecyclerView
import retrofit2.*
import com.example.tattoapp.RecyclerViews.DataClasses.Local as LocalData

class Local {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: LocalRecyclerView
    private val localServices:LocalServices= ApiEngine.getApi().create(LocalServices::class.java)
    public  fun loadLocals(context:Context,idRVLocal:RecyclerView) {
        val response:Call<LocalResponse> = this.localServices.getLocals()
        response.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                val locals=response.body()?.locals!!
                adapter= LocalRecyclerView(context,locals)
                recyclerView=idRVLocal
                recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                recyclerView.adapter=adapter
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                Log.e("Hola 2",t.toString())
            }

        })
    }
}