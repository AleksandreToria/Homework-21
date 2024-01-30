package com.example.homework21.presentation.screen

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework21.databinding.FragmentHomeBinding
import com.example.homework21.presentation.base.BaseFragment
import com.example.homework21.presentation.event.HomeEvent
import com.example.homework21.presentation.extension.showSnackBar
import com.example.homework21.presentation.state.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var adapter: HomeRecyclerAdapter

    override fun bind() {
        adapter = HomeRecyclerAdapter()
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = adapter
        }

        viewModel.onEvent(HomeEvent.FetchConnections)
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collect {
                    handleHomeState(it)
                }
            }
        }
    }

    private fun handleHomeState(state: HomeState) {
        binding.progress.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.items?.let {
            adapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(HomeEvent.ResetErrorMessage)
        }
    }
}