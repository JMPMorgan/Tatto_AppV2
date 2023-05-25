package com.example.tattoapp.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.DB.SQLLocal
import com.example.tattoapp.Models.Local
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.LocalRecyclerView

class LocalsFragment : Fragment() {

    private lateinit var  binding:View
    private lateinit var adapter: LocalRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=inflater.inflate(R.layout.fragment_locals, container, false)
        return binding.rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpLocals()
    }

    private fun setUpRecycler(){

        adapter = LocalRecyclerView(listOf())
        binding.findViewById<RecyclerView>(R.id.localRecycler).apply {
            layoutManager=GridLayoutManager(
                activity,1,GridLayoutManager.VERTICAL,false
            )
            adapter=this@LocalsFragment.adapter
        }
    }

    private fun setUpLocals(){
        val local= Local()
        val SQLLocal= SQLLocal(requireContext())
        if(!isInternetConnected(requireContext())){
            if(!SQLLocal.isTableExists("LOCAL",SQLLocal.writableDatabase)){
                showToast("Locales no Encontrados.")
                return
            }
            val localDB=SQLLocal.getInformationLocal()
            adapter.add(localDB)
            return
        }
        SQLLocal.deleteInformationLocal()
        SQLLocal.onCreate(SQLLocal.writableDatabase)
        local.loadLocals(adapter,SQLLocal)
//        adapter.add(local.locals)
    }

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun showToast(text:String){
        Toast.makeText(requireContext() ,text, Toast.LENGTH_SHORT).show()
    }

}