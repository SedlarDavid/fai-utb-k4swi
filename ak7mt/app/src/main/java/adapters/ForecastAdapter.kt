package adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.sedlardavid.ak7mt.R
import serializers.entities.forecast.ForecastData
import services.SystemService
import java.text.SimpleDateFormat
import java.util.*


class ForecastAdapter(private val dataSet: Array<ForecastData>, private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foreSummary: TextView
        val foreTime: TextView
        val foreIcon: ImageView
        val foreTemperature: TextView

        init {
            // Define click listener for the ViewHolder's View.
            foreSummary = view.findViewById(R.id.settTitle)
            foreTime = view.findViewById(R.id.foreTime)
            foreIcon = view.findViewById(R.id.foreIcon)
            foreTemperature = view.findViewById(R.id.foreTemperature)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_forecast, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.foreSummary.text = dataSet[position].summary
        viewHolder.foreTime.text = convertLongToTime(dataSet[position].time.toLong() * 1000)
        viewHolder.foreIcon.setImageDrawable(drawableFromData(dataSet[position].icon))
        viewHolder.foreTemperature.text = "${dataSet[position].temperature} ${SystemService.getUnitsSymbol()}"
    }

    private fun drawableFromData(icon: String): Drawable? {
        val drawableInt = when (icon) {
            "clear-day" -> R.drawable.ic_clear_day
            "clear-night" -> R.drawable.ic_clear_night
            "cloudy" -> R.drawable.ic_cloudy
            "fog" -> R.drawable.ic_fog
            "partly-cloudy-day" -> R.drawable.ic_partly_cloudy_day
            "partly-cloudy-night" -> R.drawable.ic_partly_cloudy_night
            "rain" -> R.drawable.ic_rain
            "sleet" -> R.drawable.ic_sleet
            "snow" -> R.drawable.ic_snow
            "wind" -> R.drawable.ic_wind
            else -> R.drawable.empty_icon
        }

        return context.resources.getDrawable(drawableInt)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM HH:mm")
        return df.parse(date).time
    }
}
