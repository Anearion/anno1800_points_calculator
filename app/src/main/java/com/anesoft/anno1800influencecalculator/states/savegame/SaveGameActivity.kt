package com.anesoft.anno1800influencecalculator.states.savegame

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goBack()
                }
            })

    }

    override fun onSupportNavigateUp(): Boolean {

        return goBack()
    }

    private fun goBack(): Boolean {
        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("Are you finished saving scores ?")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("Stay here", null)
            .show()
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false;
    }

}