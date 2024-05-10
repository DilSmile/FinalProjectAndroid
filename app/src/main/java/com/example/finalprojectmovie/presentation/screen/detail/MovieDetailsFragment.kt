package com.example.finalprojectmovie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalprojectmovie.R
import com.example.finalprojectmovie.databinding.FragmentMovieDetailsBinding
import com.example.finalprojectmovie.image_loader.ImageLoader
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun hideView() = with(binding) {
        posterImage.visibility = View.INVISIBLE
        scrollView.visibility = View.INVISIBLE
    }

    private fun showView() = with(binding) {
        progressBar.visibility = View.GONE
        posterImage.visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
    }

    private fun setUpViewModel() = with(binding) {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            imageLoader.load(posterImage, it.posterUrl)
            movieTitle.text = it.title
            ratingbar.rating = it.rating
            ratingAverage.text = root.resources.getString(R.string.rating, it.rating)
            description.text = it.description
            addToWatchListButton.setOnClickListener {
                viewModel.addRemoveWatchList()
            }

            viewPager.isUserInputEnabled = false
            TabLayoutMediator(topTab, viewPager, false, false) { tab, position ->
                when (position) {
                    0 -> tab.text = "MORE LIKE THIS"
                    1 -> tab.text = "TRAILER"
                }
            }.attach()
            showView()
        }

        viewModel.inWatchList.observe(viewLifecycleOwner) {
            if (it == true) {
                addToWatchListButton.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                    setIconTintResource(R.color.gray)
                    setIconResource(R.drawable.baseline_done_24)
                }
            } else {
                addToWatchListButton.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    setIconTintResource(R.color.white)
                    setIconResource(R.drawable.baseline_add_24)
                }
            }

        }

        viewModel.id = requireArguments().getInt(MOVIE_ID)
    }

    companion object {
        private const val MOVIE_ID = "id"
        fun createBundle(id: Int): Bundle {
            return bundleOf(
                MOVIE_ID to id
            )
        }
    }

}
