package com.dhandev.gamer.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dhandev.gamer.R
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.databinding.ActivityDetailBinding
import com.dhandev.gamer.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val detailGames = intent.getParcelableExtra<Games>(EXTRA_DATA)

        showDetailgames(detailGames)

    }

    private fun showDetailgames(detailGames: Games?) {
        detailGames?.let {
            supportActionBar?.title = it.name
            binding.content.tvSuggestion.text = it.suggestionsCount.toString()
            Glide.with(this)
                .load(it.backgroundImage)
                .into(binding.ivDetailImage)
            val rating = if (it.rating != null) it.rating!! * 100/5 else 0
            binding.content.progRating.progress = rating.toInt()
            binding.content.tvRating.text = it.rating.toString()
            binding.content.progMeta.progress = it.metacritic?.toInt() ?: 0
            binding.content.tvMeta.text = it.metacritic.toString()

            var statusFavorite = detailGames.isFavorite
            if (statusFavorite != null) {
                setStatusFavorite(statusFavorite)
            }
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite!!
                detailViewModel.setFavorite(detailGames, statusFavorite!!)
                setStatusFavorite(statusFavorite!!)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
        }
    }


    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}