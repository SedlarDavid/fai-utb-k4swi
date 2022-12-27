package cz.sedlardavid.eventorr.components.screenData.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.viewModels.EventsViewModel

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
fun EventTile(event: Event, isFavorite: Boolean = false) {
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

            //TODO
            if (event.performers.isNotEmpty())
                Image(
                    painter = rememberAsyncImagePainter(event.performers.first().image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Green), verticalArrangement = Arrangement.SpaceBetween) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(25.dp)
                        .height(25.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        if (isFavorite) viewModel.removeFromFavorites(event) else viewModel.addToFavorites(event)
                    }) {
                        Icon(if (!isFavorite) Icons.Filled.Close else Icons.Filled.Favorite, "favorite")
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
}