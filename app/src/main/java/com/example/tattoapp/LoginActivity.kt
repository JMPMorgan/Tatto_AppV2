package com.example.tattoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin=findViewById<Button>(R.id.btn_login)
        val btnSignUp=findViewById<Button>(R.id.btn_signin)
        btnLogin.setOnClickListener {
            LogInUser()
        }

        btnSignUp.setOnClickListener {
            val launch=Intent(this,SignUpActivity::class.java)
            startActivity(launch)
        }
    }

    private fun LogInUser() {
        val email=findViewById<EditText>(R.id.input_email).text.toString()
        val password=findViewById<EditText>(R.id.input_password).text.toString()

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.showToast("El campo Email es requerido y debe ser un email valido")
        }

        if(password.isEmpty()){
            this.showToast("El campo Password es requerido")
        }
        val user=User(null,null,null,email,password,null,null,null,null,null)

        val logIn=user.logIn()

        logIn.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                 users= response.body()?.users!!
                Log.e("USERS",response.body().toString())
                Log.e("USER",response.body().toString())
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Hola","Hola")
            }
        })
    }


    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}