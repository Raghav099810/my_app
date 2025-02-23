package com.example.my_app.ui.models
data class Release(
    val id: Int,
    val title: String,
    val type: String,
    val tmdbId: Int?,
    val tmdbType: String,
    val imdbId: String,
    val seasonNumber: Int?,
    val posterUrl: String,
    val sourceReleaseDate: String,
    val sourceId: Int,
    val sourceName: String,
    val isOriginal: Int
) {
    companion object {
        fun fromJson(json: Map<String, Any>): Release {
            return Release(
                id = json["id"] as Int,
                title = json["title"] as String,
                type = json["type"] as String,
                tmdbId = json["tmdb_id"] as? Int,
                tmdbType = json["tmdb_type"] as String,
                imdbId = json["imdb_id"] as String,
                seasonNumber = json["season_number"] as? Int,
                posterUrl = json["poster_url"] as String,
                sourceReleaseDate = json["source_release_date"] as String,
                sourceId = json["source_id"] as Int,
                sourceName = json["source_name"] as String,
                isOriginal = json["is_original"] as Int
            )
        }
    }
}
