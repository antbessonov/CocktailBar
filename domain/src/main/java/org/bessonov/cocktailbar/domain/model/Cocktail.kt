package org.bessonov.cocktailbar.domain.model

data class Cocktail(
    val title: String,
    val description: String?,
    val recipe: String?
)