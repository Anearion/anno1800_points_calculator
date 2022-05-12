package com.anesoft.anno1800influencecalculator.states.savegame

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.anesoft.anno1800influencecalculator.R
import com.anesoft.anno1800influencecalculator.databinding.ActivitySaveGameBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SaveGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener {
                onSupportNavigateUp()
            }.build()

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {

        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("Going back will cancel the game.")
            .setPositiveButton("Ok"){ _, _ ->
                finish()
            }
            .setNegativeButton("Stay here", null)
            .show()
        return true
    }

}