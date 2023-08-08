package org.bessonov.cocktailbar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.databinding.CocktailItemBinding


class CocktailViewHolder(
    val parent: ViewGroup,
    private val onItemClick: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.cocktail_item,
        parent,
        false
    )
) {

    val binding = CocktailItemBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onItemClick?.invoke(bindingAdapterPosition)
        }
    }
}