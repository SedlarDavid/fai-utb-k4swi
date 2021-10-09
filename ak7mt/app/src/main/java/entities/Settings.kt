package entities

import tools.Language
import tools.Units


data class Settings(
    val language: Language,
    val units: Units,
    val primaryCity: String
)
