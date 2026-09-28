package com.example.learn2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class UiState {
    data object Loading : UiState()

    data class Success(
        val name: String,
        val profession: String
    ) : UiState()

    data class Error(
        val message: String
    ) : UiState()
}


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> = _uiState

    private var loadJob: Job? = null

    fun loadUser() {
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            _uiState.value = UiState.Loading

            when (val result = repository.getUser()) {

                is Resource.Success -> {
                    val user = result.data

                    _uiState.value = UiState.Success(
                        name = user.name,
                        profession = user.profession
                    )
                }

                is Resource.Error -> {
                    val message = when (result.error) {

                        AppError.NoInternet ->
                            "No internet connection"

                        AppError.NotFound ->
                            "User not found"

                        AppError.Server ->
                            "Server error"

                        AppError.Unknown ->
                            "Something went wrong"
                    }

                    _uiState.value = UiState.Error(message)
                }
            }
        }
    }

    fun showError() {
        loadJob?.cancel()
        _uiState.value = UiState.Error(
            message = "User not found"
        )
    }

    fun reset() {
        loadJob?.cancel()
        _uiState.value = UiState.Loading
    }

    fun testDispatchers() {
        viewModelScope.launch {
            println("Main: ${Thread.currentThread().name}")

            withContext(Dispatchers.IO) {
                println("IO: ${Thread.currentThread().name}")
            }

            println("Main again: ${Thread.currentThread().name}")
        }
    }
}
