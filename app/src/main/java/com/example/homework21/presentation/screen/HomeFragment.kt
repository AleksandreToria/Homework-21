package com.example.homework21.presentation.screen

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var itemAdapter: HomeRecyclerAdapter
    private lateinit var categoryAdapter: CategoryRecyclerAdapter

    override fun bind() {
        itemAdapter = HomeRecyclerAdapter()
        categoryAdapter = CategoryRecyclerAdapter()

        setupRecyclerView()

        viewModel.onEvent(HomeEvent.FetchItems)
        viewModel.onEvent(HomeEvent.FetchCategories)
    }

    override fun bindViewActionListeners() {
        binding.apply {
            retryBtn.setOnClickListener {
                if (!binding.retryBtn.isEnabled) return@setOnClickListener
                viewModel.onEvent(HomeEvent.FetchItems)
            }

            categoryAdapter.setOnItemClickListener { category ->
                if (category == "All") {
                    viewModel.onEvent(HomeEvent.FetchItems)
                } else {
                    viewModel.onEvent(HomeEvent.FetchProductsByCategory(category))
                }
            }
        }
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

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = itemAdapter
        }

        binding.apply {
            categoryRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            categoryRecyclerView.adapter = categoryAdapter
        }
    }

    private fun handleHomeState(state: HomeState) = binding.apply {
        progress.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        retryBtn.visibility = if (state.showRetry) View.VISIBLE else View.GONE

        if (viewModel.isFetchingByCategory()) {
            state.categoryItems?.let {
                itemAdapter.submitList(it)
            }
        }else {
            state.items?.let {
                itemAdapter.submitList(it)
            }
        }

        state.category?.let {
            categoryAdapter.submitList(it)
        }

        state.errorMessage?.let {
            root.showSnackBar(message = it)
            viewModel.onEvent(HomeEvent.ResetErrorMessage)
        }
    }
}