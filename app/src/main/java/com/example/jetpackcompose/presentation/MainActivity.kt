package com.example.jetpackcompose.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.presentation.navigation.Screen
import com.example.jetpackcompose.presentation.ui.beers.screens.BeersDetailScreen
import com.example.jetpackcompose.presentation.ui.beers.screens.BeersScreen
import com.example.jetpackcompose.presentation.ui.beers.viewmodels.BeerDetailViewModel
import com.example.jetpackcompose.presentation.ui.beers.viewmodels.BeersViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {

    var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            navController?.let { navController ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.BeersScreen.route
                ) {

                    composable(route = Screen.BeersScreen.route) {
                        BeersScreen(
                            onNavigateToScreen = { route ->
                                navController.navigate(route)
                            },
                            viewModel = hiltNavGraphViewModel<BeersViewModel>(it),
                        )
                    }

                    composable(
                        route = "${Screen.BeerDetailScreen.route}/{beerId}",
                        arguments = listOf(
                            navArgument("beerId") { type = NavType.IntType }
                        )
                    ) {
                        BeersDetailScreen(
                            viewModel =  hiltNavGraphViewModel<BeerDetailViewModel>(it),
                        )
                    }

                }
            }

        }
    }

}
