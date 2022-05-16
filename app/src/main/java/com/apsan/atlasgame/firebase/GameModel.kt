package com.apsan.atlasgame.firebase

data class GameModel(
    val p1 : String,
    val p2 : String,
    val status: Int,
    val words_p1 : List<String>,
    val words_p2 : List<String>
)
