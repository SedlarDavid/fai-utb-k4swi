package cz.sedlardavid.eventorr.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.components.event.EventTile
import cz.sedlardavid.eventorr.viewModels.EventsViewModel

@Composable
fun EventsPage(
    navController: NavHostController,
) {
    val viewModel: EventsViewModel = hiltViewModel()
    viewModel.getEvents()
    val events = viewModel.events.observeAsState()

    if (events.value!!.isEmpty())
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    else
        LazyColumn {
            items(events.value!!.size)
            { index ->
                EventTile(
                    events.value!![index],
                    navController
                )
            }
        }
}

