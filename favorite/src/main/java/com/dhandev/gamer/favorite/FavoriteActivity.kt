package com.dhandev.gamer.favorite

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.core.ui.GamesAdapter
import com.dhandev.gamer.detail.DetailActivity
import com.dhandev.gamer.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        Log.i("INFO", "HEY BISA")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("INFO CREATE", "HEY BISA")

        loadKoinModules(favoriteModule)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite"
        val adapterGame = GamesAdapter()
        adapterGame.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
        favoriteViewModel.favoriteGames.observe(this) { data ->
            adapterGame.setData(data)
            Log.e("FAV DATA", data.toString())
            binding.animationView.visibility = if (data.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
        with(binding.rvGamesFav) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterGame
        }
    }
}