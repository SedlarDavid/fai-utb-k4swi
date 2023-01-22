package cz.sedlardavid.eventorr.components.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.components.event.EventTile
import cz.sedlardavid.eventorr.viewModels.EventsViewModel


@Composable
fun FavoritesPage(
    navController: NavHostController

) {
    val viewModel: EventsViewModel = hiltViewModel()
    val favorites = viewModel.favorites.observeAsState()

    LazyColumn {
        item {
            Text(
                text = "Favorite events",
                color = Color.White, modifier = Modifier.padding(20.dp), fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(favorites.value!!.size) { index ->
            EventTile(favorites.value!![index], navController)
        }
    }
}