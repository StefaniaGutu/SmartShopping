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
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding
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

                    shareList = entity.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            binding.buttonEdit.setOnClickListener {
                lifecycleScope.launch {
                    val updatedList = ListModel(binding.titleEdit.text.toString(),
                        binding.descriptionEdit.text.toString(),
                        binding.estimatedCostEdit.text.toString().toInt(),
                        email
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