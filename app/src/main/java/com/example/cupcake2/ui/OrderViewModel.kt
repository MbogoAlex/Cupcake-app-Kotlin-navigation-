package com.example.cupcake2.ui

import android.icu.text.NumberFormat
import androidx.lifecycle.ViewModel
import com.example.cupcake2.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(value = OrderUiState(pickUpOptions = pickDate()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setQuantity(quantity: Int) {
        _uiState.update {
            it.copy(
                quantity = quantity,
                price = calculatePrice(quantity = quantity)
            )
        }
    }

    fun setFlavor(flavor: String) {
        _uiState.update {
            it.copy(
                flavor = flavor
            )
        }
    }

    fun setDate(pickUpdate: String) {
        _uiState.update {
            it.copy(
                date = pickUpdate,
                price = calculatePrice(pickUpdate = pickUpdate)
            )
        }
    }

    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickUpdate: String = _uiState.value.date,
    ): String {
        var calculatePrice = quantity * PRICE_PER_CUPCAKE

        if(pickDate()[0] == pickUpdate) {
            calculatePrice += PRICE_FOR_SAME_DAY_PICKUP
        }

        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatePrice)
        return formattedPrice
    }

    fun pickDate() : List<String> {
        var dates: MutableList<String> = mutableListOf()
        val formatDate = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            dates.add(formatDate.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dates
    }

    fun resetOrder() {
        _uiState.value = OrderUiState(pickUpOptions = pickDate())
    }
}