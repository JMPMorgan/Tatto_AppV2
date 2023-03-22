package com.example.tattoapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tattoapp.LoginActivity
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.example.tattoapp.SignUpActivity
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var  binding:View
    private lateinit var button:Button


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=inflater.inflate(R.layout.fragment_profile,container,false)
        button=binding.findViewById(R.id.btnRegisterUser)
        button.setOnClickListener {
            val launch = Intent(context,LoginActivity::class.java)
            startActivity(launch)
//            validateNewUser()
        }
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.setOnClickListenerR)
//       view.setOnClickListener{
//           when(view.id){
//               R.id.btnRegisterUser->{
//                   Log.e("PREUBAAAAA","HOLA COMO ESTAS WUWU")
//               }
//           }
//       }
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

}