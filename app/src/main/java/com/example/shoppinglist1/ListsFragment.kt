package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.databinding.FragmentListsBinding

class ListsFragment : Fragment() {

    lateinit var binding: FragmentListsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

}