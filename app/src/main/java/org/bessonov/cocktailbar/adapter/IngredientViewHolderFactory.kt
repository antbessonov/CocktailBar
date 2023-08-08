package org.bessonov.cocktailbar.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bessonov.cocktailbar.adapter.IngredientListAdapter.Companion.VIEW_TYPE_ADD_INGREDIENT
import org.bessonov.cocktailbar.adapter.IngredientListAdapter.Companion.VIEW_TYPE_INGREDIENT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientViewHolderFactory @Inject constructor() {

    fun create(
        parent: ViewGroup,
        viewType: Int,
        onDeleteClick: ((Int) -> Unit)? = null,
        onAddIngredientClick: (() -> Unit)? = null,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_INGREDIENT -> IngredientUiViewHolder(
                parent = parent,
                onDeleteClick = onDeleteClick
            )
            VIEW_TYPE_ADD_INGREDIENT -> AddIngredientViewHolder(
                parent = parent,
                onAddIngredientClick = onAddIngredientClick
            )
            else -> {
                throw RuntimeException("Unknown view type: $viewType")
            }
        }
    }
}