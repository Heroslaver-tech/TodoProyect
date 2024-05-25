package com.example.project.view.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.model.Pet
import com.example.project.viewmodel.PetViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.LifecycleOwner
import com.example.project.webservices.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.findNavController
import com.example.project.R
import com.example.project.webservices.Raza
import com.example.project.databinding.FragmentFormBinding


class FragmentForm : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val petViewModel: PetViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
       //obtenerRazasDePerro()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drivers()
        goBack()
    }

    private fun drivers() {
        validarDatos()
        binding.btnSavePet.setOnClickListener {
            savePet()
        }


    }

    private fun savePet() {
        val name = binding.etName.text.toString()
        val race = binding.etRace.text.toString()
        val namePerson = binding.etNamePerson.text.toString()
        val tel = binding.etTel.text.toString().toLong()
        // Obtener el índice del elemento seleccionado en el Spinner
        val selectedPosition = binding.spSymptom.selectedItemPosition
        // Obtener el valor del elemento seleccionado utilizando el índice
        val symptom = binding.spSymptom.getItemAtPosition(selectedPosition).toString()
        val newPet =
            Pet(name = name, race = race, namePerson = namePerson, tel = tel, symptom = symptom)
        petViewModel.savePet(newPet)
        Log.d("test", newPet.toString())
        Toast.makeText(context, "Artículo guardado !!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()

    }

    //Verifica que los datos se hayan llenado antes de mandar
    private fun validarDatos() {
        val listTextInputEditText =
            listOf(binding.etName, binding.etRace, binding.etNamePerson, binding.etTel)
        val spinner = binding.spSymptom

        // Verifica TextInputEditText
        listTextInputEditText.forEach { textInputEditText ->
            textInputEditText.addTextChangedListener { editable ->
                val isListFull =
                    listTextInputEditText.all { it.text?.isNotEmpty() == true } && spinner.selectedItemPosition != 0
                binding.btnSavePet.isEnabled = isListFull
            }
        }


        //Verifica el spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val isListFull = listTextInputEditText.all { it.text?.isNotEmpty() == true } && position != 0
                binding.btnSavePet.isEnabled = isListFull
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No necesitamos hacer nada aquí
            }
        }
    }


//    private fun obtenerRazasDePerro() {
//        val retroftiTraer = RetrofitClient.consumirApi.getTraer()
//
//        retroftiTraer.enqueue(object : Callback<List<Raza>> {
//
//            override fun onResponse(call: Call<List<Raza>>, response: Response<List<Raza>>) {
//                val razas = response.body()
//
//                if (razas != null) {
//                    mostrarRazasEnTextView(razas)
//                } else {
//                    showToast("No se encontraron razas")
//                }
//
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<List<Raza>>, t: Throwable) {
//                showToast("Error al consultar Api Rest: $t")
//            }
//        })
//    }


    private fun mostrarRazasEnTextView(razas: List<Raza>) {
        val properties = razas::class.java.declaredFields
        val keysList = mutableListOf<String>()

        for (property in properties) {
            property.isAccessible = true
            val propertyName = property.name
            if (property.type == List::class.java) {
                keysList.add(propertyName)
            }
        }

        val razasString =  razas.joinToString(separator = "\n") { it.name }

       // Mostrar el listado de razas en el TextView
       // binding.etRace.setText(keysList[0])
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun goBack() {
        binding.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}