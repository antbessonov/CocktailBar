package org.bessonov.cocktailbar.state

import org.bessonov.cocktailbar.model.IngredientUi

data class AddCocktailState(
    val title: String? = null,
    val ingredientList: List<IngredientUi> = emptyList(),
    val description: String? = null,
    val recipe: String? = null
)

sealed class AddCocktailEvent {

    object EmptyTitle : AddCocktailEvent()

    object EmptyIngredientList : AddCocktailEvent()

    object Save : AddCocktailEvent()

    object ClickCancel : AddCocktailEvent()
}