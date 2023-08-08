package org.bessonov.cocktailbar.adapter

import androidx.recyclerview.widget.DiffUtil
import org.bessonov.cocktailbar.model.IngredientUi

object IngredientUiDiffCallback : DiffUtil.ItemCallback<IngredientUi>() {

    override fun areItemsTheSame(oldItem: IngredientUi, newItem: IngredientUi): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: IngredientUi, newItem: IngredientUi): Boolean {
        return oldItem == newItem
    }
}