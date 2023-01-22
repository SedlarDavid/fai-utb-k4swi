package cz.sedlardavid.eventorr.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.sedlardavid.eventorr.components.pages.EventDetailPage
import cz.sedlardavid.eventorr.components.screenData.ScreenData
import cz.sedlardavid.eventorr.components.screenData.pages.DashboardPage
import cz.sedlardavid.eventorr.components.screenData.pages.EventsPage
import cz.sedlardavid.eventorr.viewModels.EventsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = listOf(
            ScreenData.Dashboard,
            ScreenData.Event,
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                label = { Text(stringResource(screen.resourceId)) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = ScreenData.Dashboard.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(ScreenData.Dashboard.route) { DashboardPage(navController) }
                    composable(ScreenData.Event.route) { EventsPage(navController) }
                    composable(ScreenData.EventDetail.route + "/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType }))
                    { backStackEntry ->
                        val viewModel: EventsViewModel = hiltViewModel()
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        // Read from our single source of truth
                        // This means if that data later becomes *not* static, you'll
                        // be able to easily substitute this out for an observable
                        // data source
                        val event = viewModel.events.value!!.find { e -> e.id == id } ?: throw Exception("Event not found!")
                        EventDetailPage(event, navController)
                    }
                }
            }
        }
    }
}
