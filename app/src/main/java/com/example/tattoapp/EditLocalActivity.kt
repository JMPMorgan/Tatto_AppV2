package com.example.tattoapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.RecyclerViews.DataClasses.Local
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.LocalResponse
import com.squareup.picasso.Picasso
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

class EditLocalActivity : AppCompatActivity() {

    private val pickImage = 102
    var imgArray:ByteArray? =  null
    var btnUploadImg: Button?=null
    val local: Local = Local()
    var txtLocalName:TextView ?=null
    var inputSchedule:EditText ?=null
    var inputWeekdays:EditText ?=null
    var inputLocation:EditText ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_local)
         btnUploadImg= findViewById<Button>(R.id.btnUploadLocalEditImg)
        val btnEditLocal= findViewById<Button>(R.id.btncreateLocalEdit)
        txtLocalName=findViewById<TextView>(R.id.inputLocalEdit)
         inputSchedule = findViewById<EditText>(R.id.inputScheduleEdit)
        inputWeekdays = findViewById<EditText>(R.id.inputWeekdaysEdit)
        inputLocation= findViewById<EditText>(R.id.inputLocationEdit)

        val btnBack = findViewById<Button>(R.id.btn_back_edit)
        btnUploadImg!!.setOnClickListener {
            val gallery= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        btnEditLocal!!.setOnClickListener {
            editLocal()
        }

        btnBack.setOnClickListener {
            val launch = Intent(this,MainActivity::class.java)
            startActivity(launch)
        }
        getInfoLocal()
    }

    fun getInfoLocal(){
        val SQLUser=SQLUser(this)
        val info = SQLUser.getInformation()
        local.userCreator=info[0].toString()
        val response= local.getLocalPerUser()
        response.enqueue(object : Callback<LocalResponse> {
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                if(!response.isSuccessful){
                    val jsonObject= response.errorBody()?.string()?.let{ JSONObject(it) }
                    val msgError=jsonObject?.getString("msg").toString()
                    showToast(msgError)
                    return;
                }
                val localResponse=response.body()?.local
                local.img=localResponse!!.img
                local.id=localResponse!!.id
                txtLocalName=findViewById<TextView>(R.id.inputLocalEdit)
                inputSchedule = findViewById<EditText>(R.id.inputScheduleEdit)
                inputWeekdays = findViewById<EditText>(R.id.inputWeekdaysEdit)
                inputLocation= findViewById<EditText>(R.id.inputLocationEdit)
                txtLocalName!!.text=localResponse!!.name.toString()
                inputSchedule!!.setText(localResponse!!.schedule.toString())
                inputWeekdays!!.setText(localResponse!!.weekdays.toString())
                inputLocation!!.setText(localResponse!!.location.toString())

                val image=findViewById<ImageView>(R.id.imageViewLocalEdit)

                Picasso.get()
                    .load(localResponse!!.img.toString())
                    .into(image)


            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                // TODO("Not yet implemented")
            }
        })
    }

    fun editLocal(){
        val valueSchedule = inputSchedule!!.text.toString()
        val valueWeekdays = inputWeekdays!!.text.toString()
        val valueLocation = inputLocation!!.text.toString()

        if(valueSchedule.isEmpty()){
            showToast("El valor de horarios es requerido")
            return
        }

        if(valueWeekdays.isEmpty()){
            showToast("El valor de la semana es requerido")
            return
        }

        if(valueLocation.isEmpty()){
            showToast("El valor de la localizacion  es requerido")
            return
        }

        if(local.img == null){
            showToast("La imagen es requerida")
            return
        }

        local.schedule=valueSchedule
        local.weekdays=valueWeekdays
        local.location=valueLocation

        Log.e("LOCAL",local.toString())

        val response = local.editLocal()
        response.enqueue(object : Callback<LocalResponse>{
            override fun onResponse(call: Call<LocalResponse>, response: Response<LocalResponse>) {
                Log.e("EDICION DE LOCAL", response.body().toString())
                val launch = Intent(this@EditLocalActivity, MainActivity::class.java)
                startActivity(launch)
            }

            override fun onFailure(call: Call<LocalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        } )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==pickImage){
            val photo=data?.data
            val image = findViewById<ImageView>(R.id.imageViewLocalEdit)
            image.setImageURI(photo)
            val bitmap= (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream)
            imgArray=stream.toByteArray()
            val encodedString:String = Base64.getEncoder().encodeToString(imgArray)
            val strEncodeImage:String= "data:image/png;base64,"+encodedString
            local.img=strEncodeImage
        }
    }

    fun showToast(text:String){
        Toast.makeText(this ,text, Toast.LENGTH_SHORT).show()
    }
}