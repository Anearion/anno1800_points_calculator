package com.anesoft.anno1800influencecalculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.filters.SmallTest
import com.anesoft.anno1800influencecalculator.repository.local.AppDatabase
import com.anesoft.anno1800influencecalculator.repository.local.dao.PlayerDao
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.contains
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Before
    fun setup() {
        hiltRule.inject()
        playerDao = database.playerDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val player = Player(
            name = "Clint",
            gender = "male",
            id = 1
        )
        playerDao.insertAll(player)
        val allUsers = playerDao.getAll()
        assertThat(allUsers, contains(player))
    }

}