package com.example.reciperanger.ui.screens.login

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

        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            binding.btnLogin.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            mAuth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.visibility = View.GONE

                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                else if (task.exception is FirebaseAuthInvalidUserException) {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment(email,password))
                }
                else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    binding.containerPassword.helperText = "Wrong password!"
                }
            }
        }

        binding.btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}