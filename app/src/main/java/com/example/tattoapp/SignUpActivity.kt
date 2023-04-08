package com.example.tattoapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.tattoapp.Fragments.DatePickerFragment
import com.example.tattoapp.Interfaces.DateSelected
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(),DateSelected {

    private val pickImage = 100
    var imgArray:ByteArray? =  null
    var btnDatePicker:Button ?= null
    var btnPickImage:Button?=null
    val user:User=User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnDatePicker=findViewById<Button>(R.id.btnPickDate)
        btnPickImage=findViewById(R.id.btnPickImage)
        val btnSignUp = findViewById<Button>(R.id.btnRegisterUser)

        btnDatePicker!!.setOnClickListener {
            showDatePicker();
        }

        btnPickImage!!.setOnClickListener {
            val gallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        btnSignUp.setOnClickListener {
            validateNewUser()
        }

    }

    private fun showDatePicker() {
        val datePickerFragment= DatePickerFragment(this)
        datePickerFragment.show(supportFragmentManager,"datePicker")
    }


    fun validateNewUser(){
        val name=findViewById<TextView>(R.id.etNombreUsuario).text.toString()
        val lastName= findViewById<TextView>(R.id.inputLastName).text.toString()
        val password = findViewById<TextView>(R.id.inputPassword).text.toString()
        val confirmPassword= findViewById<TextView>(R.id.inputCPassword).text.toString()
        val email= findViewById<TextView>(R.id.inputEmail).text.toString()
        val username= findViewById<TextView>(R.id.inputUsername).text.toString()

        if(name.isEmpty()){
            this.showToast("Nombre de Usuario es requerido")
            return
        }

        if(lastName.isEmpty()){
            this.showToast("Apellido de Usuario es requerido")
            return
        }

        if(password.isEmpty()){
            this.showToast("Password del Usuario es requerido")

            return
        }

        if(password != confirmPassword){
            this.showToast("Contraseñas no Coinciden")
            return
        }
        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.showToast("El campo Email es requerido y debe ser un email valido")
            return
        }


        if(username.isEmpty()){
            this.showToast("El campo Username es requerido ")
            return
        }
        user.name=name
        user.lastname=lastName
        user.email=email
        user.password=password
        user.status=true
        user.username=username
//        user = User(null,name,lastName,email,password,true,null,username,null,null)
        newUser(user)
    }

    fun newUser(user:User){
        val prueba= user.createUser()
        prueba.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                 users= response.body()?.users!!
                Log.e("USERS",response.body().toString())
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
                Log.e("ERROR",t.toString())
                Log.e("Hola","Hola")
            }

        })
    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }



    override fun receiveData(year: Int, month: Int, dayOfMonth: Int) {
        val calendar= GregorianCalendar()
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.YEAR,year)

        val viewFormatter = SimpleDateFormat("dd-MM-YYYY")
        var viewFormattedDate= viewFormatter.format(calendar.time)
        user.birthday=viewFormattedDate
        btnDatePicker!!.setText(viewFormattedDate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==pickImage){
            val photo =  data?.data
            val image=findViewById<ImageView>(R.id.signUpPhoto)
            image.setImageURI(photo)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            imgArray =  stream.toByteArray()
            val encodedString:String =  Base64.getEncoder().encodeToString(imgArray)
            val strEncodeImage:String = "data:image/png;base64," + encodedString
            user.file=strEncodeImage
        }
    }



}


