package org.bessonov.cocktailbar.state

import org.bessonov.cocktailbar.domain.model.Cocktail

sealed class MyCocktailsState {

    object Loading : MyCocktailsState()

    object Empty : MyCocktailsState()

    data class Content(
        val cocktailList: List<Cocktail>
    ) : MyCocktailsState()
}
