package com.example.homework21.presentation.event

sealed class HomeEvent {
    data object FetchItems : HomeEvent()
    data object ResetErrorMessage : HomeEvent()
    data object FetchCategories : HomeEvent()
}