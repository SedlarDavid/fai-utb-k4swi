package cz.sedlardavid.eventorr.entities

data class Taxonomy(
    val document_source: DocumentSource,
    val id: Int,
    val name: String,
    val parent_id: Int,
    val rank: Int
)