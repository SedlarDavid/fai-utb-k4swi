package cz.sedlardavid.eventorr.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.sedlardavid.eventorr.R
import cz.sedlardavid.eventorr.components.pages.EventDetailPage
import cz.sedlardavid.eventorr.components.pages.EventsPage
import cz.sedlardavid.eventorr.components.pages.FavoritesPage
import cz.sedlardavid.eventorr.components.screenData.ScreenData
import cz.sedlardavid.eventorr.viewModels.EventsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val items = listOf(
            ScreenData.Events,
            ScreenData.Favorites,
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                backgroundColor = Color.Black.copy(alpha = 0.85f),
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = colorResource(R.color.primary),
                        contentColor = Color.White
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.icon, contentDescription = null) },
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
                    startDestination = ScreenData.Events.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(ScreenData.Favorites.route) { FavoritesPage(navController) }
                    composable(ScreenData.Events.route) { EventsPage(navController) }
                    composable(ScreenData.EventDetail.route + "/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType }))
                    { backStackEntry ->
                        val viewModel: EventsViewModel = hiltViewModel()
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        // Read from our single source of truth
                        // This means if that data later becomes *not* static, you'll
                        // be able to easily substitute this out for an observable
                        // data source
                        val model = viewModel.events.value!!.find { m -> m.event.id == id } ?: throw Exception("Event not found!")
                        EventDetailPage(model.event)
                    }
                }
            }
        }
    }
}
