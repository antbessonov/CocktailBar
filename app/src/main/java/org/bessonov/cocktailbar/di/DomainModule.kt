package org.bessonov.cocktailbar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.bessonov.cocktailbar.domain.repository.CocktailRepository
import org.bessonov.cocktailbar.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideAddCocktailUseCase(
        repository: CocktailRepository
    ): AddCocktailUseCase {
        return AddCocktailUseCase(repository = repository)
    }

    @Provides
    fun provideGetCocktailUseCase(
        repository: CocktailRepository
    ): GetCocktailUseCase {
        return GetCocktailUseCase(repository = repository)
    }

    @Provides
    fun provideUpdateCocktailUseCase(
        repository: CocktailRepository
    ): UpdateCocktailUseCase {
        return UpdateCocktailUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteCocktailUseCase(
        repository: CocktailRepository
    ): DeleteCocktailUseCase {
        return DeleteCocktailUseCase(repository = repository)
    }

    @Provides
    fun provideGetCocktailListUseCase(
        repository: CocktailRepository
    ): GetCocktailListUseCase {
        return GetCocktailListUseCase(repository = repository)
    }
}