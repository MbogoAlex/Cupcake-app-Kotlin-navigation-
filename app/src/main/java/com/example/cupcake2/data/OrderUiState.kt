package com.example.cupcake2.data

data class OrderUiState(
    val quantity: Int = 0,
    val flavor: String = "",
    val date: String = "",
    val price: String = "",
    val pickUpOptions: List<String> = listOf()
)
