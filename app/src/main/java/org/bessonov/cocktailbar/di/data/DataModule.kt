package org.bessonov.cocktailbar.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bessonov.cocktailbar.data.database.CocktailDao
import org.bessonov.cocktailbar.data.mapper.CocktailMapper
import org.bessonov.cocktailbar.data.repository.CocktailRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCocktailMapper(): CocktailMapper {
        return CocktailMapper()
    }

    @Provides
    @Singleton
    fun provideCocktailRepositoryImpl(
        cocktailDao: CocktailDao,
        cocktailMapper: CocktailMapper
    ): CocktailRepositoryImpl {
        return CocktailRepositoryImpl(
            cocktailDao = cocktailDao,
            cocktailMapper = cocktailMapper
        )
    }
}