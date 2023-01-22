package cz.sedlardavid.eventorr.components.screenData

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import cz.sedlardavid.eventorr.R

sealed class ScreenData(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Favorites : ScreenData("favorites", Icons.Filled.Favorite, R.string.favorites)
    object Events : ScreenData("events", Icons.Filled.CalendarMonth, R.string.events)

    object EventDetail : ScreenData("eventDetail", Icons.Filled.Details, R.string.event_detail)
}