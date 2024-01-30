package com.example.homework21.presentation.state

import com.example.homework21.presentation.model.Items

data class HomeState(
    val isLoading: Boolean = false,
    val items: List<Items>? = emptyList(),
    val errorMessage: String? = null
)