package com.example.tattoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLocalActivity : AppCompatActivity() {

    private val pickImage = 101
    var imgArray:ByteArray? =  null
    var btnUploadImg:Button?=null
    val local:Local=Local()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_local)
        val btnCreateLocal= findViewById<Button>(R.id.btncreateLocal)

        btnUploadImg= findViewById(R.id.btnUploadLocalImg)

        btnCreateLocal.setOnClickListener {
            createLocal()
        }

        btnUploadImg!!.setOnClickListener {
            val gallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==pickImage){
            val photo =  data?.data
            val image=findViewById<ImageView>(R.id.imageViewLocal)
            image.setImageURI(photo)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            imgArray =  stream.toByteArray()
            val encodedString:String =  Base64.getEncoder().encodeToString(imgArray)
            val strEncodeImage:String = "data:image/png;base64," + encodedString
            local.img=strEncodeImage
        }
    }

    private fun createLocal(){
        val localName= findViewById<EditText>(R.id.inputLocal).text.toString()
        val schedule = findViewById<EditText>(R.id.inputSchedule).text.toString()
        val weekdays = findViewById<EditText>(R.id.inputWeekdays).text.toString()
        val location = findViewById<EditText>(R.id.inputLocation).text.toString()
//        val imgLocal = findViewById<EditText>(R.id.inputImgLocal).text.toString()

        if(localName.isEmpty()){
            showToast("El Nombre del local es requerido")
        }

        if(schedule.isEmpty()){
            showToast("El Nombre del local es requerido")
        }

        if(weekdays.isEmpty()){
            showToast("El Nombre del local es requerido")
        }

        if(location.isEmpty()){
            showToast("Locacion del Local es requerida")
        }

        if(local.img.toString().isEmpty()){
            showToast("El Nombre del local es requerido")
        }

        local.name=localName
        local.weekdays=weekdays
        local.schedule=schedule
        local.location=location
        //TODO("Get the data of DB")
        local.userCreator="641b619dac5f89b8ad46f7fa"


        Log.e("LOcal",local.toString())

        val createLocal=local.createLocal()

        createLocal.enqueue(object : Callback<LocalResponse> {
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                Log.e("Pruba",response.body().toString())
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
               // TODO("Not yet implemented")
            }
        })

    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}

