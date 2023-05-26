package com.example.tattoapp.Fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.example.tattoapp.*
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.squareup.picasso.Picasso
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private  var  binding : View ?= null
    private var user:User ?=null
    private var hasConexion:Boolean=true
    private var uuidUser:String =  "";



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=inflater.inflate(R.layout.fragment_profile,container,false)
        val button=binding!!.findViewById<Button>(R.id.btnRegisterUser)
        val btnCreateLocal = binding!!.findViewById<Button>(R.id.btnCreateLocal)
        val btnEditLocal=binding!!.findViewById<Button>(R.id.btnEditLocal)
        val btnEditUser = binding!!.findViewById<Button>(R.id.btnEditUser)
        val btnAddPost= binding!!.findViewById<Button>(R.id.btnAddPost)
        val btnLogOut = binding!!.findViewById<Button>(R.id.btnLogout)
        btnLogOut.visibility=View.GONE
        button.setOnClickListener {
            val launch = Intent(context,LoginActivity::class.java)
            startActivity(launch)
//            validateNewUser()
        }

        btnCreateLocal.setOnClickListener {
            if(!isInternetConnected(requireContext())){
                showToast("No hay Conexion a Internet")

            }else{
                val launch=Intent(context,CreateLocalActivity::class.java)
                startActivity(launch)
            }

        }

        btnEditLocal.setOnClickListener {
            if(!isInternetConnected(requireContext())){
                showToast("No hay Conexion a Internet")

            }else {
                val launch = Intent(context,EditLocalActivity::class.java)
                startActivity(launch)
            }

        }

        btnEditUser.setOnClickListener {
            if(!isInternetConnected(requireContext())){
                showToast("No hay Conexion a Internet")

            }else {
                val launch = Intent(context,EditUserActivity::class.java)
                startActivity(launch)
            }

        }

        btnAddPost.setOnClickListener {
            if(!isInternetConnected(requireContext())){
                showToast("No hay Conexion a Internet")

            }else {
                val launch = Intent(context,UploadPostActivity::class.java)
                startActivity(launch)
            }

        }

        btnLogOut.setOnClickListener {
            logOut()
        }





        return binding!!.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val SQLUser: SQLUser = SQLUser(requireContext())
        val info = SQLUser.getInformation()
        if(!info.isEmpty() && isInternetConnected(requireContext())){
            Log.e("HOLAAAA","AQUIIIIII")
            uuidUser= info[0].toString()
            loadInfoUser()
            return
        }
        if(info.isEmpty() && !isInternetConnected(requireContext())){
            val btnCreateLocal = binding!!.findViewById<Button>(R.id.btnCreateLocal)
            val btnEditLocal=binding!!.findViewById<Button>(R.id.btnEditLocal)
            val btnEditUser = binding!!.findViewById<Button>(R.id.btnEditUser)
            val btnAddPost= binding!!.findViewById<Button>(R.id.btnAddPost)
            val btnLogOut = binding!!.findViewById<Button>(R.id.btnLogout)
            val btnKeys = binding!!.findViewById<Button>(R.id.btnKeys)
            btnCreateLocal.visibility=View.GONE
            btnEditLocal.visibility=View.GONE
            btnEditUser.visibility=View.GONE
            btnAddPost.visibility=View.GONE
            btnLogOut.visibility=View.GONE
            btnKeys.visibility=View.GONE
            showToast("No hay forma de conectar a internet ni recoletar informacion")
            return
        }

        if(!info.isEmpty() && !isInternetConnected(requireContext()) ){
            //Esta desconectado y  hay info
            return
        }

        if(info.isEmpty() && isInternetConnected(requireContext()) ){
            val launch = Intent(context,LoginActivity::class.java)
            startActivity(launch)
            return
        }


    }

    fun loadInfoUser(){
        val userServices=User()
        val response = userServices.getUser(uuidUser)
        response.enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    Log.e("Prueba",response.toString())
                    Log.e("Prueba2",response.body().toString())
                    val btnEditLocal=binding!!.findViewById<Button>(R.id.btnEditLocal)
                    val btnAddPost= binding!!.findViewById<Button>(R.id.btnAddPost)
                    val btnLogOut = binding!!.findViewById<Button>(R.id.btnLogout)

                    if(!response.isSuccessful){
                        val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) };
                        val msgError= jsonObject?.getString("msg").toString();
                        showToast(msgError)
                        return;
                    }
                    user=response.body()?.user

                    val image=binding!!.findViewById<ImageView>(R.id.image_profile_fragment)
                    val nameUser = binding!!.findViewById<TextView>(R.id.user_name_profile_fragment)
                    nameUser.text="${user!!.name.toString()} ${user!!.lastname.toString()}"
                    Picasso.get()
                        .load(user!!.file.toString())
                        .into(image)
                    // TODO("Falta Guardar los mensajes en la BD de la APP")
                    btnEditLocal.visibility=View.GONE
                    btnAddPost.visibility=View.GONE
                    btnLogOut.visibility=View.VISIBLE
                    if(user!!.hasLocal){
                        val btnCreateLocal=binding!!.findViewById<Button>(R.id.btnCreateLocal)
                        Log.e("Hola","Hola")
                        btnCreateLocal.visibility=View.GONE
                        btnEditLocal.visibility=View.VISIBLE
                        btnAddPost.visibility=View.VISIBLE
                    }



                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Hola","Hola")
                    hasConexion=false

                }
            }
        )
    }

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

//    fun validateNewUser(){
//        val name=binding.findViewById<TextView>(R.id.etNombreUsuario).text.toString()
//        val lastName= binding.findViewById<TextView>(R.id.inputLastName).text.toString()
//        val password = binding.findViewById<TextView>(R.id.inputPassword).text.toString()
//        val confirmPassword= binding.findViewById<TextView>(R.id.inputCPassword).text.toString()
//        val email= binding.findViewById<TextView>(R.id.inputEmail).text.toString()
//        val username= binding.findViewById<TextView>(R.id.inputUsername).text.toString()
//
//        if(name.isEmpty()){
//            Toast.makeText(binding.context,"Nombre de Usuario es requerido",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if(lastName.isEmpty()){
//            Toast.makeText(binding.context,"Apellido de Usuario es requerido",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if(password.isEmpty()){
//            Toast.makeText(binding.context,"Password del Usuario es requerido",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if(password != confirmPassword){
//            Toast.makeText(binding.context,"Contraseñas no Coinciden",Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(binding.context,"El campo Email es requerido y debe ser un email valido",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//
//        if(username.isEmpty()){
//            Toast.makeText(binding.context,"El campo Username es requerido ",Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val user = User(null,name,lastName,email,password,true,null,username,null,null)
//        newUser(user)
//    }


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
        Toast.makeText(binding!!.context ,text, Toast.LENGTH_SHORT).show()
    }

    fun logOut(){
        val SQLUser: SQLUser = SQLUser(requireContext())
        SQLUser.deleteInformation()
        reloadView()
    }

    fun reloadView() {
        binding = null
        view?.let { v ->
            val parent = v.parent as? ViewGroup
            parent?.removeView(v)
        }
        onCreateView(layoutInflater, view?.parent as? ViewGroup, null)
    }

}