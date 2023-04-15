package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding
import com.example.shoppinglist1.db.ShoppingListModel
import kotlinx.coroutines.launch

class AddListFragment : Fragment() {

    lateinit var binding: FragmentAddListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = ListRepository(requireContext())
        binding.button.setOnClickListener {
            lifecycleScope.launch{
                repository.insert(
                    ShoppingListModel(title = binding.titleEdit.text.toString(),
                                    description = binding.descriptionEdit.text.toString(),
                                    estimatedCost = binding.estimatedCostEdit.text.toString().toInt())
                )
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
        //
    }
}