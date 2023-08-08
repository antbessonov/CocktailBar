package org.bessonov.cocktailbar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.databinding.AddIngredientItemBinding

class AddIngredientViewHolder(
    val parent: ViewGroup,
    onAddIngredientClick: (() -> Unit)?
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.add_ingredient_item,
        parent,
        false
    )
) {
    val binding = AddIngredientItemBinding.bind(itemView)

    init {

        binding.addBtn.setOnClickListener {
            onAddIngredientClick?.invoke()
        }
    }
}