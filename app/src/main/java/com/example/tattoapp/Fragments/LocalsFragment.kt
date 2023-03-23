package com.example.tattoapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.Models.Local
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.LocalRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        Log.e("ADAPTEERRRR",adapter.toString())
    }

    private fun setUpLocals(){
        val local= Local()
        local.loadLocals(adapter)
//        adapter.add(local.locals)
    }

}