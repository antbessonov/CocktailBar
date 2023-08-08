package org.bessonov.cocktailbar.domain.repository

import kotlinx.coroutines.flow.Flow
import org.bessonov.cocktailbar.domain.model.Cocktail

interface CocktailRepository {

    suspend fun add(cocktail: Cocktail)

    fun get(title: String): Flow<Cocktail>

    suspend fun update(cocktail: Cocktail)

    fun getList(): Flow<List<Cocktail>>
}