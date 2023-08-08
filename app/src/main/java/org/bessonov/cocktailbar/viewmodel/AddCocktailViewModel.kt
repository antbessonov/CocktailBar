package org.bessonov.cocktailbar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bessonov.cocktailbar.domain.model.Cocktail
import org.bessonov.cocktailbar.domain.usecase.AddCocktailUseCase
import org.bessonov.cocktailbar.model.IngredientUi
import org.bessonov.cocktailbar.state.AddCocktailEvent
import org.bessonov.cocktailbar.state.AddCocktailState
import javax.inject.Inject

@HiltViewModel
class AddCocktailViewModel @Inject constructor(
    private val addCocktailUseCase: AddCocktailUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddCocktailState> = MutableStateFlow(AddCocktailState())
    val uiState = _uiState.asStateFlow()

    private val _event: MutableStateFlow<AddCocktailEvent?> = MutableStateFlow(null)
    val event = _event.asStateFlow()

    fun updateTitle(value: CharSequence?) {
        _uiState.update { state -> state.copy(title = value.toString()) }
        hideEvent()
    }

    fun clickedSave() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.title != null && state.title != EMPTY_TITLE) {
                checkIngredientList(
                    title = state.title,
                    ingredientList = state.ingredientList,
                    description = state.description,
                    recipe = state.recipe
                )
            } else {
                _event.update { AddCocktailEvent.EmptyTitle }
            }
        }
    }

    fun clickedCancel() {
        _event.update { AddCocktailEvent.ClickCancel }
    }

    fun hideEvent() {
        _event.update { null }
    }

    fun updateIngredientList(ingredient: IngredientUi) {
        _uiState.update { state ->
            val ingredientList = state.ingredientList.toMutableList()
            ingredientList.add(ingredient)
            state.copy(ingredientList = ingredientList)
        }
    }

    private suspend fun checkIngredientList(
        title: String,
        ingredientList: List<IngredientUi>,
        description: String?,
        recipe: String?
    ) {
        if (ingredientList.isNotEmpty()) {
            val cocktail = Cocktail(
                title = title,
                ingredientList = ingredientList.toString(),
                description = description,
                recipe = recipe
            )
            saveCocktail(cocktail = cocktail)
        } else {
            _event.update { AddCocktailEvent.EmptyIngredientList }
        }
    }

    private suspend fun saveCocktail(cocktail: Cocktail) {
        addCocktailUseCase.invoke(cocktail = cocktail)
        _event.update { AddCocktailEvent.Save }
    }

    companion object {

        private const val EMPTY_TITLE = ""
    }
}