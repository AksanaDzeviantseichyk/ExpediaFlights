package tests

import config.ConfigManager
import utils.DriverFactory
import org.openqa.selenium.WebDriver
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import pages.SearchFlightsPage
import pages.SearchResultsPage
import utils.JsonDataReader
import java.time.format.DateTimeFormatter

class FlightSearchTests {
    private val driver: WebDriver by lazy { DriverFactory.getDriver() }

    @BeforeClass
    fun setup() {
        driver.manage().window().maximize()
    }

    @Test
    fun testFlightSearch() {
        //Open the URL of Expedia's flight search page
        driver.get(ConfigManager.getBaseUrl())
        val searchFlightsPage = SearchFlightsPage(driver)
        Assert.assertTrue(searchFlightsPage.isSearchFlightsPageOpen(), "Flight Search Page don't open")

//        //Input search criteria
        val testData = JsonDataReader.getFlightSearchData()
        searchFlightsPage.clickLeavingFromField()
        searchFlightsPage.enterTextToLeavingFromInputField(testData.departureCity)
        searchFlightsPage.clickGoingToField()
        searchFlightsPage.enterTextToGoingToInputField(testData.arrivalCity)
        searchFlightsPage.clickDatesField()
        searchFlightsPage.clickOnDateButton(testData.departureDate)
        searchFlightsPage.clickOnDateButton(testData.returnDate)
        searchFlightsPage.clickDatesPickerDoneButton()
        searchFlightsPage.clickTrevelersField()
        val numberOfAdults = searchFlightsPage.getNumbersOfAdults()
        searchFlightsPage.increaseAdults(testData.travelers.adults - numberOfAdults)
        searchFlightsPage.clickTrevelersSelectorDoneButton()

//        //Click on the Search button to submit the flight search.
        searchFlightsPage.clickSearchButton()

        //Verify search results:
        val searchResultsPage = SearchResultsPage(driver)
        Assert.assertTrue(searchResultsPage.isSearchResultsPageOpen(testData.departureCity, testData.arrivalCity),
            "Search Results Page don't open")
        Assert.assertTrue(searchResultsPage.isSearchResultsDisplayed(),
            "Search Result List isn't displayed")
        var actualDepartureAirport = searchResultsPage.getDepartureAirport()
        var actualArrivalAirport = searchResultsPage.getArrivalAirport()
        var actualDeparturingDate = searchResultsPage.getDeparturingDate()
        var actualReturningDate = searchResultsPage.getReturningDate()
        val dateFormatter = DateTimeFormatter.ofPattern("MMM dd")
        val expectedDeparturingDateString = testData.departureDate.format(dateFormatter)
        val expectedReturningDateString = testData.returnDate.format(dateFormatter)
        Assert.assertTrue(actualDepartureAirport.contains(testData.departureCity.replace(")", "")),
            "Departure airports don't match")
        Assert.assertTrue(actualArrivalAirport.contains(testData.arrivalCity.replace(")", "")),
            "Arrival airports don't match")
        Assert.assertTrue(actualDeparturingDate.equals(expectedDeparturingDateString),
            "Departuring dates don't match")
        Assert.assertTrue(actualReturningDate.equals(expectedReturningDateString),
            "Returning dates don't match")

        //Apply a filter to show only non-stop flights, if available.
        if(searchResultsPage.isNonstopCheckboxDisplayed()){
            searchResultsPage.toggleNonstopCheckbox()
        }

        //From the filtered results, select the first flight displayed.
        searchResultsPage.clickFirstSearchResult()

        //Click on Select or Continue to proceed to the next page
        searchResultsPage.clickFirstSelectButton()

        //Ensure that the selected flight details (departure/arrival airport and dates) are correctly displayed on the next page.
        actualDepartureAirport = searchResultsPage.getDepartureAirport()
        actualArrivalAirport = searchResultsPage.getArrivalAirport()
        actualDeparturingDate = searchResultsPage.getDeparturingDate()
        actualReturningDate = searchResultsPage.getReturningDate()
        Assert.assertTrue(actualDepartureAirport.contains(testData.departureCity.replace(")", "")),
            "Departure airports don't match")
        Assert.assertTrue(actualArrivalAirport.contains(testData.arrivalCity.replace(")", "")),
            "Arrival airports don't match")
        Assert.assertTrue(actualDeparturingDate.equals(expectedDeparturingDateString),
            "Departuring dates don't match")
        Assert.assertTrue(actualReturningDate.equals(expectedReturningDateString),
            "Returning dates don't match")
    }
    
    @AfterClass
    fun tearDown() {
       DriverFactory.quitDriver()
    }
}