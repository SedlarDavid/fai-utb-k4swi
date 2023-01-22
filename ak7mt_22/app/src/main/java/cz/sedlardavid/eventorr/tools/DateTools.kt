package cz.sedlardavid.eventorr.tools

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"


class DateTools {
    companion object {
        fun getEventDay(
            dateString: String
        ): String {

            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            val dayFormatter = SimpleDateFormat("d", Locale.ENGLISH)
            return dayFormatter.format(formatter.parse(dateString)!!)
        }

        fun getEventMonth(
            dateString: String
        ): String {

            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            val monthFormatter = SimpleDateFormat("MMM", Locale.ENGLISH)
            return monthFormatter.format(formatter.parse(dateString)!!)
        }
    }
}