package com.example.shoppinglist1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.shoppinglist1.databinding.FragmentListsBinding
import com.example.shoppinglist1.db_models.ListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

const val LIST_ID = "list_id"

class ListsFragment : Fragment() {

    lateinit var binding: FragmentListsBinding
    lateinit var initialList: ArrayList<ListModel>
    lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainBundle = this.arguments
        val email = mainBundle?.getString(USER_EMAIL)

        binding.searchView!!.clearFocus()
        binding.searchView!!.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

        })

        adapter = CustomAdapter { model ->
            //trimit id prin bundle catre edit fragment
            val bundle = Bundle()
            bundle.putString(LIST_ID, model.id ?: "") //iau id-ul listei (daca e null, trimit un string gol)
            bundle.putString(USER_EMAIL, model.userEmail)

            // deschidem fragmentul
            val fragment = ListDetailsFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .addToBackStack(null)
                .commit()
        }

        initialList = adapter.listItems as ArrayList<ListModel>
        binding.recyclerView.adapter = adapter

        binding.floatingAdd.setOnClickListener{

            //trimit emailul prin bundle catre add fragment
            val bundle = Bundle()
            bundle.putString(USER_EMAIL,  email?: "")

            // deschidem fragmentul
            val fragment = AddListFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun filterList(text: String) {
        if(text == "" || text == null){
            adapter.listItems = initialList
            adapter.notifyDataSetChanged()
        }
        else{
            val filteredList = ArrayList<ListModel>()
            for (item in initialList){
                if(item.title!!.lowercase().contains(text.lowercase())){
                    filteredList.add(item)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show()
            }

            adapter.listItems = filteredList
            adapter.notifyDataSetChanged()

        }
    }

    override fun onResume() {
        super.onResume()

        val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")
        val shoppingLists = mutableListOf<ListModel>()

        val bundle = this.arguments
        if(bundle != null) {
            val email = bundle.getString(USER_EMAIL)

            val query = databaseReference.child("shoppingLists").orderByChild("userEmail").equalTo(email)

            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val entity = childSnapshot.getValue(ListModel::class.java)
                        entity?.id = childSnapshot.key
                        shoppingLists.add(entity!!)
                    }

                    lifecycleScope.launch {
                        adapter.update(shoppingLists)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            lifecycleScope.launch {
                adapter.update(shoppingLists)
            }
        }
    }
}