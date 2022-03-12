package com.anesoft.anno1800influencecalculator.model

import com.anesoft.anno1800influencecalculator.repository.local.entity.Score

data class Game(val scoreList: List<Score>, val winner: String, val numberOfPlayers : Int) {


}