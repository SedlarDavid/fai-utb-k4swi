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
            dateString: String,
            type: EventDateType = EventDateType.MONTH_NAME
        ): String {

            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            val monthFormatter = SimpleDateFormat(if (type == EventDateType.MONTH_NAME) "MMM" else "M", Locale.ENGLISH)
            return monthFormatter.format(formatter.parse(dateString)!!)
        }

        fun getEventDate(
            dateString: String,
        ): String {

            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            val fullFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
            return fullFormat.format(formatter.parse(dateString)!!)
        }
    }
}

enum class EventDateType {
    MONTH, MONTH_NAME
}