package com.example.flixsterpt1

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("title")
    var title: String? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var poster_path: String? = null
}