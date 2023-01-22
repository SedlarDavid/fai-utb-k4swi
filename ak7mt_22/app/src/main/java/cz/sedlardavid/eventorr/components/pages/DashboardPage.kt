package cz.sedlardavid.eventorr.components.screenData.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.viewModels.EventsViewModel


@Composable
fun DashboardPage(
    navController: NavHostController

) {
    val viewModel: EventsViewModel = hiltViewModel()
    val favorites = viewModel.favorites.observeAsState()
    //  var favorites by remember { mutableStateOf(ArrayList<Event>()) }


    LazyColumn {
        item {
            Text(text = "Favorite events", modifier = Modifier.padding(bottom = 20.dp))
        }
        items(favorites.value!!.size) { index ->
            EventTile(favorites.value!![index], isFavorite = true, navController)
        }
    }
}