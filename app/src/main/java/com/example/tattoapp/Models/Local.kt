package com.example.tattoapp.Models

import android.util.Log
import com.example.tattoapp.ApiRequests.LocalServices
import com.example.tattoapp.DataClasses.ServerResponse.LocalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Local {

    private val localServices:LocalServices= ApiEngine.getApi().create(LocalServices::class.java)
    public fun getLocals(){
        val response:Call<LocalResponse> = this.localServices.getLocals()
        val prueba=response.request().body()
        Log.e("ERROR",prueba.toString())
        Log.e("HOLA",prueba.toString())
        response.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                Log.e("Hola",response.body().toString())
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                Log.e("Hola 2",t.toString())
            }

        })
    }
}