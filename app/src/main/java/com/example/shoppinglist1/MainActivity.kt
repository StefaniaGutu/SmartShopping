package com.example.shoppinglist1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shoppinglist1.databinding.ActivityMainBinding
import com.example.shoppinglist1.databinding.FragmentAddListBinding

const val USER_EMAIL = "user_email"
const val USER_FULLNAME = "user_fullname"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var userEmail: String
    lateinit var userFullname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra("userEmail")!!
        userFullname = intent.getStringExtra("userFullname")!!

        //trimit email-ul prin bundle catre lists fragment
        val bundle = Bundle()
        bundle.putString(USER_EMAIL, userEmail)
        bundle.putString(USER_FULLNAME, userFullname)

        val fragment = ListsFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .commit()


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.lists -> replaceFragment(ListsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> { }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val bundle = Bundle()
        bundle.putString(USER_EMAIL, userEmail)
        bundle.putString(USER_FULLNAME, userFullname)

        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, null)
            .commit()
    }
}