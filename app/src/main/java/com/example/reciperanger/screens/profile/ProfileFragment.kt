package com.example.reciperanger.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.reciperanger.R
import com.example.reciperanger.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var profileImageUpdate: UserProfileChangeRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    profileImageUpdate = UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)
                        .build()

                    Glide.with(binding.ivProfileImage).load(uri).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ivProfileImage)
                    Log.d("PhotoPicker", "Selected URI: $uri")
                }
                else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        checkImageAvailability()

        binding.etEmail.setText(mAuth.currentUser?.email)

        // Launch image picker on image click
        binding.ivProfileImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Save data on button click
        binding.btnUpdate.setOnClickListener {
            mAuth.currentUser?.updateProfile(profileImageUpdate)
        }

        // Logout on click
        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            if (mAuth.currentUser == null) {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                (requireActivity() as AppCompatActivity).supportActionBar?.hide()
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun checkImageAvailability() {
        if (mAuth.currentUser?.photoUrl.toString().isNotEmpty()) {
            Glide.with(binding.ivProfileImage).load(mAuth.currentUser?.photoUrl).diskCacheStrategy(
                DiskCacheStrategy.DATA
            ).into(binding.ivProfileImage)
        }
    }
}