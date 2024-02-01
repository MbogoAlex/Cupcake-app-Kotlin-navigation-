package com.example.cupcake2.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake2.R
import com.example.cupcake2.data.Datasource
import com.example.cupcake2.ui.theme.Cupcake2Theme

@Composable
fun SelectNumOfCupcakesScreen(
    options: List<Pair<Int, Int>>,
    onNumOfCupcakesSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.cupcake),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.one_cupcake),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        options.forEach { item ->
            CupcakeButton(

                label = item.first,
                onClick = { onNumOfCupcakesSelected(item.second) },
                modifier = modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun CupcakeButton(
    @StringRes label: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.i("MYTAG", "CLICKED")
    Button(
        onClick = {onClick()},
        modifier = modifier
            .widthIn(min = 250.dp)
    ) {
        Text(text = stringResource(id = label))
    }
}

@Preview(showBackground = true)
@Composable
fun SelectNumOfCupcakesScreenReview() {
    Cupcake2Theme {
        SelectNumOfCupcakesScreen(
            onNumOfCupcakesSelected = {},
            options = Datasource.quantityOptions,
        )
    }
}