package org.bessonov.cocktailbar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.databinding.IngredientItemBinding


class IngredientUiViewHolder(
    val parent: ViewGroup,
    private val onDeleteClick: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.ingredient_item,
        parent,
        false
    )
) {

    val binding = IngredientItemBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onDeleteClick?.invoke(bindingAdapterPosition)
        }
    }
}