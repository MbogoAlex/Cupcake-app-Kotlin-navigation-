//package com.example.cupcake2
//
//import androidx.activity.ComponentActivity
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.compose.ComposeNavigator
//import androidx.navigation.testing.TestNavHostController
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//
//class CupcakeScreenNavigationTest {
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
//
//    private lateinit var navController: TestNavHostController
//
//    @Before
//    fun setupCupcakeNavHost() {
//        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current).apply {
//                navigatorProvider.addNavigator(ComposeNavigator())
//            }
//            CupCakeApp(navController = navController)
//        }
//    }
//
//    @Test
//    fun cupcakeNavHost_verifyStartDestination() {
//        assertEquals(CupcakeScreen.Start.name, navController.currentBackStackEntry?.destination?.route)
//    }
//}