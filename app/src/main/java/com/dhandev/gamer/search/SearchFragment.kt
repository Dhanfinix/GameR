package com.dhandev.gamer.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.core.data.Resource
import com.dhandev.gamer.core.ui.GamesAdapter
import com.dhandev.gamer.databinding.FragmentSearchBinding
import com.dhandev.gamer.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val searchViewModel: SearchViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val query = binding.edQuery.text
            binding.btnSearch.setOnClickListener {
                search(query)
            }
        }
    }

    private fun search(query: Editable) {
        val adapterGame = GamesAdapter()
        adapterGame.onItemClick = {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
        searchViewModel.setSearch(query.toString()).observe(viewLifecycleOwner) { games ->
            if (games != null){
                when(games){
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.animationView.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.animationView.visibility = View.GONE
                        adapterGame.setData(games.data)
                        Log.e("Search Data", games.data.toString())
                        //TODO: KNOWN BUG -> Search show all games from database, after navigate to other screen and back to it
                    }
                    is Resource.Error -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}