package com.example.homework21.presentation.event

sealed class HomeEvent {
    data object FetchConnections : HomeEvent()
    data object ResetErrorMessage : HomeEvent()
}