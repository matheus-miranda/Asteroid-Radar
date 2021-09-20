package com.udacity.asteroidradar.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.presentation.adapter.AsteroidAdapter
import com.udacity.asteroidradar.presentation.adapter.ClickHandler
import com.udacity.asteroidradar.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding
    private val asteroidAdapter by lazy {
        AsteroidAdapter(ClickHandler {
            viewModel.navigateToDetails(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        bindRecyclerView()
        bindObservers()

        return binding.root
    }

    private fun bindRecyclerView() {
        binding.statusLoadingWheel.isVisible = true
        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.asteroid.observe(viewLifecycleOwner, {
            it?.let {
                binding.statusLoadingWheel.visibility = View.GONE
                asteroidAdapter.submitList(it)
            }
        })
    }

    private fun bindObservers() {
        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(
                        asteroid
                    )
                )
                viewModel.navigateToDetailsComplete()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
