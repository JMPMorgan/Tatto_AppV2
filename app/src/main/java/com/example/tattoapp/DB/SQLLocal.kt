package com.example.tattoapp.DB

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SQLLocal(context: Context): SQLiteOpenHelper(context,"local",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val datos =
            "CREATE TABLE LOCAL(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LOCATION TEXT,SCHEDULE TEXT,WEEKDAYS TEXT,USSER INTERGER,STATUS BOOLEAN,ID_BACKEND TEXT)"
        db!!.execSQL(datos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getInformationLocal(): List<Any> {
        val query = "SELECT * FROM LOCAL"
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM LOCAL", null)
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
        info.add(cursor.getString(cursor.getColumnIndex("NAME")))
        info.add(cursor.getString(cursor.getColumnIndex("LOCATION")))
        info.add(cursor.getString(cursor.getColumnIndex("SCHEDULE ")))
        info.add(cursor.getString(cursor.getColumnIndex("WEEKDAYS")))
        info.add(cursor.getString(cursor.getColumnIndex("URSSER")))
        info.add(cursor.getString(cursor.getColumnIndex("STATUS")))

        return info


    }

    fun deleteInformationLocal(db: SQLiteDatabase?){
        val borrar ="DROP TABLE IF EXISTS LOCAL"
        db!!.execSQL(borrar)
    }

    fun newLoocal(name:String,location:String,weekday:String,schedule:String,usser:Int,status:Boolean,idBackend:String){
        // Info: Esto de momento solo manda llamar cuando
        val data = contentValuesOf();
        data.put("NAME",name )
        data.put("LOCATION",location)
        data.put("ID_BACKEND",idBackend)
        data.put("SCHEDULE",schedule )
        data.put("WEEKDAYS",weekday)
        data.put("USSER",usser )
        data.put("STATUS",status )
        val db= this.writableDatabase
        db.insert("LOCAL",null,data)
        db.close()

    }
}