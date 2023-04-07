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
import com.example.tattoapp.RecyclerViews.DataClasses.Post
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class UploadPostActivity : AppCompatActivity() {

    private val pickImage=104
    var imgArray:ByteArray?=null
    val post:Post=Post()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        val btnSelectImage = findViewById<Button>(R.id.btnSelectImage)
        val btnUploadPost= findViewById<Button>(R.id.btnPublish)
        btnSelectImage.setOnClickListener {
            val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        btnUploadPost.setOnClickListener {
            uploadPost()
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
        post.userid="641b619dac5f89b8ad46f7fa"
        post.localid="642d08e7a6d3ee4dd5c1752a"

        if(editText.isEmpty()){
            showToast("La descripcion es requerida")
            return;
        }

        post.description=editText

        val response = post.createPost()
        response.enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.e("Prueba POst",response.body().toString())
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