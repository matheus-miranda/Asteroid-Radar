package com.udacity.asteroidradar.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.State
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        bindObservers()

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun bindObservers() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                State.Loading -> Timber.e("Loading")
                is State.Error -> Timber.e(state.error.message)
                is State.Success -> {
                    Timber.e(state.data.title)
                    Timber.e(state.data.url)
                    Timber.e(state.data.mediaType)
                }
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
