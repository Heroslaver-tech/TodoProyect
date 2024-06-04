package com.example.project.view.fragment

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.example.project.databinding.FragmentMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()
}

