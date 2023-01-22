package cz.sedlardavid.eventorr.components.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.viewModels.EventsViewModel

@Composable
fun EventDetailPage(
    event: Event,
    navController: NavHostController,
) {
    val viewModel: EventsViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = rememberAsyncImagePainter(event.performers.first().image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Text(text = event.type.replaceFirstChar { c -> c.uppercase() }, color = Color.White)
        Text(text = event.title, color = Color.White, fontSize = 25.sp)
    }
}