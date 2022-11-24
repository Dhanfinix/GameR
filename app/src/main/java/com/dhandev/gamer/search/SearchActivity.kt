package com.dhandev.gamer.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.core.ui.GamesAdapter
import com.dhandev.gamer.databinding.ActivitySearchBinding
import com.dhandev.gamer.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = binding.edQuery.text
        binding.btnSearch.setOnClickListener {
            search(query)
        }
    }

    private fun search(query: Editable) {
        val adapterGame = GamesAdapter()
        adapterGame.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
        searchViewModel.setSearch(query.toString()).observe(this) { games ->
            if (games != null){
                when(games){
                    is com.dhandev.gamer.core.data.Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.animationView.visibility = View.VISIBLE
                    }
                    is com.dhandev.gamer.core.data.Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.animationView.visibility = View.GONE
                        adapterGame.setData(games.data)
                        Log.e("Search Data", games.data.toString())
                        //TODO: KNOWN BUG -> Search show all games from database, after navigate to other screen and back to it
                    }
                    is com.dhandev.gamer.core.data.Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.animationView.visibility = View.VISIBLE
                    }
                }
            }
        }
        with(binding.rvGames) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterGame
        }
    }
}