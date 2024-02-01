package com.example.cupcake2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake2.R
import com.example.cupcake2.data.Datasource
import com.example.cupcake2.ui.components.FormattedPriceLabel
import com.example.cupcake2.ui.theme.Cupcake2Theme

@Composable
fun SelectOptionsScreen(
    subtotal: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedOption by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            options.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = selectedOption == it,
                            onClick = {
                                selectedOption = it
                                onOptionSelected(it)
                            }
                        )
                ) {
                    RadioButton(selected = selectedOption == it, onClick = {
                        selectedOption = it
                        onOptionSelected(it)
                    })
                    Text(text = it)
                }

            }
            Divider(
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            FormattedPriceLabel(
                subtotal = subtotal,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { onCancelButtonClicked() }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = selectedOption.isNotEmpty(),
                onClick = { onNextButtonClicked() }
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectOptionsScreenPreview() {
    val localContext = LocalContext.current
    Cupcake2Theme {
        SelectOptionsScreen(
            subtotal = "300.00",
            options = Datasource.flavors.map { localContext.resources.getString(it) }
        )
    }
}