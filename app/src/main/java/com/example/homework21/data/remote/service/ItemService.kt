package com.example.homework21.data.remote.service

import com.example.homework21.data.remote.model.ItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ItemService {
    @GET("1775d634-92dc-4c32-ae71-1707b8cfee41")
    suspend fun getItems(): Response<List<ItemDto>>
}