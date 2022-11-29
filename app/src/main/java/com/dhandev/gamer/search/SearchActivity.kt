package com.dhandev.gamer.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.R
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

        title = resources.getString(R.string.search)

        showSoftKeyboard(binding.edQuery)

        binding.edQuery.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                search(p0)
            }

        })
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }
    }

    private fun search(query: Editable?) {
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