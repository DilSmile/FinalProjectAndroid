package com.example.finalprojectmovie.data.network.model

import com.google.gson.annotations.SerializedName

data class GenreEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
