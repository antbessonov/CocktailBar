package org.bessonov.cocktailbar.domain.usecase

import org.bessonov.cocktailbar.domain.repository.CocktailRepository

class DeleteCocktailUseCase(private val repository: CocktailRepository) {

    suspend operator fun invoke(title: String) {
        repository.delete(title = title)
    }
}