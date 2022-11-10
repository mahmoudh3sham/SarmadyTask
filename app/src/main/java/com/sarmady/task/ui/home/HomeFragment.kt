package com.sarmady.task.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.sarmady.task.R
import com.sarmady.task.data.models.PhotoModel
import com.sarmady.task.databinding.FragmentHomeBinding
import com.sarmady.task.ui.full_screen.FullScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home), PhotoAdapter.OnPhotoClickListener {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var photoAdapter: PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setupAdapter()

        subscribeViewModel()

        setHasOptionsMenu(true)

    }

    private fun subscribeViewModel() {
        viewModel.results.observe(viewLifecycleOwner){
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setupAdapter(){
        photoAdapter = PhotoAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter {photoAdapter.retry()},
                footer = PhotoLoadStateAdapter {photoAdapter.retry()}
            )

            buttonRetry.setOnClickListener {
                photoAdapter.retry()
            }
        }


        photoAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //empty view
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && photoAdapter.itemCount < 1){
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                }else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(photo: PhotoModel) {
        startActivity(FullScreenActivity.newIntent(context).putExtra("photo", photo))
    }
}