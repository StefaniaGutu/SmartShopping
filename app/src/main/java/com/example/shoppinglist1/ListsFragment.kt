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
import com.example.shoppinglist1.db.ListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        val mainBundle = this.arguments
        val email = mainBundle?.getString(USER_EMAIL)

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