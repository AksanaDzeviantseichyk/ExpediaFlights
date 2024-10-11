package pages

import config.ConfigManager
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SearchResultsPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getWaitTime()))
    private val flyingFromFieldLokator = By.xpath("//button[contains(@aria-label, 'Flying from')]")
    private val flyingToFieldLokator = By.xpath("//button[contains(@aria-label, 'Flying to')]")
    private val departingFieldLokator = By.xpath("//button[contains(@aria-label, 'Departing')]")
    private val returningFieldLokator = By.xpath("//button[contains(@aria-label, 'Returning')]")
    private val searchResultsListLokator = By.cssSelector("ul[data-test-id='listings']")
    private val nonstopCheckboxLocator = By.xpath("//*[contains(@id, 'NUM_OF_STOPS-0')]")
    private val nonstopLabelLocator = By.xpath("//div[input[contains(@id, 'NUM_OF_STOPS-0')]]//label")
    private val firstSearchResultLocator = By.cssSelector("button[data-test-id='select-link']")
    private val firstSelectButtonLokator = By.cssSelector("button[data-test-id='select-button']")
    private val loadingBarLokator = By.cssSelector("[class='uitk-loading-bar-container']")

    private val flyingFromField: WebElement
        get() = driver.findElement(flyingFromFieldLokator)
    private val flyingToField: WebElement
        get() = driver.findElement(flyingToFieldLokator)
    private val departingField: WebElement
        get() = driver.findElement(departingFieldLokator)
    private val returningField: WebElement
        get() = driver.findElement(returningFieldLokator)
    private val searchResults: WebElement
        get() = driver.findElement(searchResultsListLokator)
    private val nonstopCheckbox: WebElement
        get() = driver.findElement(nonstopCheckboxLocator)
    private val nonstopLabel: WebElement
        get() = driver.findElement(nonstopLabelLocator)
    private val firstSelectButton: WebElement
        get() = driver.findElement(firstSelectButtonLokator)
    private val firstSearchResult: WebElement
        get() = driver.findElement(firstSearchResultLocator)

    fun isSearchResultsPageOpen(departureCity: String, arrivalCity: String): Boolean {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingBarLokator))
        val expectedCity = extractCityCode(departureCity) + " to " + extractCityCode(arrivalCity) + " flights"
        return driver.title == expectedCity
    }

    fun isSearchResultsDisplayed(): Boolean{
         return searchResults.isDisplayed()
    }

    fun getDepartureAirport(): String{
        wait.until { driver.findElement(flyingFromFieldLokator).isDisplayed }
        val text = flyingFromField.text
        return text
    }

    fun getDeparturingDate(): String{
        val text = departingField.text
        return text
    }

    fun getReturningDate(): String{
        val text = returningField.text
        return text
    }

    fun getArrivalAirport(): String{
        val text = flyingToField.text
        return text
    }

    fun isNonstopCheckboxDisplayed(): Boolean {
        return nonstopCheckbox.isDisplayed()
    }

    fun toggleNonstopCheckbox() {
        if (!nonstopCheckbox.isSelected) {
            nonstopLabel.click()
        }
    }

    fun clickFirstSearchResult() {
        firstSearchResult.click()
    }

    fun clickFirstSelectButton() {
        firstSelectButton.click()
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingBarLokator))
    }

    private fun extractCityCode(title: String): String? {

        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(title)

        return matchResult?.groups?.get(1)?.value
    }
}