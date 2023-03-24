package com.example.reciperanger.ui.screens.register

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

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if (args.email.isNotEmpty() && args.password.isNotEmpty()) {
            binding.etMail.setText(args.email)
            binding.etPassword.setText(args.password)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmation = binding.etConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (password != confirmation) {
                binding.containerConfirmPassword.helperText = "Password mismatch."
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.containerPassword.helperText = "Minimum 6 characters are required."
                return@setOnClickListener
            }

            binding.containerPassword.helperText = null
            binding.containerEmail.helperText = null
            binding.containerConfirmPassword.helperText = null

            binding.btnRegister.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.btnRegister.isEnabled = true
                    binding.progressBar.visibility = View.GONE

                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }
                else if (task.exception is FirebaseAuthUserCollisionException) {
                    binding.btnRegister.isEnabled = true
                    binding.progressBar.visibility = View.GONE
                    binding.containerEmail.helperText = "Already Exists!"
                }
            }
        }

        binding.btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}