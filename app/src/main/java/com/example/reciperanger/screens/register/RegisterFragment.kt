package com.example.reciperanger.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reciperanger.R
import com.example.reciperanger.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterFragment : Fragment() {
    private val args: RegisterFragmentArgs by navArgs()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        populateEmailAndPassword()

        binding.btnRegister.setOnClickListener {

            // Extract input values
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmation = binding.etConfirmPassword.text.toString()

            // Validation
            if (validateEmptyFields(email, password)) return@setOnClickListener
            if (validatePasswordMatch(password, confirmation)) return@setOnClickListener
            if (validatePasswordLength(password)) return@setOnClickListener

            resetHelperText()

            // Disable register button and show progress bar
            binding.btnRegister.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            registerUserWithFirebaseAuth(email, password)
        }

        // Create new account button navigate to login fragment
        binding.btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    // Register user with Firebase Authentication
    private fun registerUserWithFirebaseAuth(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registration successful, enable register button and hide progress bar
                binding.apply {
                    btnRegister.isEnabled = true
                    progressBar.visibility = View.GONE
                }

                // Navigate to home screen
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            }
            else if (task.exception is FirebaseAuthUserCollisionException) {
                // Registration failed due to email already existing, enable register button and hide progress bar
                binding.apply {
                    // Registration failed due to email already existing, enable register button and hide progress bar
                    btnRegister.isEnabled = true
                    progressBar.visibility = View.GONE

                    // Set helper text for email field
                    containerEmail.helperText = getString(R.string.already_exits)
                }
            }
        }
    }

    // Reset helper texts
    private fun resetHelperText() {
        binding.apply {
            containerPassword.helperText = null
            containerEmail.helperText = null
            containerConfirmPassword.helperText = null
        }
    }

    // Check for password confirmation match
    private fun validatePasswordMatch(password: String, confirmation: String): Boolean {
        if (password != confirmation) {
            binding.containerConfirmPassword.helperText = "Password mismatch."
            return true
        }
        return false
    }

    // Check for minimum password length
    private fun validatePasswordLength(password: String): Boolean {
        if (password.length < 6) {
            binding.containerPassword.helperText = "Minimum 6 characters are required."
            return true
        }
        return false
    }


    // Check for empty fields
    private fun validateEmptyFields(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fields_required),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        return false
    }


    // Check the passed args if not empty
    // Then set initial value depends on the args check result
    private fun populateEmailAndPassword() {
        if (args.email.isNotEmpty() && args.password.isNotEmpty()) {
            binding.etMail.setText(args.email)
            binding.etPassword.setText(args.password)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}