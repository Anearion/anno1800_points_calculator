package com.anesoft.anno1800influencecalculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.filters.SmallTest
import com.anesoft.anno1800influencecalculator.repository.local.AppDatabase
import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import com.anesoft.anno1800influencecalculator.repository.local.dao.ScoreDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import com.anesoft.anno1800influencecalculator.repository.local.entity.Score
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class PlayerDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var playerDao: PlayerDao
    private lateinit var scoreDao: ScoreDao

    @Before
    fun setup() {
        hiltRule.inject()
        playerDao = database.playerDao()
        scoreDao = database.scoreDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val player = Player(
            name = "Anto",
            gender = "male"
        )

        val player2 = Player(
            name = "Fede",
            gender = "female"
        )

        playerDao.insertAll(player, player2)
        val playerRes1 = playerDao.getByName("Anto")
        val playerRes2 = playerDao.getByName("Fede")

        val score1 = createDummyScore(playerRes1, 1)
        val score2 = createHighDummyScore(playerRes2, 1)
        val score3 = createDummyScore(playerRes2, 2)

        scoreDao.insert(score1)
        scoreDao.insert(score2)
        scoreDao.insert(score3)

        val allUsers = playerDao.getAll()
        assertThat(allUsers, contains(player, player2))

        val scoreResult1 = scoreDao.getAll()
        assertThat(scoreResult1.size, `is`(3))

        val map = scoreResult1.groupBy { it.gameId }
        assertThat(map.keys.size, `is`(2))
    }

    private fun createDummyScore(playerRes1: Player, gameId : Int): Score {
        val score = Score(
            10,
            5,
            4,
            8,
            2,
            7,
            0,
            3,
            4,
            5,
            3,
            gameId,
            playerRes1.id
        )
        return score
    }

    private fun createHighDummyScore(playerRes1: Player, gameId : Int): Score {
        val score = Score(
            10,
            10,
            10,
            8,
            2,
            7,
            0,
            3,
            4,
            5,
            3,
            gameId,
            playerRes1.id
        )
        return score
    }

}