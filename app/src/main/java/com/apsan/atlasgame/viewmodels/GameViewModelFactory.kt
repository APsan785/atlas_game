package com.apsan.atlasgame.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apsan.atlasgame.repo.CountriesRepo
import com.apsan.atlasgame.repo.IndianCitiesRepo
import com.apsan.atlasgame.repo.WorldCities
import com.apsan.atlasgame.retrofit.APIMethods

class GameViewModelFactory(
    private val indianCitiesRepo: IndianCitiesRepo,
    private val countriesRepo: CountriesRepo,
    private val worldCities: WorldCities
) : ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel( indianCitiesRepo, countriesRepo, worldCities) as T
    }
}