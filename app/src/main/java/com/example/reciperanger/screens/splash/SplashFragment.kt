package com.example.reciperanger.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.reciperanger.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        lifecycleScope.launch {
            // Set 3 seconds delay in splash
            delay(3000)

            decideNavigation()
        }

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    // A function that will decide the navigation destination depends on the user login state
    private fun decideNavigation() {
        // Check if user is logged in
        // Navigate to home if logged in
        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
        // Navigate to welcome if not logged in
        else {
            findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
        }
    }
}