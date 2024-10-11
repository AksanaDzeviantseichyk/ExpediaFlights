package utils

import config.ConfigManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import java.util.concurrent.TimeUnit

object DriverFactory {
    private var driver: WebDriver? = null

    fun getDriver(): WebDriver {
        if (driver == null) {
            val browser = ConfigManager.getBrowser()
            val optionsList = ConfigManager.getBrowserOptions()
            when (browser.lowercase()) {
                "chrome" -> {
                    WebDriverManager.chromedriver().setup()
                    val options = ChromeOptions()
                    optionsList.forEach { option ->
                        options.addArguments(option)
                    }
                    driver = ChromeDriver()
                }
                "firefox" -> {
                    WebDriverManager.firefoxdriver().setup()
                    val options = FirefoxOptions()
                    optionsList.forEach { option ->
                        options.addArguments(option)
                    }
                    driver = FirefoxDriver()
                }
                else -> throw IllegalArgumentException("Unsupported browser: $browser")
            }
            driver!!.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        }
        return driver!!
    }

    fun quitDriver() {
        driver?.quit()
        driver = null
    }
}