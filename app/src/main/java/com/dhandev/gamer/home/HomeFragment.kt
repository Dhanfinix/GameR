package com.dhandev.gamer.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.gamer.R
import com.dhandev.gamer.core.data.Resource
import com.dhandev.gamer.core.ui.GamesAdapter
import com.dhandev.gamer.core.ui.ViewModelFactory
import com.dhandev.gamer.databinding.FragmentHomeBinding
import com.dhandev.gamer.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val gamesAdapter = GamesAdapter()
            gamesAdapter.onItemClick = {selectedData ->
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
//                Toast.makeText(requireActivity(), selectedData.id.toString(), Toast.LENGTH_SHORT).show()
            }

            val factory = ViewModelFactory.getInstance(requireActivity())
            homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            homeViewModel.games.observe(viewLifecycleOwner) { games ->
                if (games != null){
//                    Toast.makeText(requireActivity(), games.data?.get(0).toString(), Toast.LENGTH_SHORT).show()
                    when(games){
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            gamesAdapter.setData(games.data)
                        }
                        is Resource.Error -> {
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
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}