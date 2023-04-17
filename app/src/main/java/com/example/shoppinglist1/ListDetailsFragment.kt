package com.example.shoppinglist1

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding
import com.example.shoppinglist1.db.Item
import com.example.shoppinglist1.db.ListModel
import com.example.shoppinglist1.db.ShoppingListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.values
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class ListDetailsFragment : Fragment() {

    lateinit var binding: FragmentListDetailsBinding
    var list = ArrayList<Item>()
    lateinit var adapter: CheckboxRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")
        var entity: ListModel
        var shareList: String = ""

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

        val bundle = this.arguments
        if(bundle != null) {
            val id = bundle.getString(LIST_ID)
            val email = bundle.getString(USER_EMAIL)
            val curentEntryQuery = databaseReference.child("shoppingLists").child(id!!)

            curentEntryQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    entity = snapshot.getValue(ListModel::class.java)!!
                    binding.titleEdit.text = SpannableStringBuilder(entity.title)
                    binding.descriptionEdit.text = SpannableStringBuilder(entity.description)
                    binding.estimatedCostEdit.text =
                        SpannableStringBuilder(entity.estimatedCost.toString())

                    list = entity.items
                    shareList = entity.toString()

                    lifecycleScope.launch {
                        adapter.update(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            binding.buttonEdit.setOnClickListener {
                lifecycleScope.launch {
                    val updatedList = ListModel(binding.titleEdit.text.toString(),
                        binding.descriptionEdit.text.toString(),
                        binding.estimatedCostEdit.text.toString().toInt(),
                        email,
                        items = list
                        )
                    curentEntryQuery.setValue(updatedList)
                }
                requireActivity().supportFragmentManager.popBackStack()
            }

            binding.buttonDelete.setOnClickListener {
                lifecycleScope.launch {
                    curentEntryQuery.removeValue()
                }
                requireActivity().supportFragmentManager.popBackStack()
            }

            binding.buttonShare.setOnClickListener {
                //intent to share
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, shareList)

                //create chooser
                val chooser = Intent.createChooser(intent, "Share using...")
                startActivity(chooser)
            }
        }
    }
}