package com.example.homework21.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework21.data.common.Resource
import com.example.homework21.domain.usecase.GetItemsUseCase
import com.example.homework21.presentation.event.HomeEvent
import com.example.homework21.presentation.mapper.toPresenter
import com.example.homework21.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchConnections -> fetchConnections()
            HomeEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            getItemsUseCase().collect { it ->
                when (it) {
                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Loading -> {
                        _homeState.update { currentState ->
                            currentState.copy(isLoading = it.loading)
                        }
                    }

                    is Resource.Success -> {
                        val items = it.data.map { it.toPresenter() }

                        _homeState.update { currentState ->
                            currentState.copy(
                                items = items,
                                isLoading = false,
                            )
                        }

                        if (items.isEmpty()) {
                            updateErrorMessage(message = "No Items to show")
                        }
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(message: String?) {
        _homeState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}