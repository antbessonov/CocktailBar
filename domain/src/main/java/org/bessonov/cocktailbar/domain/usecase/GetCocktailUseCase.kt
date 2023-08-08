package org.bessonov.cocktailbar.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.bessonov.cocktailbar.domain.model.Cocktail
import org.bessonov.cocktailbar.domain.repository.CocktailRepository

class GetCocktailUseCase(private val repository: CocktailRepository) {

    operator fun invoke(title: String): Flow<Cocktail> {
        return repository.get(title = title)
    }
}