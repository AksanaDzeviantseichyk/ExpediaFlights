# Selenium Flight Search Tests

This repository contains Selenium test automation code for searching flights on Expedia.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **macOS**: Ensure you are using macOS for running the tests.
- **Java**: You must have Java Development Kit (JDK) installed (version 11 or later). You can check your Java version with the following command:
  ```bash
  java -version

- Maven: Apache Maven is required to manage dependencies and run the project. You can install it using Homebrew:

brew install maven

## Setup Instructions
Clone the Repository: Clone this repository to your local machine using Git:

git clone <repository-url>
cd <repository-name>

Install Dependencies: Use Maven to install the required dependencies:

mvn install

Configure Test Data: The test data is located in src/test/resources/testdata.json and can be modified according to your needs. Ensure the flight search data is accurate for the tests you want to run.

Configure Application Settings: Update the configuration settings in src/test/resources/config.properties to set the base URL and desired browser. For example:

baseUrl=https://www.expedia.com/Flights
browser=firefox
browserOptions=--private
waitTime=10

## Running Tests
To run the tests, use the following command:

mvn test

This command will execute all the test cases defined in the project. Note that the WebDriver will be automatically downloaded and configured using WebDriverManager.

## Test Data
The following test data is used in the tests located in src/test/resources/testdata.json:

{
  "departureCity": "Orlando, FL (MCO)",
  "arrivalCity": "New York, NY (JFK)",
  "daysFromTodayDeparture": 10,
  "daysFromDepartureDate": 5,
  "travelers": {
    "adults": 2
  }
}

## Explanation of Test Data:
- departureCity: The city from which the flight departs.
- arrivalCity: The destination city.
- daysFromTodayDeparture: The number of days from today for the departure date.
- daysFromDepartureDate: The number of days from the departure date for the return flight.
- travelers: The number of adults, children, and infants traveling.