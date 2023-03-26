package com.example.reciperanger.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.reciperanger.R
import com.example.reciperanger.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Set click listener for login button
        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()

            if (checkEmptyFields(email, password)) return@setOnClickListener

            // Disable login button and show progress bar
            binding.btnLogin.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            signInAttempt(email, password)
        }

        // Set click listener for create account button
        binding.btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    // Attempt to sign in with email and password provided
    private fun signInAttempt(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // If successful, enable login button and hide progress bar, then navigate to home fragment
                binding.btnLogin.isEnabled = true
                binding.progressBar.visibility = View.GONE
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else if (task.exception is FirebaseAuthInvalidUserException) {
                // If user is not registered, enable login button and hide progress bar, then navigate to register fragment
                binding.btnLogin.isEnabled = true
                binding.progressBar.visibility = View.GONE
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment(
                        email,
                        password
                    )
                )
            }
            else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                // If password is incorrect, enable login button and hide progress bar, then display error message
                binding.btnLogin.isEnabled = true
                binding.progressBar.visibility = View.GONE
                binding.containerPassword.helperText = "Wrong password!"
            }
        }
    }

    // Check if email or password is empty, display a toast message if so and return
    private fun checkEmptyFields(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT)
                .show()
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}