package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding

class AddListFragment : Fragment() {

    lateinit var binding: FragmentAddListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddListBinding.inflate(inflater, container, false)
        return binding.root
    }

}