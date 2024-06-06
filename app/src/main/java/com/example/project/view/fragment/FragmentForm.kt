package com.example.project.view.fragment
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentFormBinding
import com.example.project.viewmodel.PetViewModel

import  androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Date
class FragmentForm : Fragment() {
    private val calendar = Calendar.getInstance()
    private lateinit var binding: FragmentFormBinding
    private var selectedDate: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater)
        binding.datePicker.hint = "Date"


        return binding.root
    }


    override fun onResume() {
        super.onResume()
       //obtenerRazasDePerro()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spPrioridad.setSelection(1)
        setup()
        goBack()
    }


    private fun setup() {


        binding.etFecha.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showDatePickerDialog()
                return@setOnTouchListener true // Consume the touch event
            }
            false
        }
    }
    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.etFecha.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun goBack() {
        binding.toolbarBalls.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}