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
            "CREATE TABLE LOCAL(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LOCATION TEXT,SCHEDULE TEXT,WEEKDAYS TEXT,USER TEXT,STATUS BOOLEAN,ID_BACKEND TEXT)"
        db!!.execSQL(datos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    @SuppressLint("Range")
    fun getLocalPerUser(idUser:String): List<Any> {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM LOCAL WHERE USER = '${idUser}' LIMIT 1",null)
        val info:MutableList<Any> = mutableListOf()
        cursor.moveToFirst()

        info.add(cursor.getString(cursor.getColumnIndex("ID")))
        info.add(cursor.getString(cursor.getColumnIndex("NAME")))
        info.add(cursor.getString(cursor.getColumnIndex("LOCATION")))
        info.add(cursor.getString(cursor.getColumnIndex("WEEKDAYS")))
        info.add(cursor.getString(cursor.getColumnIndex("NAME")))
        info.add(cursor.getString(cursor.getColumnIndex("STATUS")))
        info.add(cursor.getString(cursor.getColumnIndex("USER")))
        info.add(cursor.getString(cursor.getColumnIndex("ID_BACKEND")))

        cursor.close()
        return info
    }

    @SuppressLint("Range")
    fun getInformationLocal(): List<Any> {
        val query = "SELECT * FROM LOCAL"
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM LOCAL", null)
        cursor.moveToFirst()
        if (cursor.count <= 0) {
            cursor.close()
            return listOf()
        }
        val info: MutableList<Any> = mutableListOf()
        info.add(cursor.getString(cursor.getColumnIndex("ID_BACKEND")))
        info.add(cursor.getString(cursor.getColumnIndex("NAME")))
        info.add(cursor.getString(cursor.getColumnIndex("LOCATION")))
        info.add(cursor.getString(cursor.getColumnIndex("SCHEDULE")))
        info.add(cursor.getString(cursor.getColumnIndex("WEEKDAYS")))
        info.add(cursor.getString(cursor.getColumnIndex("USER")))
        info.add(cursor.getString(cursor.getColumnIndex("STATUS")))

        return info


    }

    fun deleteInformationLocal(){
        val borrar ="DROP TABLE IF EXISTS LOCAL"
        val db= this.writableDatabase
        db!!.execSQL(borrar)
    }

    fun newLocal(name:String,location:String,weekday:String,schedule:String,user:String,status:Boolean,idBackend:String){
        // Info: Esto de momento solo manda llamar cuando
        val data = contentValuesOf();
        data.put("NAME",name )
        data.put("LOCATION",location)
        data.put("ID_BACKEND",idBackend)
        data.put("SCHEDULE",schedule )
        data.put("WEEKDAYS",weekday)
        data.put("USER",user )
        data.put("STATUS",status )
        val db= this.writableDatabase
        db.insert("LOCAL",null,data)
        Log.e("LOCAL DATA",db.toString())
        db.close()

    }

    fun isTableExists(tableName: String, database: SQLiteDatabase): Boolean {
        val cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'", null)
        val tableExists = cursor.count > 0
        cursor.close()
        Log.e("PRUEBA TABLA",tableExists.toString())
        return tableExists
    }
}