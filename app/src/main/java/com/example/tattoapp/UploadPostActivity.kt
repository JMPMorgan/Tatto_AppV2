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
import com.example.tattoapp.RecyclerViews.DataClasses.Post
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class UploadPostActivity : AppCompatActivity() {

    private val pickImage=104
    var imgArray:ByteArray?=null
    val post:Post=Post()
    val SQLUser= SQLUser(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        val btnSelectImage = findViewById<Button>(R.id.btnSelectImage)
        val btnUploadPost= findViewById<Button>(R.id.btnPublish)
        val btnBack =  findViewById<Button>(R.id.btn_back_upl_post)
        btnSelectImage.setOnClickListener {
            val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        btnUploadPost.setOnClickListener {
            uploadPost()
        }

        btnBack.setOnClickListener {
            val launch= Intent(this,MainActivity::class.java)
            startActivity(launch)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==pickImage){
            val photo=data?.data
            val image = findViewById<ImageView>(R.id.imagePostLocal)
            image.setImageURI(photo)
            val bitmap= (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream)
            imgArray=stream.toByteArray()
            val encodedString:String = Base64.getEncoder().encodeToString(imgArray)
            val strEncodeImage:String="data:image/png;base64,"+encodedString
            post.img=strEncodeImage
        }
    }

    fun uploadPost(){
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()
        val userData= SQLUser.getInformation()
        post.userid=userData[0].toString()
        post.localid="642d08e7a6d3ee4dd5c1752a"

        if(editText.isEmpty()){
            showToast("La descripcion es requerida")
            return;
        }

        if(post.img!!.isEmpty()){
            showToast("Debe seleccioanr una imagen")
            return
        }

        post.description=editText

        val response = post.createPost()
        response.enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                showToast("Post Creado con Exito.")
                val launch = Intent(this@UploadPostActivity,MainActivity::class.java)
                startActivity(launch)
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}