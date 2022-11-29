package com.dhandev.gamer.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dhandev.gamer.R
import com.dhandev.gamer.core.domain.model.Games
import com.dhandev.gamer.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            binding.apply {
                content.tvSuggestion.text = it.suggestionsCount.toString()
                Glide.with(this@DetailActivity)
                    .load(it.backgroundImage)
                    .into(ivDetailImage)
                val rating = if (it.rating != null) it.rating!! * 100/5 else 0
                content.progRating.progress = rating.toInt()
                content.tvRating.text = it.rating.toString()
                content.progMeta.progress = it.metacritic?.toInt() ?: 0
                content.tvMeta.text = it.metacritic.toString()
            }

            var statusFavorite = detailGames.isFavorite
            if (statusFavorite != null) {
                setStatusFavorite(statusFavorite)
            }
            binding.fab.setOnClickListener {
                Toast.makeText(this, if (statusFavorite == true) getString(R.string.removed_fav) else getString(
                                    R.string.add_to_favorite), Toast.LENGTH_SHORT).show()
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