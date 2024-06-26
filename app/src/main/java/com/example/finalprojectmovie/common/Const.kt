package com.example.finalprojectmovie.common

object Const {
    object Api {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val YOUTUBE_URL = "https://www.youtube.com/watch?v="
        const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"
        const val MOVIE_STARTING_PAGE_INDEX = 1
        const val MOVIES_PAGE_SIZE = 20
        const val TRAILER = "Trailer"

        val genres = mapOf(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantasy",
            36 to "History",
            27 to "Horror",
            10402 to "Music",
            9648 to "Mystery",
            10749 to "Romance",
            878 to  "Science Fiction",
            10770 to "TV Movie",
            53 to "Thriller",
            10752 to "War",
            37 to "Western"
        )
    }

    object DB {
        const val DB_NAME = "movie_data_base"
        const val DB_VERSION = 1
    }
}
