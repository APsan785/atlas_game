package com.apsan.atlasgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apsan.atlasgame.databinding.ActivityMainBinding
import com.apsan.atlasgame.fragments.GameFragment
import com.hoc081098.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    val TAG = "TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding.root
        supportFragmentManager.beginTransaction().replace(R.id.frame, GameFragment()).commit()
        supportActionBar?.hide()
    }

}