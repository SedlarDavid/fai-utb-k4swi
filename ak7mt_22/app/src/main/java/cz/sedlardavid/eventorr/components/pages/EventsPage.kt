package cz.sedlardavid.eventorr.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.sedlardavid.eventorr.R
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
            CircularProgressIndicator(color = colorResource(id = R.color.accent))
        }
    else
        LazyColumn {
            item {
                Text(
                    text = stringResource(id = R.string.events),
                    color = Color.White, modifier = Modifier.padding(20.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold

                )
            }
            items(events.value!!.size)
            { index ->
                EventTile(
                    events.value!![index],
                    navController
                )
            }
        }
}

