package org.bessonov.cocktailbar.state

data class AddIngredientDialogState(
    val title: String? = null
)

sealed class AddIngredientDialogEvent {

    object EmptyTitle : AddIngredientDialogEvent()

    object Save : AddIngredientDialogEvent()
}