package com.example.reciperanger.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.reciperanger.R
import com.example.reciperanger.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding : FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        binding.btnNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}