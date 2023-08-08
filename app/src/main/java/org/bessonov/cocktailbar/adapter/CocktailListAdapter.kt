package org.bessonov.cocktailbar.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.bessonov.cocktailbar.databinding.CocktailItemBinding
import org.bessonov.cocktailbar.domain.model.Cocktail
import javax.inject.Inject

class CocktailListAdapter @Inject constructor() : ListAdapter<Cocktail, CocktailViewHolder>(
    CocktailDiffCallback
) {

    var onItemClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return CocktailViewHolder(parent = parent, onItemClick = onItemClick)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = getItem(position)
        setTitle(binding = holder.binding, cocktail = cocktail)
    }

    private fun setTitle(binding: CocktailItemBinding, cocktail: Cocktail) {
        binding.title.text = cocktail.title
    }
}