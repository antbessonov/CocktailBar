package org.bessonov.cocktailbar.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.bessonov.cocktailbar.databinding.IngredientItemBinding
import org.bessonov.cocktailbar.model.IngredientUi
import javax.inject.Inject

class IngredientListAdapter @Inject constructor(
    private val ingredientViewHolderFactory: IngredientViewHolderFactory
) : ListAdapter<IngredientUi, RecyclerView.ViewHolder>(IngredientUiDiffCallback) {

    var onAddIngredientClick: (() -> Unit)? = null
    var onDeleteClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ingredientViewHolderFactory.create(
            parent = parent,
            viewType = viewType,
            onDeleteClick = onDeleteClick,
            onAddIngredientClick = onAddIngredientClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IngredientUiViewHolder -> setIngredientContent(
                binding = holder.binding,
                position = position
            )
            is AddIngredientViewHolder -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) {
            VIEW_TYPE_ADD_INGREDIENT
        } else {
            VIEW_TYPE_INGREDIENT
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + ONE_ADD_BUTTON
    }

    private fun setIngredientContent(binding: IngredientItemBinding, position: Int) {
        val ingredient = getItem(position)
        setTitle(binding = binding, ingredient = ingredient)
    }

    private fun setTitle(ingredient: IngredientUi, binding: IngredientItemBinding) {
        binding.title.text = ingredient.title
    }

    companion object {

        const val VIEW_TYPE_INGREDIENT = 1
        const val VIEW_TYPE_ADD_INGREDIENT = -1

        const val ONE_ADD_BUTTON = 1
    }
}