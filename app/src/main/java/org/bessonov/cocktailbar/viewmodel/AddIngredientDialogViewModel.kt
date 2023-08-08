package org.bessonov.cocktailbar.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.bessonov.cocktailbar.state.AddIngredientDialogEvent
import org.bessonov.cocktailbar.state.AddIngredientDialogState
import javax.inject.Inject

@HiltViewModel
class AddIngredientDialogViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<AddIngredientDialogState> = MutableStateFlow(
        AddIngredientDialogState()
    )
    val uiState = _uiState.asStateFlow()

    private val _event: MutableStateFlow<AddIngredientDialogEvent?> = MutableStateFlow(null)
    val event = _event.asStateFlow()

    fun updateTitle(value: CharSequence?) {
        _uiState.update { state -> state.copy(title = value.toString()) }
        hideEvent()
    }

    fun clickedAdd() {
        val state = _uiState.value
        if (state.title != null && state.title != EMPTY_TITLE) {
            _event.update { AddIngredientDialogEvent.Save }
        } else {
            _event.update { AddIngredientDialogEvent.EmptyTitle }
        }
    }

    private fun hideEvent() {
        _event.update { null }
    }

    companion object {

        private const val EMPTY_TITLE = ""
    }
}