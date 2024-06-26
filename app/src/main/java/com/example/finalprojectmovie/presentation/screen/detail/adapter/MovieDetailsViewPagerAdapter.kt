package com.example.finalprojectmovie.presentation.screen.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalprojectmovie.presentation.screen.detail.MovieTrailerFragment
import com.example.finalprojectmovie.presentation.screen.detail.SimilarMoviesFragment

class MovieDetailsViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SimilarMoviesFragment()
            else -> MovieTrailerFragment()
        }
    }

}
