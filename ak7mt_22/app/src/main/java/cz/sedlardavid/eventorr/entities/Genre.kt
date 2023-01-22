package cz.sedlardavid.eventorr.entities

data class Genre(
    val document_source: DocumentSource,
    val id: Int,
    val image: String,
    val images: Images,
    val name: String,
    val primary: Boolean,
    val slug: String
)