package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding

class ListDetailsFragment : Fragment() {

    lateinit var binding: FragmentListDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}