package com.example.cupcake2

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake2.data.Datasource
import com.example.cupcake2.ui.OrderViewModel
import com.example.cupcake2.ui.SelectNumOfCupcakesScreen
import com.example.cupcake2.ui.SelectOptionsScreen
import com.example.cupcake2.ui.SummaryScreen
import com.example.cupcake2.ui.theme.Cupcake2Theme

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary),
}

@Composable
fun CupCakeApp(
    viewModel: OrderViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupCakeAppBar(
                currentScreen = currentScreen,
                navigationBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        val orderUiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                SelectNumOfCupcakesScreen(
                    options = Datasource.quantityOptions,
                    onNumOfCupcakesSelected = {
                        quantity -> viewModel.setQuantity(quantity)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    }
                )

            }

            composable(route = CupcakeScreen.Flavor.name) {
                val localContext = LocalContext.current
                SelectOptionsScreen(
                    subtotal = orderUiState.price,
                    options = Datasource.flavors.map { item -> localContext.resources.getString(item) },
                    onOptionSelected = { flavor -> viewModel.setFlavor(flavor) },
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Pickup.name)
                    }
                )

            }

            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionsScreen(
                    subtotal = orderUiState.price,
                    options = orderUiState.pickUpOptions,
                    onOptionSelected = { date -> viewModel.setDate(date) },
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    }
                )
            }
            
            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                SummaryScreen(
                    orderUiState = orderUiState,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onSendButtonClicked = {
                            subject: String, summary: String ->
                        shareOrder(context, subject, summary)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupCakeAppBar(
    currentScreen: CupcakeScreen,
    navigationBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = currentScreen.title))
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if(navigationBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}


@Preview(showBackground = true)
@Composable
fun CupCakeAppPreview() {
    Cupcake2Theme {
        CupCakeAppBar(
            currentScreen = CupcakeScreen.Start,
            navigationBack = true
        )
    }
}