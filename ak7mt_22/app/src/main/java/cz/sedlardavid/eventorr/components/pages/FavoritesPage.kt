package cz.sedlardavid.eventorr.components.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.components.event.EventTile
import cz.sedlardavid.eventorr.models.EventModel
import cz.sedlardavid.eventorr.viewModels.EventsViewModel

@Composable
fun FavoritesPage(
    navController: NavHostController

) {
    val viewModel: EventsViewModel = hiltViewModel()

    var search by rememberSaveable { mutableStateOf("") }

    var renderFavorites by remember { mutableStateOf(mutableListOf<EventModel>()) }
    val favorites = viewModel.favorites.observeAsState()
    renderFavorites = favorites.value!!.toMutableList()
    LazyColumn {
        item {
            Text(
                text = "Favorite events",
                color = Color.White, modifier = Modifier.padding(20.dp), fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            TextField(
                value = search,
                onValueChange = {
                    search = it
                    renderFavorites = if (search.isNotBlank())
                        viewModel.favorites.value!!.filter { m -> m.event.title.contains(search, ignoreCase = true) }.toMutableList()
                    else
                        favorites.value!!.toMutableList()
                },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp), colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White, //hide the indicator
                    unfocusedIndicatorColor = Color.White,
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White
                )
            )

        }
        if (renderFavorites.isNotEmpty())
            items(renderFavorites.size) { index ->
                EventTile(renderFavorites[index], navController)
            }
        else
            item {
                Text(
                    text = "No favorites yet!",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 200.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
    }
}