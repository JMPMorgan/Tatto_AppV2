package com.example.tattoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.Fragments.ChatsFragment
import com.example.tattoapp.Fragments.LocalsFragment
import com.example.tattoapp.Fragments.ProfileFragment
import com.example.tattoapp.Fragments.SearchFragment
import com.example.tattoapp.Models.Local
import com.example.tattoapp.RecyclerViews.LocalRecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter:LocalRecyclerView

    lateinit var  bottomNavigation:BottomNavigationView
    private val onNavMenu= BottomNavigationView.OnNavigationItemSelectedListener{
        item->
        when(item.itemId){
            R.id.LocalsFragmentOptions->{
                supportFragmentManager.commit {
                    replace<LocalsFragment> (R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.PruebaOptions->{
                supportFragmentManager.commit {
                    replace<ProfileFragment> (R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.messages->{
                supportFragmentManager.commit {
                    replace<ChatsFragment> (R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val local= Local()

        bottomNavigation=findViewById(R.id.navMenu)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavMenu)
        supportFragmentManager.commit {
            replace<LocalsFragment> (R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }

//        local.loadLocals(this,findViewById(R.id.localRecycler))
    }
}