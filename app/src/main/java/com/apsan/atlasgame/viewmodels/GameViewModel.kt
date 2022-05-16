package com.apsan.atlasgame.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.apsan.atlasgame.MyApplication
import com.apsan.atlasgame.fragments.GameFragment
import com.apsan.atlasgame.repo.CountriesRepo
import com.apsan.atlasgame.repo.IndianCitiesRepo
import com.apsan.atlasgame.repo.WorldCities
import com.apsan.atlasgame.retrofit.APIMethods
import com.apsan.atlasgame.retrofit.LocationObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import retrofit2.awaitResponse

// API url - https://restcountries.eu/rest/v2/name/

class GameViewModel(
    private val indianCitiesRepo: IndianCitiesRepo,
    private val countriesRepo: CountriesRepo,
    private val worldCities: WorldCities
) : ViewModel() {

    private var LocationUsed = mutableListOf<LocationObj>()
    var FilteredLocations = mutableListOf<LocationObj>()

    val lastLetter: MutableLiveData<Char> = MutableLiveData()
    var lastLocationByRepo: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: MutableLiveData<String> = MutableLiveData()
    val yourScore: MutableLiveData<Int> = MutableLiveData(0)

    fun country_search(input: String) {

        FilteredLocations.clear()

        if (input[0].toUpperCase() != lastLetter.value) {
            toastLiveData.value = "Location starting with Wrong letter"
        } else if (LocationUsed.contains(LocationObj(input))) {
            toastLiveData.value = "This Country has already been used"
        } else {

            viewModelScope.launch(Dispatchers.Main) {

                var location_found = false
                var enteredLocationFound = false

                val letter_to_start = input.last()
                val response1 = countriesRepo.CountriesList.contains(
                    input.substring(0, 1).toUpperCase() + input.substring(1)
                )
                val response2 = indianCitiesRepo.citiesList.contains(
                    input.substring(0, 1).toUpperCase() + input.substring(1))
                val response3 = worldCities.WorldCitiesList.contains(
                    input.substring(0,1).toUpperCase() + input.substring(1)
                )
                if (response1 || response2 || response3) {

                    LocationUsed.add(LocationObj(input))

                    enteredLocationFound = true
                    val list_response = countriesRepo.getCountriesWithLetter(letter_to_start)

                    for (country in list_response) {
                        if (!LocationUsed.contains(LocationObj(country))) {
                            location_found = true
                            FilteredLocations.add(LocationObj(country))

                        }
                    }

                    val IndianCitiesList = indianCitiesRepo.getIndianCitiesWithLetter(
                        letter_to_start
                    )
                    for (cities in IndianCitiesList) {
                        if (!LocationUsed.contains(LocationObj(cities))) {

                            FilteredLocations.add(LocationObj(cities))

                            location_found = true

                        }
                    }

                }

                if (!location_found && enteredLocationFound) {

                    toastLiveData.value = "No Location found, Let's change the alphabet"
                    changeAlphabet()

                }
                if (!enteredLocationFound) {
                    toastLiveData.value = "Entered Location Name is Incorrect"
                }


                if (FilteredLocations.isNotEmpty()) {
                    selectRandomLocation()
                }

            }
        }
    }

    private fun changeAlphabet() {

    }

    private fun selectRandomLocation() {
        val randLocation = FilteredLocations.random()
        lastLocationByRepo.value = randLocation.name
        lastLetter.value = randLocation.name.last().toUpperCase()
        LocationUsed.add(randLocation)
        yourScore.value = yourScore.value?.plus(1)
    }

    fun forfeit() {
        FilteredLocations.clear()
        val list_response = countriesRepo.getCountriesWithLetter(lastLetter.value!!)

        for (country in list_response) {
            if (country[0] == lastLetter.value!!.toUpperCase()) {
                if (!LocationUsed.contains(LocationObj(country))) {
                    FilteredLocations.add(LocationObj(country))
                }
            }
        }
        val IndianCitiesList = indianCitiesRepo.getIndianCitiesWithLetter(
            lastLetter.value
        )
        for (cities in IndianCitiesList) {
            if (!LocationUsed.contains(LocationObj(cities))) {

                FilteredLocations.add(LocationObj(cities))

            }
        }
        FilteredLocations.shuffle()
    }

    fun refreshValues() {
        lastLetter.value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"[(0..25).random()]
        yourScore.value = 0
        lastLocationByRepo.value = ""
        LocationUsed.clear()
        FilteredLocations.clear()
    }


}