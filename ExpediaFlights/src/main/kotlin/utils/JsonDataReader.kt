package utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import models.FlightSearchData
import java.io.File

object JsonDataReader {
    fun getFlightSearchData(): FlightSearchData {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(File("src/test/resources/testdata.json"), FlightSearchData::class.java)
    }
}