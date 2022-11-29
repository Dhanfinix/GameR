package com.dhandev.gamer.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.R
import com.dhandev.gamer.core.ui.GamesAdapter
import com.dhandev.gamer.databinding.ActivityMainBinding
import com.dhandev.gamer.detail.DetailActivity
import com.dhandev.gamer.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_open_anim
    ) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_close_anim
    ) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.from_bottom_anim
    ) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.to_bottom_anim
    ) }
    private var clicked = false
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gamesAdapter = GamesAdapter()
        gamesAdapter.onItemClick = {selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        mainViewModel.games.observe(this) { games ->
            if (games != null){
                when(games){
                    is com.dhandev.gamer.core.data.Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is com.dhandev.gamer.core.data.Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        gamesAdapter.setData(games.data)
                        Log.e("Home Data", games.data.toString())
                    }
                    is com.dhandev.gamer.core.data.Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = games.message ?: getString(R.string.maaf_terjadi_eror)
                    }
                }
            }
        }
        with(binding.rvTourism) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = gamesAdapter
        }

        binding.mainFab.setOnClickListener {
            onFavClicked()
        }

        binding.searchFab.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.favFab.setOnClickListener {
            val uri = Uri.parse("gamerapp://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun onFavClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked){
            binding.searchFab.isClickable = true
            binding.favFab.isClickable = true
        } else {
            binding.searchFab.isClickable = false
            binding.favFab.isClickable = false
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.mainFab.animation = rotateOpen
            binding.searchFab.animation = fromBottom
            binding.favFab.animation = fromBottom
        } else {
            binding.mainFab.animation = rotateClose
            binding.searchFab.animation = toBottom
            binding.favFab.animation = toBottom
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            binding.favFab.visibility = View.VISIBLE
            binding.searchFab.visibility = View.VISIBLE
        } else {
            binding.favFab.visibility = View.INVISIBLE
            binding.searchFab.visibility = View.INVISIBLE
        }
    }
}