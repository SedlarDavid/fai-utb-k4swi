package cz.sedlardavid.eventorr.components.screens

import androidx.annotation.StringRes
import cz.sedlardavid.eventorr.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Dashboard : Screen("dashboard", R.string.dashboard)
    object Event : Screen("event", R.string.event)
}