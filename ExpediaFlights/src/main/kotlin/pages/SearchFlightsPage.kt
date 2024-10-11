package pages

import config.ConfigManager
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchFlightsPage(private val driver: WebDriver) {

    private val title = "Cheap Flights, Plane Tickets & Airline Deals - Expedia"
    private val wait = WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getWaitTime()))
    private val leavingFromFieldLokator = By.cssSelector("button[aria-label='Leaving from']")
    private val leavingFromInputFieldLokator = By.xpath("//*[@id='search-location-input-field']//input")

    private val goingToFieldLokator = By.cssSelector("button[aria-label='Going to']")
    private val goingToInputFieldLokator = By.xpath("//*[@id='search-location-input-field']//input")

    private val datesFieldLokator = By.id("date_form_field-btn")
    private val datesPickerDoneButtonLokator = By.cssSelector("button[data-stid='apply-date-picker']")
    private fun getDateButtonLocator(date: String): By {
        return By.xpath("//button[contains(@aria-label, '$date')]")
    }

    private val trevelersFieldLokator = By.cssSelector("button[data-stid='open-room-picker']")
    private val increaseAdultsButtonLokator = By.xpath("//button[descendant::*[@id='traveler_selector_adult_step_input-increase-title']]")
    private val trevelersSelectorDoneButtonLokator = By.id("travelers_selector_done_button")
    private val numbersOfAdultsLokator = By.id("traveler_selector_adult_step_input")

    private val searchButtonLokator = By.id("search_button")

    private val leavingFromField: WebElement
        get() = driver.findElement(leavingFromFieldLokator)

    private val leavingFromInputField: WebElement
        get() = driver.findElement(leavingFromInputFieldLokator)

    private val goingToField: WebElement
        get() = driver.findElement(goingToFieldLokator)

    private val goingToInputField: WebElement
        get() = driver.findElement(goingToInputFieldLokator)

    private val datesField: WebElement
        get() = driver.findElement(datesFieldLokator)

    private val datesPickerDoneButton: WebElement
        get() = driver.findElement(datesPickerDoneButtonLokator)

    private val trevelersField: WebElement
        get() = driver.findElement(trevelersFieldLokator)

    private val increaseAdultsButton: WebElement
        get() = driver.findElement(increaseAdultsButtonLokator)

    private val trevelersSelectorDoneButton: WebElement
        get() = driver.findElement(trevelersSelectorDoneButtonLokator)

    private val numbersOfAdults: WebElement
        get() = driver.findElement(numbersOfAdultsLokator)

    private val searchButton: WebElement
        get() = driver.findElement(searchButtonLokator)

    fun isSearchFlightsPageOpen(): Boolean {
        return driver.title == title
    }

    fun clickLeavingFromField() {
        leavingFromField.click()
    }

    fun enterTextToLeavingFromInputField(value: String) {
        wait.until { driver.findElement(leavingFromInputFieldLokator).isDisplayed }
        leavingFromInputField.click()
        leavingFromInputField.sendKeys(value)
        leavingFromInputField.sendKeys(Keys.ENTER)
    }

    fun clickGoingToField() {
        goingToField.click()
    }

    fun enterTextToGoingToInputField(value: String) {
        wait.until { driver.findElement(goingToInputFieldLokator).isDisplayed }
        goingToInputField.click()
        goingToInputField.sendKeys(value)
        goingToInputField.sendKeys(Keys.ENTER)
    }

    fun clickDatesField() {
        datesField.click()
    }

    fun clickOnDateButton(date: LocalDate) {
        val dateButtonLocator = getDateButtonLocator(formatDateForButton(date))
        val dateButton: WebElement = driver.findElement(dateButtonLocator)
        dateButton.click()
    }

    private fun formatDateForButton(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        return date.format(formatter)
    }

    fun clickDatesPickerDoneButton() {
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", datesPickerDoneButton)
    }

    fun clickTrevelersField() {
        trevelersField.click()
    }

    fun increaseAdults(times: Int) {
        for (i in 1..times) {
            increaseAdultsButton.click()
        }
    }

    fun clickTrevelersSelectorDoneButton() {
        trevelersSelectorDoneButton.click()
    }

    fun getNumbersOfAdults(): Int {
        val value = numbersOfAdults.getAttribute("value")
        return value.toInt()
    }

    fun clickSearchButton() {
        searchButton.click()
    }
}