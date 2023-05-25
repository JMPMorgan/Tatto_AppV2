package com.example.tattoapp.Models

import android.util.Log
import com.example.tattoapp.ApiRequests.LocalServices
import com.example.tattoapp.DB.SQLLocal
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.example.tattoapp.RecyclerViews.LocalRecyclerView
import retrofit2.*
import com.example.tattoapp.RecyclerViews.DataClasses.Local as LocalData

class Local {
    var locals :List<LocalData> = listOf()
    private val localServices:LocalServices= ApiEngine.getApi().create(LocalServices::class.java)
    fun loadLocals(localRecyclerView: LocalRecyclerView,SQLLocal:SQLLocal) {

        val response:Call<LocalResponse> = this.localServices.getLocals()
        response.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                locals=response.body()?.locals!!
                Log.e("PRUEBASA",locals.toString())
                localRecyclerView.add(locals)
                SQLLocal.insertLocals(locals)
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                Log.e("Hola 2",t.toString())
            }

        })
    }


}