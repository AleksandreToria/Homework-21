package com.example.homework21.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework21.data.remote.common.Resource
import com.example.homework21.domain.usecase.GetItemsByIdUseCase
import com.example.homework21.domain.usecase.GetItemsUseCase
import com.example.homework21.presentation.event.HomeEvent
import com.example.homework21.presentation.mapper.toPresenter
import com.example.homework21.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val getsItemsByIdUseCase: GetItemsByIdUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    private var isFetchingByCategory = false

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchItems -> fetchItems()
            HomeEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            HomeEvent.FetchCategories -> fetchCategories()
            is HomeEvent.FetchProductsByCategory -> fetchItemsByCategory(event.category)
        }
    }

    private fun fetchItems() {
        isFetchingByCategory = false
        viewModelScope.launch {
            getItemsUseCase().collect { it ->
                when (it) {
                    is Resource.Error -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = it.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _homeState.update { currentState ->
                            currentState.copy(isLoading = it.loading, showRetry = false)
                        }
                    }

                    is Resource.Success -> {
                        val items = it.data.map { it.toPresenter() }

                        _homeState.update { currentState ->
                            currentState.copy(
                                items = items,
                                isLoading = false
                            )
                        }

                        if (items.isEmpty()) {
                            updateErrorMessage(message = "No Items to show")
                        } else {
                            updateErrorMessage(message = null)
                        }
                    }
                }
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            getItemsUseCase().collect { it ->
                when (it) {
                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _homeState.update {
                        it.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        val categories = it.data.map { it.category }.distinct()
                        val updatedCategories = mutableListOf("All").apply { addAll(categories) }

                        _homeState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                category = updatedCategories
                            )
                        }
                    }
                }
            }
        }
    }


    private fun fetchItemsByCategory(category: String) {
        isFetchingByCategory = true
        viewModelScope.launch {
            getsItemsByIdUseCase(category).collect{ it ->
                when(it) {
                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _homeState.update {
                        it.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                categoryItems = it.data.map { it.toPresenter() }
                            )
                        }
                    }
                }
            }
        }
    }

    fun isFetchingByCategory() = isFetchingByCategory

    private fun updateErrorMessage(message: String?) {
        _homeState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}