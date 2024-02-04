package com.example.homework21.data.remote.service

import com.example.homework21.data.remote.model.ItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    @GET("df8d4951-2757-45aa-8f60-bf1592a090ce")
    suspend fun getItems(): Response<List<ItemDto>>
}