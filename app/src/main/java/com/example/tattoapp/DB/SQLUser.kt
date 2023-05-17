package com.example.tattoapp.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SQLUser(context:Context):SQLiteOpenHelper(context,"user",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val datos=
            "CREATE TABLE USER(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LASTNAME TEXT,USER TEXT,BIRTHDATE TEXT,EMAIL TEXT,ID_BACKEND TEXT,PWD TEXT)"
        db!!.execSQL(datos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int , p2: Int) {
        TODO("Not yet implemented")
    }


    @SuppressLint("Range")
    fun getInformation():List<Any>{
        val query="SELECT * FROM USER"
        val db= this.writableDatabase
        if(!isTableExists("USER",db )){
            return listOf()
        }
        val cursor=db.rawQuery("SELECT * FROM USER",null)
        cursor.moveToFirst()
        if(cursor.count <= 0){
            cursor.close()
            return listOf()
        }

        val info:MutableList<Any> = mutableListOf()
        info.add(cursor.getString(cursor.getColumnIndex("ID_BACKEND")))
        info.add(cursor.getString(cursor.getColumnIndex("NAME")))
        info.add(cursor.getString(cursor.getColumnIndex("LASTNAME")))
        info.add(cursor.getString(cursor.getColumnIndex("USER")))
        info.add(cursor.getString(cursor.getColumnIndex("BIRTHDATE")))
        info.add(cursor.getString(cursor.getColumnIndex("EMAIL")))
        cursor.close()
        return info
    }

    fun deleteInformation(){
        val borrar ="DROP TABLE IF EXISTS USER"
        val db= this.writableDatabase
        db!!.execSQL(borrar)
    }

    fun newUser(name:String,lastname:String,user:String,brith:String,email:String,idBackend:String,pwd:String){
        // Info: Esto de momento solo manda llamar cuando
        val data = contentValuesOf();
        data.put("NAME",name )
        data.put("LASTNAME",lastname )
        data.put("USER",user )
        data.put("BIRTHDATE",brith )
        data.put("EMAIL",email )
        data.put("ID_BACKEND",idBackend)
        data.put("PWD",pwd )
        val db= this.writableDatabase
        db.insert("USER",null,data)
        db.close()

    }

    fun editUser(name:String,lastname: String,idBackend: String){
        val contentValues= ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("LASTNAME",lastname)
        this.writableDatabase.update("USER",contentValues,"ID_BACKEND='${idBackend}'",null)
    }

    fun isTableExists(tableName: String, database: SQLiteDatabase): Boolean {
        val cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'", null)
        val tableExists = cursor.count > 0
        cursor.close()
        return tableExists
    }

}