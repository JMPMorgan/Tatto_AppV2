package com.example.tattoapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.UserResponse
import com.example.tattoapp.RecyclerViews.DataClasses.User
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.Base64

class EditUserActivity : AppCompatActivity() {

    private val pickImage=103
    var imgArray:ByteArray?=null
    val user:User=User()
    var idUser:String = ""

    val SQLUser= SQLUser(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)
        val btnEditUser = findViewById<Button>(R.id.btnEditUser)
        val btnChangePhotoUser=findViewById<Button>(R.id.btnUploadImgUser)
        val btnBack = findViewById<Button>(R.id.btn_back_edt_user)
        btnEditUser.setOnClickListener {
            editUser()
        }

        btnChangePhotoUser.setOnClickListener {
            val gallery= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        btnBack.setOnClickListener {
            val launch = Intent(this, MainActivity::class.java)
            startActivity(launch)
        }
        val data = SQLUser.getInformation()
        idUser=data[0].toString()
        loadUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==pickImage){
            val photo=data?.data
            val image = findViewById<ImageView>(R.id.imageViewUserEdit)
            image.setImageURI(photo)
            val bitmap= (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream)
            imgArray=stream.toByteArray()
            val encodedString:String = Base64.getEncoder().encodeToString(imgArray)
            val strEncodeImage:String="data:image/png;base64,"+encodedString
            user.file=strEncodeImage
        }
    }

    private fun editUser() {
        val inputName= findViewById<EditText>(R.id.inputNameUser).text.toString()
        val inputLastName= findViewById<EditText>(R.id.inputLastNameUser).text.toString()

        if(inputName.isEmpty()){
            showToast("El  Nombre es requerido")
            return;
        }

        if(inputLastName.isEmpty()){
            showToast("El  Apellido es requerido")
            return;
        }

        user.lastname=inputLastName
        user.name=inputName
        user.userid=idUser
        val result = user.editUser()
        result.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                SQLUser.editUser(user.name!!,user.lastname!!,user.userid!!)
                showToast("Usuario Editado con Exito.")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })
    }

    fun loadUser(){
        val inputName= findViewById<EditText>(R.id.inputNameUser)
        val inputLastName= findViewById<EditText>(R.id.inputLastNameUser)
        val imageViewProfile = findViewById<ImageView>(R.id.imageViewUserEdit)
        user.userid=idUser
        val result = user.getUser(user.userid!!)
        result.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                inputName.setText(response.body()!!.user!!.name)
                inputLastName.setText(response.body()!!.user!!.lastname)

                Picasso.get()
                    .load(response.body()!!.user!!.file.toString())
                    .into(imageViewProfile)

                user.file=response.body()!!.user!!.file .toString()
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })

    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}