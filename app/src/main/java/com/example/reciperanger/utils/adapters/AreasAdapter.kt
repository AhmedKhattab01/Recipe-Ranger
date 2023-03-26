package com.example.reciperanger.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.areas.Meal
import com.example.reciperanger.R
import com.example.reciperanger.databinding.ItemRvAreasBinding
import com.example.reciperanger.screens.home.HomeFragmentDirections
import com.example.reciperanger.utils.differs.AreasDiffItemCallBack

class AreasAdapter :
    ListAdapter<Meal, AreasAdapter.ViewHolder>(AreasDiffItemCallBack()) {

    inner class ViewHolder(private val binding: ItemRvAreasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meal) {
            binding.areas = item
            binding.executePendingBindings()

            loadImage(item, binding.ivCategory)

            binding.root.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAreaMealsFragment(item.strArea))
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvAreasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun loadImage(item: Meal, view: ImageView) {
        val areaMap = mapOf(
            "American" to R.drawable.us,
            "British" to R.drawable.gb,
            "Canadian" to R.drawable.ca,
            "Chinese" to R.drawable.cn,
            "Croatian" to R.drawable.hr,
            "Dutch" to R.drawable.nl,
            "Egyptian" to R.drawable.eg,
            "French" to R.drawable.fr,
            "Greek" to R.drawable.gr,
            "Indian" to R.drawable.`in`,
            "Irish" to R.drawable.ie,
            "Italian" to R.drawable.it,
            "Jamaican" to R.drawable.jm,
            "Japanese" to R.drawable.jp,
            "Kenyan" to R.drawable.kn,
            "Malaysian" to R.drawable.my,
            "Mexican" to R.drawable.mx,
            "Moroccan" to R.drawable.ma,
            "Polish" to R.drawable.pl,
            "Portuguese" to R.drawable.pt,
            "Russian" to R.drawable.ru,
            "Spanish" to R.drawable.es,
            "Thai" to R.drawable.th,
            "Tunisian" to R.drawable.tn,
            "Turkish" to R.drawable.tr,
            "Vietnamese" to R.drawable.vn
        )

        val resourceID = areaMap[item.strArea] ?: 0

        Glide.with(view).load(resourceID).fitCenter().into(view)
    }
}