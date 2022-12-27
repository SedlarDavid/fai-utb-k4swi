package cz.sedlardavid.eventorr.components.screenData

import androidx.annotation.StringRes
import cz.sedlardavid.eventorr.R

sealed class ScreenData(val route: String, @StringRes val resourceId: Int) {
    object Dashboard : ScreenData("dashboard", R.string.dashboard)
    object Event : ScreenData("event", R.string.event)
}