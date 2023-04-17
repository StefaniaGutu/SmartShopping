package com.example.shoppinglist1

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.db.Item
import com.example.shoppinglist1.db.ListModel
import com.example.shoppinglist1.db.ShoppingListModel
import kotlinx.coroutines.launch

class AddListFragment : Fragment() {

    lateinit var binding: FragmentAddListBinding
    var list = ArrayList<Item>()
    lateinit var adapter: CheckboxRecyclerViewAdapter

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

        adapter = CheckboxRecyclerViewAdapter(list)

        binding.checkboxReciclerview.layoutManager = LinearLayoutManager(context)
        binding.checkboxReciclerview.adapter = adapter

        binding.floatingAdd.setOnClickListener {

            val newItem = binding.newItem.text.toString()
            list.add(Item(list.size, newItem))

            binding.newItem.text = SpannableStringBuilder("")

            lifecycleScope.launch {
                adapter.update(list)
            }
        }

        binding.button.setOnClickListener {
            lifecycleScope.launch{
                val bundle = this@AddListFragment.arguments
                val email = bundle?.getString(USER_EMAIL)

                // creare lista noua
                val newList = ListModel(title = binding.titleEdit.text.toString(),
                    description = binding.descriptionEdit.text.toString(),
                    estimatedCost = binding.estimatedCostEdit.text.toString().toInt(),
                    userEmail = email,
                    items = list
                )

                repository.insertList(newList)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}