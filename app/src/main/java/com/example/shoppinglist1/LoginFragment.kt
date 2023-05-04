package com.example.shoppinglist1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shoppinglist1.databinding.FragmentLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = requireActivity().
            getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener{
            val emailText = binding.email.text.toString()
            val passwordText = binding.password.text.toString()

            if(emailText.isBlank() || passwordText.isBlank()){
                Toast.makeText(activity, "Please enter your email or password", Toast.LENGTH_SHORT).show()
            }
            else{
                databaseReference.child("users").addListenerForSingleValueEvent(object:
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val emailTextPath = emailText.replace(".", ",")

                        // verific daca email-ul exista in bd
                        if(snapshot.hasChild(emailTextPath)){
                            val getPassword = snapshot.child(emailTextPath).child("password").getValue(String:: class.java)

                            if (getPassword.equals(passwordText)){
                                Toast.makeText(requireActivity(), "Successfully logged in", Toast.LENGTH_SHORT).show()

                                val fullnameText = snapshot.child(emailTextPath).child("fullname").getValue(String:: class.java)

                                // update SharedPreferences
                                val editor = sharedPreferences.edit()
                                editor.putBoolean(KEY_LOGGED_IN, true)
                                editor.putString(KEY_USER_EMAIL, emailText)
                                editor.putString(KEY_USER_FULLNAME, fullnameText)
                                editor.apply()

                                // open MainActivity
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.putExtra("userEmail", emailText)
                                intent.putExtra("userFullname", fullnameText)
                                startActivity(intent)
                                activity?.finish()
                            }
                            else{
                                Toast.makeText(activity, "Wrong password", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(activity, "Wrong email", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }

        binding.registerNowButton.setOnClickListener {
            startActivity(Intent(activity, RegisterActivity::class.java))
        }
    }
}