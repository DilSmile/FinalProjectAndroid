package com.example.finalprojectmovie.presentation.screen.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.finalprojectmovie.databinding.FragmentGridMoviesBinding
import com.example.finalprojectmovie.presentation.base.BaseFragment
import com.example.finalprojectmovie.presentation.decoration.OffsetDecoration
import com.example.finalprojectmovie.presentation.image_loader.ImageLoader
import com.example.finalprojectmovie.presentation.screen.detail.adapter.MovieLargeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SimilarMoviesFragment : BaseFragment() {

    private lateinit var binding: FragmentGridMoviesBinding
    private lateinit var adapter: MovieLargeAdapter
    private val viewModel: MovieDetailsViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpViewModel()
    }

    private fun setUpAdapter() = with(binding) {
        adapter = MovieLargeAdapter(imageLoader)
        adapter.onClick = navigateToMovieDetails()

        list.addItemDecoration(OffsetDecoration(end = 8, bottom = 16))

        list.adapter = adapter
    }

    private fun setUpViewModel() {
        viewModel.similarMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.i(">>>", "$it")
        }
    }
}
