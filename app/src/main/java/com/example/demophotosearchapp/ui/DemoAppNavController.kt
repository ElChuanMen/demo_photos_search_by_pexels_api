package com.example.demophotosearchapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
/**
 * Created by ElChuanmen on 6/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
sealed class Destinations(val route: String) {
    data object Splash : Destinations("splash")
    data object Home : Destinations("home")

}

object NavParams {
    const val IMAGE_ID = "image_id"
}

@Composable
fun rememberEventAppNavController(
    navController: NavHostController = rememberNavController()
): DemoAppNavController = remember(navController) {
    DemoAppNavController(navController)
}

@Stable
class DemoAppNavController(
    val navController: NavHostController
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateTo(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
            }
        }
    }

    fun popNavigateTo(route: String) {
        if (route != currentRoute) {
            navController.popBackStack(findStartDestination(navController.graph).id, true)
            navController.navigate(route) {
                launchSingleTop = true
            }
        }
    }

    fun popGatewayNavigateTo(route: String) {
        if (route != currentRoute) {
            navController.popBackStack(Destinations.Home.route, true)
            navController.navigate(route) {
                launchSingleTop = true
            }
        }
    }
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}