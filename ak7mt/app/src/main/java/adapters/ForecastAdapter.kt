package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.sedlardavid.ak7mt.R
import serializers.entities.forecast.ForecastData
import services.SystemService
import java.text.SimpleDateFormat
import java.util.*


class ForecastAdapter(private val dataSet: Array<ForecastData>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foreSummary: TextView
        val foreTime: TextView
        val foreIcon: TextView
        val foreTemperature: TextView

        init {
            // Define click listener for the ViewHolder's View.
            foreSummary = view.findViewById(R.id.foreSummary)
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
        viewHolder.foreIcon.text = dataSet[position].icon
        viewHolder.foreTemperature.text = "${dataSet[position].temperature} ${SystemService.getUnits()}"
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
