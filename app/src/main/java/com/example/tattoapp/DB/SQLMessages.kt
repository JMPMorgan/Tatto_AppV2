package com.example.tattoapp.DB

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SQLMessages(context: Context): SQLiteOpenHelper(context,"message",null,1)  {
    override fun onCreate(db: SQLiteDatabase?) {
        val datos=
            "CREATE TABLE MESSAGE(ID INTEGER PRIMARY KEY AUTOINCREMENT,CONTENT TEXT,SITUATION BOOLEAN,CREATIONDATE TEXT,ID_RECEIVER INTEGER,ID_BACKEND TEXT)"
        db!!.execSQL(datos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getInformationMessage(id1:Int,id2:Int):List<Any> {
        val query = "SELECT * FROM MESSAJE WHERE ID="+ id1+"AND ID_RECEIVER ="+id2
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM MESSAJE WHERE ID="+ id1+"AND ID_RECEIVER ="+id2, null)
        cursor.moveToFirst()
//        val data = )
//        Log.e("CURSOS",cursor.getString(cursor.getColumnIndex("ID_BACKEND")))
        Log.d("INFO", cursor.count.toString())
        if (cursor.count <= 0) {
            cursor.close()
            return listOf()
        }

        val info: MutableList<Any> = mutableListOf()
        info.add(cursor.getString(cursor.getColumnIndex("ID_BACKEND")))
        info.add(cursor.getString(cursor.getColumnIndex("ID")))
        info.add(cursor.getString(cursor.getColumnIndex("ID_RECEIVER")))
        info.add(cursor.getString(cursor.getColumnIndex("CONTENT")))
        info.add(cursor.getString(cursor.getColumnIndex("SITUATION")))
        info.add(cursor.getString(cursor.getColumnIndex("CREATIONDATE")))


        return info
    }

    fun deleteInformationMessagew(db: SQLiteDatabase?){
        val borrar ="DROP TABLE IF EXISTS MESSAJE"
        db!!.execSQL(borrar)
    }

    fun newUserMessage(id:Int,id_receiver:Int,content:String ,creationDate:String,situation:Boolean,idBackend:String){
        // Info: Esto de momento solo manda llamar cuando
        val data = contentValuesOf();
        data.put("ID",id )
        data.put("ID_RECEIVER",id_receiver )
        data.put("CONTENT", content)
        data.put("ID_BACKEND",idBackend)
        data.put("SITUATION", creationDate)
        data.put("CREATIONDATE", situation)

        val db= this.writableDatabase
        db.insert("MESSAGE",null,data)
        db.close()

    }
}