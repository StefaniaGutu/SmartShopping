package com.example.shoppinglist1

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.shoppinglist1.databinding.FragmentListDetailsBinding
import com.example.shoppinglist1.db.ShoppingListModel
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

        val repo = ListRepository(requireContext())

        val bundle = this.arguments
        if(bundle != null){
            val id = bundle.getInt(LIST_ID)
            lifecycleScope.launch{
                val list = repo.getListDetails(id)

                binding.titleEdit.text = SpannableStringBuilder(list.title)
                binding.descriptionEdit.text = SpannableStringBuilder(list.description)
                binding.estimatedCostEdit.text = SpannableStringBuilder(list.estimatedCost.toString())
            }

            binding.buttonEdit.setOnClickListener {
                lifecycleScope.launch {
                    repo.updateListDetails(
                        ShoppingListModel(
                            id,
                            binding.titleEdit.text.toString(),
                            binding.descriptionEdit.text.toString(),
                            binding.estimatedCostEdit.text.toString().toInt()
                        )
                    )
                }
                requireActivity().supportFragmentManager.popBackStack()
            }

            binding.buttonDelete.setOnClickListener {
                lifecycleScope.launch {
                    repo.delete(id)
                }
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}