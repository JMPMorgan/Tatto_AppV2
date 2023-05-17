package com.example.tattoapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tattoapp.DB.SQLUser
import com.example.tattoapp.R
import com.example.tattoapp.RecyclerViews.ChatsRecyclerView
import com.example.tattoapp.RecyclerViews.DataClasses.Chats
import com.example.tattoapp.RecyclerViews.DataClasses.ServerResponse.ChatsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsFragment : Fragment() {
    private lateinit var  binding:View
    private lateinit var adapter: ChatsRecyclerView
    var listChats: List<Chats> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=inflater.inflate(R.layout.fragment_chats, container, false)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpChats()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter=ChatsRecyclerView(listChats)
        binding.findViewById<RecyclerView>(R.id.chatsRecycler).apply {
            layoutManager=GridLayoutManager(
                activity,1,GridLayoutManager.VERTICAL,false
            )
            adapter=this@ChatsFragment.adapter
        }

    }

    private fun setUpChats(){
        val SQLUser=SQLUser(requireContext())
        val data = SQLUser.getInformation()
        if(data.isEmpty()){
            return
        }
        val chats=Chats()
        val result=chats.getConversations(data[0].toString())
        result.enqueue(object :Callback<ChatsResponse>{
            override fun onResponse(call: Call<ChatsResponse>, response: Response<ChatsResponse>) {
                listChats=response.body()!!.chats!!
                Log.e("CHATS AAAA",listChats.toString())
                adapter.add(listChats)
            }

            override fun onFailure(call: Call<ChatsResponse>, t: Throwable) {
                Log.e("PRUEBA ERROR",t.toString())
            }

        })
    }

}