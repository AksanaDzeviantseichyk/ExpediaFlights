package config

import java.io.FileInputStream
import java.util.*

object ConfigManager {
    private val properties = Properties()

    init {
        val configFile = FileInputStream("src/test/resources/config.properties")
        properties.load(configFile)
    }

    fun getBaseUrl(): String = properties.getProperty("baseUrl")
    fun getBrowser(): String = properties.getProperty("browser")
    fun getBrowserOptions(): List<String> {
        return properties.getProperty("browserOptions", "").split(",").map { it.trim() }
    }
    fun getWaitTime(): Long = properties.getProperty("waitTime").toLong()
}