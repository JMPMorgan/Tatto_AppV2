package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.Fragments.LocalsFragment
import com.example.tattoapp.Models.Local
import com.example.tattoapp.RecyclerViews.LocalRecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter:LocalRecyclerView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val local= Local()

        supportFragmentManager.commit {
            replace<LocalsFragment> (R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }

//        local.loadLocals(this,findViewById(R.id.localRecycler))
    }
}