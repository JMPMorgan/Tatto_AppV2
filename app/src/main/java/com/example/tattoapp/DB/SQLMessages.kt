package com.example.tattoapp.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.tattoapp.RecyclerViews.DataClasses.Message
import com.example.tattoapp.RecyclerViews.DataClasses.User
import java.time.LocalDate

class SQLMessages(context: Context): SQLiteOpenHelper(context,"message",null,1)  {
    override fun onCreate(db: SQLiteDatabase?) {
        val datos=
            "CREATE TABLE MESSAGES (ID INTEGER PRIMARY KEY AUTOINCREMENT,CONTENT TEXT,SITUATION BOOLEAN,CREATIONDATE TEXT,ID_RECEIVER STRING,ID_SENDER STRING,ID_BACKEND TEXT)"
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
        val borrar ="DROP TABLE IF EXISTS MESSAGES"
        db!!.execSQL(borrar)
    }

    fun newUserMessage(message: Message){
        // Info: Esto de momento solo manda llamar cuando
        val data = contentValuesOf();
        data.put("ID_RECEIVER",message.idreceiver)
        data.put("ID_SENDER",message.idsender)
        data.put("CONTENT", message.message)
        data.put("ID_BACKEND",message.id)
        data.put("SITUATION", 0)
        data.put("CREATIONDATE", LocalDate.now().toString())

        val db= this.writableDatabase
        db.insert("MESSAGES",null,data)
        db.close()

    }


    @SuppressLint("Range")
    fun getMessages(): List<Message> {
        val columns = arrayOf("ID","ID_RECEIVER", "ID_SENDER","CONTENT","ID_BACKEND","SITUATION","CREATIONDATE")

        val cursor = this.writableDatabase.query("MESSAGES", columns, null, null, null, null, null)

        val messages = mutableListOf<Message>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("ID"))
            val text = cursor.getString(cursor.getColumnIndex("CONTENT"))
            val idReceiver = cursor.getString(cursor.getColumnIndex("ID_RECEIVER"))
            val idSender = cursor.getString(cursor.getColumnIndex("ID_SENDER"))
            val idBackend = cursor.getString(cursor.getColumnIndex("ID_BACKEND"))
            val situation = cursor.getInt(cursor.getColumnIndex("SITUATION"))
            val creationDate = cursor.getString(cursor.getColumnIndex("CREATIONDATE"))
            val message = Message(idBackend, situation,creationDate,User(idSender),User(idReceiver),null,text,idSender,idReceiver)
            messages.add(message)
        }

        cursor.close()
        val messagesAa:List<Message> = messages
        return messagesAa
    }

    fun insertMessages(messages:List<Message>){
        for(message in messages){
            val contentValues=ContentValues().apply {
                put("ID_RECEIVER",message.receiver!!.userid!!)
                put("ID_SENDER",message.sender!!.userid)
                put("CONTENT",message.message)
                put("ID_BACKEND",message.id)
                put("SITUATION",message.situation!!.toInt())
                put("CREATIONDATE",message.creationDate)
            }
            this.writableDatabase.insert("MESSAGES",null,contentValues)
        }
    }
}