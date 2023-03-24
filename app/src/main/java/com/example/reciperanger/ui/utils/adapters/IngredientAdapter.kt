package com.example.reciperanger.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reciperanger.databinding.ItemRvIngredientBinding
import com.example.reciperanger.ui.utils.differs.IngredientDiffItemCallBack

class IngredientAdapter : ListAdapter<Map<String,String>, IngredientAdapter.ViewHolder>(IngredientDiffItemCallBack()) {
    inner class ViewHolder(private val binding: ItemRvIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient : String,measure: String) {
            binding.ingredient = ingredient
            binding.measures = measure

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvIngredientBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).values.first(),getItem(position).values.elementAt(1))
    }
}