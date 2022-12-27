package cz.sedlardavid.eventorr.entities

data class Performer(
    val colors: Any? = null,
    val divisions: Any? = null,
    val genres: List<Genre>? = null,
    val has_upcoming_events: Boolean? = null,
    val home_venue_id: Any? = null,
    val id: Int? = null,
    val image: String,
    val image_attribution: String? = null,
    val image_license: String? = null,
    val image_rights_message: String? = null,
    val location: Any? = null,
    val name: String? = null,
    val num_upcoming_events: Int? = null,
    val popularity: Int? = null,
    val primary: Boolean? = null,
    val score: Double? = null,
    val short_name: String? = null,
    val slug: String? = null,
    val stats: Stats? = null,
    val taxonomies: List<Taxonomy>? = null,
    val type: String? = null,
    val url: String? = null
)