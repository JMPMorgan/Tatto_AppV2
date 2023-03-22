package com.example.tattoapp.Fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.tattoapp.Interfaces.DateSelected
import java.util.*

class DatePickerFragment(val dateSelected: DateSelected): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val dayOfMonth=calendar.get(Calendar.YEAR)
        return DatePickerDialog(requireContext(),this,year,month,dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year:Int, month:Int, dayOfMonth:Int){
        dateSelected.receiveData(year,month,dayOfMonth)
    }
}