package id.taufikibrahim.themoviedb_visiprimanusantara.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ActivityMovieBinding

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            content.bottomNavigation.setupWithNavController(navController)
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeScreen, R.id.watchListScreen)
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                var selected = ""
                val themes = arrayOf("Dark", "Light", "System")
                var selectedTheme: Int = 2
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    selectedTheme = 0
                } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                    selectedTheme = 1
                }
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.setting_theme))
                    .setSingleChoiceItems(themes, selectedTheme) { _, which ->
                        selectedTheme = which
                        selected = themes[which]

                    }
                    .setNegativeButton(resources.getString(R.string.close)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.apply)) { dialog, _ ->
                        dialog.dismiss()
                        when (selected) {
                            "Dark" -> {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            }
                            "Light" -> {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
                            "System" -> {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            }
                        }
                    }
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_container)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}