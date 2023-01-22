package cz.sedlardavid.eventorr.components.pages

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.viewModels.EventsViewModel

@Composable
fun EventDetailPage(
    event: Event,
    navController: NavHostController,
) {
    val viewModel: EventsViewModel = hiltViewModel()

}