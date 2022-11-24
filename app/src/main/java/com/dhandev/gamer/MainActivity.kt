package com.dhandev.gamer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dhandev.gamer.databinding.ActivityMainBinding
import com.dhandev.gamer.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        //TODO: CHANGE NAVIGATION TO DRAWER
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                val uri = Uri.parse("gamerapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> true
        }
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var fragment: Fragment? = null
//        var title = getString(R.string.app_name)
//        when (item.itemId) {
//            R.id.navigation_home -> {
//                fragment = HomeFragment()
//                title = getString(R.string.app_name)
//            }
//            R.id.navigation_search -> {
//                fragment = FavoriteFragment()
//                title = getString(R.string.favorite)
//            }
//            R.id.navigation_favorite -> {
//                val uri = Uri.parse("gamerapp://favorite")
//                startActivity(Intent(Intent.ACTION_VIEW, uri))
//            }
//        }
//        if (fragment != null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment_activity_main, fragment)
//                .commit()
//        }
//        supportActionBar?.title = title
//        return true
//    }
}