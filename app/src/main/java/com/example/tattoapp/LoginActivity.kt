package com.example.tattoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tattoapp.DB.SQLLocal
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import org.json.JSONObject
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
            return;
        }

        if(password.isEmpty()){
            this.showToast("El campo Password es requerido")
            return;
        }
        val user=User(null,null,null,email,password,null,null,null,null,null)

        val logIn=user.logIn()

        logIn.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(!response.isSuccessful){
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) };
                    val msgError= jsonObject?.getString("msg").toString();
                    showToast(msgError)
                    return;
                }
                val user =response.body()?.user
                val userDataSQL=SQLUser(this@LoginActivity)
                userDataSQL.deleteInformation()
                val data = userDataSQL.getInformation()
                if(data.isEmpty()){
                    userDataSQL.onCreate(userDataSQL.writableDatabase)
                    userDataSQL.newUser(user?.name.toString(),user?.lastname.toString(),user?.username.toString(),user?.birthday.toString(),user?.email.toString(),user?.userid.toString(),user?.password.toString())
                }
                loadLocalInfoPerUser(user?.userid.toString())

                // TODO("Falta Guardar los mensajes en la BD de la APP")

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Hola","Hola")
            }
        })
    }


    private fun loadLocalInfoPerUser(idUser:String){
        val local = Local()
        local.userCreator=idUser
        val result = local.getLocalPerUser()
        result.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                val localResponse = response.body()!!.local
                if(localResponse==null){
                    val launch = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(launch)
                    return
                }
                val localDataSQL= SQLLocal(this@LoginActivity)
                localDataSQL.deleteInformationLocal()
                if(!localDataSQL.isTableExists("LOCAL",localDataSQL.writableDatabase)){
                    localDataSQL.onCreate(localDataSQL.writableDatabase)
                    localDataSQL
                        .newLocal(localResponse!!.name.toString(),
                                  localResponse!!.location.toString(),
                                  localResponse.weekdays.toString(),
                                  localResponse.schedule.toString(),
                                  idUser,
                                  true,
                                  localResponse!!.id.toString())
                }
                val launch = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(launch)
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {

            }
        })
    }


    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}