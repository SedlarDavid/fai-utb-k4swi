package cz.sedlardavid.eventorr.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import cz.sedlardavid.eventorr.components.screens.Screen
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.mocks.EventsResponseMock
import cz.sedlardavid.eventorr.viewModels.EventsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val items = listOf(
            Screen.Dashboard,
            Screen.Event,
        )
        val mock = EventsResponseMock.mock()
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
                    startDestination = Screen.Dashboard.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Dashboard.route) { DashboardPage(navController) }
                    composable(Screen.Event.route) { EventsPage(navController) }
                }
            }
        }
    }
}

@Composable
fun DashboardPage(
    navController: NavHostController

) {
    val viewModel: EventsViewModel = hiltViewModel()
    viewModel

    Text(text = "Profile")
}

@Composable
fun EventsPage(
    navController: NavHostController,

    ) {
    val viewModel: EventsViewModel = hiltViewModel()
    LazyColumn {
        items(viewModel.events.value!!.size)
        { index ->
            EventTile(viewModel.events.value!![index])
        }
    }
}


@Composable
fun EventTile(event: Event) {
    val configuration = LocalConfiguration.current
    val viewModel: EventsViewModel = hiltViewModel()

    Card(
        shape = RoundedCornerShape(25.dp), backgroundColor = Color.Transparent,
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .height(400.dp)
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    )
    {
        Box {

            Image(
                painter = rememberAsyncImagePainter(event.performers.first().image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = Crop
            )

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(25.dp)
                        .height(25.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        viewModel.addToFavorites(event)
                    }) {
                        Icon(Icons.Filled.Favorite, "favorite")
                    }
                    Card(modifier = Modifier.background(color = Color.White)) {
                        Column {
                            Text(text = "X")
                        }
                    }

                }
                Column {
                    Text(text = event.type, color = Color.White)
                    Text(text = event.short_title, color = Color.White, fontSize = 25.sp)
                }
            }


        }
    }

}