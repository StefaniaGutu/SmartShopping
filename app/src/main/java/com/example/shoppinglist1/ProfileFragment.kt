package com.example.shoppinglist1

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import com.example.shoppinglist1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var sharedPreferences: SharedPreferences

    val KEY_LOGGED_IN = "isLoggedIn"
    val KEY_USER_EMAIL = "userEmail"
    val KEY_USER_FULLNAME = "userFullname"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if(bundle != null) {
            val fullname = bundle.getString(USER_FULLNAME)
            val email = bundle.getString(USER_EMAIL)

            binding.userEmail.text = email
            binding.userName.text = fullname
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)

            // update SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_LOGGED_IN, false)
            editor.putString(KEY_USER_EMAIL, null)
            editor.putString(KEY_USER_FULLNAME, null)
            editor.apply()
        }

        val scaleAnim = ObjectAnimator.ofFloat(binding.profileImage, View.SCALE_X, -1f, 1f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            startDelay = 1000
            repeatMode = ValueAnimator.RESTART
            interpolator = BounceInterpolator()
        }

        scaleAnim.start()
    }
}