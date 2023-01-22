package cz.sedlardavid.eventorr.entities

data class TaxonomyX(
    val id: Int,
    val name: String,
    val parent_id: Int,
    val rank: Int
)