package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist1.databinding.FragmentAddListBinding
import com.example.shoppinglist1.databinding.FragmentListsBinding
import kotlinx.coroutines.launch

const val LIST_ID = "list_id"

class ListsFragment : Fragment() {

    lateinit var binding: FragmentListsBinding

    lateinit var adapter: CustomAdapter

    val repository: ListRepository by lazy {
        ListRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CustomAdapter { model ->
            //trimit id prin bundle catre edit fragment
            val bundle = Bundle()
            bundle.putInt(LIST_ID, model.id ?: 0) //iau id-ul listei (daca e null, trimit 0)

            // deschidem fragmentul
            val fragment = ListDetailsFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerView.adapter = adapter

        binding.floatingAdd.setOnClickListener{

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddListFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch() {
            adapter.update(repository.getAllLists())
        }
    }
}