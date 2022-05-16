package com.apsan.atlasgame.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apsan.atlasgame.R
import com.apsan.atlasgame.databinding.FragmentGameBinding
import com.apsan.atlasgame.repo.CountriesRepo
import com.apsan.atlasgame.repo.IndianCitiesRepo
import com.apsan.atlasgame.repo.WorldCities
import com.apsan.atlasgame.retrofit.APIMethods
import com.apsan.atlasgame.retrofit.RetrofitBuilder
import com.apsan.atlasgame.viewmodels.GameViewModel
import com.apsan.atlasgame.viewmodels.GameViewModelFactory
import com.hoc081098.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch

class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding by viewBinding(FragmentGameBinding::bind)
    val TAG = "TAG"
    lateinit var gameViewModel: GameViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root

        init()

        //generateRandomAlphabet()
        binding.countrySearch.setOnClickListener {
            gameViewModel.country_search(binding.editText.text.toString())
            binding.editText.setSelectAllOnFocus(true)
            binding.editText.selectAll()
        }

        gameViewModel.toastLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        gameViewModel.lastLetter.observe(
            viewLifecycleOwner,
            {
                binding.guide.text =
                    "Type a country or indian location starting with ${it.toUpperCase()}"
            })

        gameViewModel.lastLocationByRepo.observe(viewLifecycleOwner,
            { binding.resultCountry.text = it })

        gameViewModel.yourScore.observe(viewLifecycleOwner,
            { binding.score.text = addZeroes(it) })
        binding.forfeitBtn.setOnClickListener {
            ForfeitAnswers()
        }
        binding.closeDialog.setOnClickListener {
            binding.forfeitDialogue.visibility = View.INVISIBLE
            gameViewModel.refreshValues()
        }
        binding.drawBtn.setOnClickListener {
            binding.forfeitDialogue.visibility = View.INVISIBLE
            gameViewModel.refreshValues()
        }
        binding.changeLetterBtn.setOnClickListener {
            binding.forfeitDialogue.visibility = View.INVISIBLE
            gameViewModel.lastLetter.value = generateRandomAlphabet(gameViewModel.lastLetter.value)
            gameViewModel.lastLocationByRepo.value = ""
        }
    }

    private fun init() {
        gameViewModel = ViewModelProvider(
            viewModelStore,
            GameViewModelFactory(
                IndianCitiesRepo(requireActivity().application),
                CountriesRepo(requireActivity().application),
                WorldCities(requireActivity().application)
            )
        ).get(GameViewModel::class.java)
        binding.guide.text =
            "Type a country or indian location starting with ${generateRandomAlphabet()}"
    }

    fun generateRandomAlphabet(excludeAlphabet: Char? = null): Char {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var alphabet: Char? = null
        if (excludeAlphabet == null) {
            val randomNumb = (0..25).random()
            alphabet = source[randomNumb]
        } else {
            alphabet = source.replace(excludeAlphabet.toString(), "")[(0..24).random()]
        }
        gameViewModel.lastLetter.value = alphabet
        return alphabet

    }

    private fun addZeroes(score: Int): String {
        if (score / 10 < 1) {
            return "0$score"
        }
        return "$score"
    }

    private fun ForfeitAnswers() {
        var string = "Below locations start with ${gameViewModel.lastLetter.value}\n"
        gameViewModel.forfeit()
        for (i in (0 until gameViewModel.FilteredLocations.size)) {
            if (i < 8) {
                string = string.plus("-> ${gameViewModel.FilteredLocations[i].name}\n")
            }
        }
        if (gameViewModel.FilteredLocations.size == 0) {
            string = "No Locations Found -"
            binding.changeLetterBtn.visibility = View.VISIBLE
            binding.drawBtn.visibility = View.VISIBLE
            binding.closeDialog.visibility = View.GONE
        }
        binding.forfeitDialogue.visibility = View.VISIBLE
        binding.answers.text = string
    }

}