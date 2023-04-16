package com.example.shoppinglist1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.db.ListModel
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
        val repository = ShoppingListRepository(requireContext())
        binding.button.setOnClickListener {
            lifecycleScope.launch{
                val bundle = this@AddListFragment.arguments
                val email = bundle?.getString(USER_EMAIL)

                // creare lista noua
                val newList = ListModel(title = binding.titleEdit.text.toString(),
                    description = binding.descriptionEdit.text.toString(),
                    estimatedCost = binding.estimatedCostEdit.text.toString().toInt(),
                    userEmail = email
                )

                repository.insertList(newList)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}