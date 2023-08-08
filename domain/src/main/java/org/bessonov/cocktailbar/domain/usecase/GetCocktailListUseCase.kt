package org.bessonov.cocktailbar.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.bessonov.cocktailbar.domain.model.Cocktail
import org.bessonov.cocktailbar.domain.repository.CocktailRepository

class GetCocktailListUseCase(private val repository: CocktailRepository) {

    operator fun invoke(): Flow<List<Cocktail>> {
        return repository.getList()
    }
}