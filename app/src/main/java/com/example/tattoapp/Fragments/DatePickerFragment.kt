package com.example.tattoapp.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.tattoapp.Interfaces.DateSelected
import com.example.tattoapp.R
import java.util.*

class DatePickerFragment(val dateSelected: DateSelected): DialogFragment(), DatePickerDialog.OnDateSetListener {
    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val dayOfMonth=calendar.get(Calendar.YEAR)
        val calendarDialog=DatePickerDialog(requireContext(),this,year,month,dayOfMonth)
        val calendarPicker=calendarDialog.datePicker
        calendarPicker.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))


        calendarPicker.foregroundTintList=ColorStateList.valueOf(R.color.purple_200)
         return calendarDialog
    }

    override fun onDateSet(view: DatePicker?, year:Int, month:Int, dayOfMonth:Int){
        dateSelected.receiveData(year,month,dayOfMonth)
    }
}