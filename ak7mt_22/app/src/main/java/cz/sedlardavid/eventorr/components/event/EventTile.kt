package cz.sedlardavid.eventorr.components.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import cz.sedlardavid.eventorr.components.screenData.ScreenData
import cz.sedlardavid.eventorr.models.EventModel
import cz.sedlardavid.eventorr.tools.DateTools
import cz.sedlardavid.eventorr.viewModels.EventsViewModel


@Composable
fun EventTile(
    model: EventModel,
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val viewModel: EventsViewModel = hiltViewModel()
    val event = model.event
    val isFav = model.isFav.observeAsState()
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black),
        startY = sizeImage.height.toFloat() / 3,  // 1/3
        endY = sizeImage.height.toFloat()
    )
    Card(
        shape = RoundedCornerShape(25.dp), backgroundColor = Color.Transparent,
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .height(400.dp)
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clickable {
                navController.navigate(ScreenData.EventDetail.route + "/${event.id}")
            }
    )
    {
        Box {

            Image(
                painter = rememberAsyncImagePainter(event.performers.first().image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned {
                        sizeImage = it.size
                    },
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(gradient)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        if (isFav.value!!) viewModel.removeFromFavorites(model) else viewModel.addToFavorites(model)
                    }) {
                        Icon(
                            if (isFav.value!!) Icons.Filled.Close else Icons.Filled.Favorite,
                            "favorite",
                            tint = Color.White
                        )


                    }
                    Card(
                        shape = RoundedCornerShape(5.dp),
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .width(45.dp)
                            .height(50.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = DateTools.getEventDay(event.datetime_local))
                            Text(text = DateTools.getEventMonth(event.datetime_local))
                        }
                    }

                }

                Column {
                    Text(text = event.type.replaceFirstChar { c -> c.uppercase() }.replace('_', ' '), color = Color.White)
                    Text(text = event.short_title, color = Color.White, fontSize = 25.sp)
                }


            }
        }

    }
}