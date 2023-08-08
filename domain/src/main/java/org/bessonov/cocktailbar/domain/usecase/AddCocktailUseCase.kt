package org.bessonov.cocktailbar.domain.usecase

import org.bessonov.cocktailbar.domain.model.Cocktail
import org.bessonov.cocktailbar.domain.repository.CocktailRepository

class AddCocktailUseCase(private val repository: CocktailRepository) {

    suspend operator fun invoke(cocktail: Cocktail) {
        repository.add(cocktail = cocktail)
    }
}