package com.example.cupcake2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake2.R
import com.example.cupcake2.data.OrderUiState
import com.example.cupcake2.ui.components.FormattedPriceLabel
import com.example.cupcake2.ui.theme.Cupcake2Theme

@Composable
fun SummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit = {},
    onSendButtonClicked: (String, String) -> Unit = {s1: String, s2: String ->},
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numOfCupcakes = resources.getQuantityString(
        R.plurals.cupcake,
        orderUiState.quantity,
        orderUiState.quantity
    )

    val orderSummary = stringResource(
        id = R.string.order_details,
        numOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.quantity
    )

    val newOrder = stringResource(id = R.string.new_cupcake_order)

    val items = listOf(
        Pair(stringResource(id = R.string.quantity), numOfCupcakes),
        Pair(stringResource(id = R.string.flavor), orderUiState.flavor),
        Pair(stringResource(id = R.string.pickup_date), orderUiState.date)
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column() {
            items.forEach { item ->
                Text(text = item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                Divider(thickness = 1.dp)
            }
            Spacer(modifier = Modifier.height(1.dp))

            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
        Row {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onSendButtonClicked(newOrder, orderSummary) }
                ) {
                    Text(text = stringResource(id = R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onCancelButtonClicked() }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    Cupcake2Theme {
        SummaryScreen(
            orderUiState = OrderUiState(1, "Test", "Test", "$300.00")
        )
    }
}