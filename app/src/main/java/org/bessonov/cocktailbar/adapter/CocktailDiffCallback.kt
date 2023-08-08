package org.bessonov.cocktailbar.adapter

import androidx.recyclerview.widget.DiffUtil
import org.bessonov.cocktailbar.domain.model.Cocktail

object CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {

    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }
}