package com.example.tattoapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tattoapp.CreateLocalActivity
import com.example.tattoapp.LoginActivity
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.example.tattoapp.SignUpActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var  binding:View
    private var user:User ?=null
    private var hasConexion:Boolean=true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO("Falta cargar la la BD interna para saber si ya hizo login")
        //?Esto se tiene que quitar cuando este listo lo de la BD

        binding=inflater.inflate(R.layout.fragment_profile,container,false)
        val button=binding.findViewById<Button>(R.id.btnRegisterUser)
        val btnCreateLocal = binding.findViewById<Button>(R.id.btnCreateLocal)
        button.setOnClickListener {
            val launch = Intent(context,LoginActivity::class.java)
            startActivity(launch)
//            validateNewUser()
        }

        btnCreateLocal.setOnClickListener {
            val launch=Intent(context,CreateLocalActivity::class.java)
            startActivity(launch)
        }

        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInfoUser()
    }

    fun loadInfoUser(){
        val userServices=User()
        val response = userServices.getUser("641b619dac5f89b8ad46f7fa")
        response.enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    Log.e("Prueba",response.toString())
                    Log.e("Prueba2",response.body().toString())
                    if(!response.isSuccessful){
                        val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) };
                        val msgError= jsonObject?.getString("msg").toString();
                        showToast(msgError)
                        return;
                    }
                    user=response.body()?.user

                    val image=binding.findViewById<ImageView>(R.id.image_profile_fragment)

                    Picasso.get()
                        .load(user!!.file.toString())
                        .into(image)
                    // TODO("Falta Guardar los mensajes en la BD de la APP")

                    if(user!!.hasLocal){
                        val btnCreateLocal=binding.findViewById<Button>(R.id.btnCreateLocal)
                        Log.e("Hola","Hola")
                        btnCreateLocal.visibility=View.GONE
                    }

                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Hola","Hola")
                    hasConexion=false

                }
            }
        )
    }

    fun validateNewUser(){
        val name=binding.findViewById<TextView>(R.id.etNombreUsuario).text.toString()
        val lastName= binding.findViewById<TextView>(R.id.inputLastName).text.toString()
        val password = binding.findViewById<TextView>(R.id.inputPassword).text.toString()
        val confirmPassword= binding.findViewById<TextView>(R.id.inputCPassword).text.toString()
        val email= binding.findViewById<TextView>(R.id.inputEmail).text.toString()
        val username= binding.findViewById<TextView>(R.id.inputUsername).text.toString()

        if(name.isEmpty()){
            Toast.makeText(binding.context,"Nombre de Usuario es requerido",Toast.LENGTH_SHORT).show()
            return
        }

        if(lastName.isEmpty()){
            Toast.makeText(binding.context,"Apellido de Usuario es requerido",Toast.LENGTH_SHORT).show()
            return
        }

        if(password.isEmpty()){
            Toast.makeText(binding.context,"Password del Usuario es requerido",Toast.LENGTH_SHORT).show()
            return
        }

        if(password != confirmPassword){
            Toast.makeText(binding.context,"Contraseñas no Coinciden",Toast.LENGTH_SHORT).show()
            return
        }
        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(binding.context,"El campo Email es requerido y debe ser un email valido",Toast.LENGTH_SHORT).show()
            return
        }


        if(username.isEmpty()){
            Toast.makeText(binding.context,"El campo Username es requerido ",Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(null,name,lastName,email,password,true,null,username,null,null)
        newUser(user)
    }


     fun newUser(user:User){
         val prueba= user.getUsers()
         prueba.enqueue(object : Callback<UserResponse> {
             override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                 users= response.body()?.users!!
                 Log.e("USERS",response.body().toString())
             }

             override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                 Log.e("Hola","Hola")
             }

         })
    }
    fun showToast(text:String){
        Toast.makeText(binding.context ,text, Toast.LENGTH_SHORT).show()
    }

}