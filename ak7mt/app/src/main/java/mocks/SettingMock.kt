package mocks

import entities.Settings
import tools.Language
import tools.Units

class SettingMock {
    companion object {
        val settings: Settings = Settings(Language.EN, Units.METRIC, "Zlin")
    }
}