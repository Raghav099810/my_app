package com.example.my_intern_project.models

data class Media(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val plotOverview: String,
    val type: String,
    val runtimeMinutes: Int?,  // Nullable
    val year: Int,
    val endYear: Int?,  // Nullable
    val releaseDate: String,
    val imdbId: String,
    val tmdbId: Int?,  // Nullable
    val tmdbType: String,
    val genres: List<Int>?,  // Nullable
    val genreNames: List<String>?,  // Nullable
    val poster: String?,  // Nullable
    val backdrop: String?,  // Nullable
    val originalLanguage: String,
    val similarTitles: List<Int>?,  // Nullable
    val networks: List<Int>?,  // Nullable
    val networkNames: List<String>?  // Nullable
) {
    companion object {
        fun fromJson(json: Map<String, Any>): Media {
            return Media(
                id = json["id"] as Int,
                title = json["title"] as? String ?: "Unknown Title",
                originalTitle = json["original_title"] as? String ?: "Unknown",
                plotOverview = json["plot_overview"] as? String ?: "No description available",
                type = json["type"] as? String ?: "Unknown",
                runtimeMinutes = json["runtime_minutes"] as? Int,
                year = json["year"] as? Int ?: 0,
                endYear = json["end_year"] as? Int,
                releaseDate = json["release_date"] as? String ?: "Unknown",
                imdbId = json["imdb_id"] as? String ?: "",
                tmdbId = json["tmdb_id"] as? Int,
                tmdbType = json["tmdb_type"] as? String ?: "",
                genres = json["genres"] as? List<Int>,
                genreNames = json["genre_names"] as? List<String>,
                poster = json["poster"] as? String,
                backdrop = json["backdrop"] as? String,
                originalLanguage = json["original_language"] as? String ?: "Unknown",
                similarTitles = json["similar_titles"] as? List<Int>,
                networks = json["networks"] as? List<Int>,
                networkNames = json["network_names"] as? List<String>
            )
        }
    }
}
