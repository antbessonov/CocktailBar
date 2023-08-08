package org.bessonov.cocktailbar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.bessonov.cocktailbar.domain.usecase.GetCocktailListUseCase
import org.bessonov.cocktailbar.state.MyCocktailsState
import javax.inject.Inject

@HiltViewModel
class MyCocktailsViewModel @Inject constructor(
    getCocktailListUseCase: GetCocktailListUseCase
) : ViewModel() {

    val uiState = getCocktailListUseCase()
        .map { cocktailList ->
            if (cocktailList.isEmpty()) {
                MyCocktailsState.Empty
            } else {
                MyCocktailsState.Content(cocktailList = cocktailList)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = MyCocktailsState.Loading
        )
}