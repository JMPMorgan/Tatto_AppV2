package com.example.tattoapp.RecyclerViews.DataClasses

import android.util.Log
import com.example.tattoapp.ApiRequests.LocalServices
import com.example.tattoapp.ApiRequests.UserService
import com.example.tattoapp.Models.ApiEngine
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.google.gson.annotations.SerializedName
import retrofit2.*


data class User(
    @SerializedName("id", alternate = ["_id"])
    var  userid:String?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("lastname")
    var lastname:String?=null,
    @SerializedName("email")
    var email:String?=null,
    @SerializedName("password")
    var password:String?=null,
    @SerializedName("status")
    var status:Boolean?=null,
    @SerializedName("birthday")
    var birthday:String?=null,
    @SerializedName("username")
    var username:String?=null,
    @SerializedName("token")
    var jwt:String?=null,
    @SerializedName("img")
    var file:String?=null,
    @SerializedName("hasLocal")
    var hasLocal:Boolean=false
){

    private val userServices: UserService = ApiEngine.getApi().create(UserService::class.java)
    public fun createUser():Call<UserResponse>{
        val response:Call<UserResponse> =this.userServices.createNewUser(this)
//        response.enqueue(object :Callback<UserResponse>{
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
        return response;
    }

    fun logIn():Call<UserResponse>{
        val response:Call<UserResponse> =this.userServices.logInUser(this)
        return response
    }

    public  fun getUsers(): Call<UserResponse>  {
//        var users:List<User>;
        val response: Call<UserResponse> = this.userServices.getUsers()
        return response;
    }

    fun getUser(id:String):Call<UserResponse>{
        return this.userServices.getUserInfo(id)

    }

    fun editUser():Call<UserResponse>{
        return this.userServices.updateUser(this.userid!!,this)
    }
}
