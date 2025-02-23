package com.example.my_intern_project.mainpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.my_app.ui.models.Release
import com.example.my_app.ui.service.ApiService
import com.example.my_intern_project.components.Design
import kotlinx.coroutines.launch

@Composable
fun TvShowPage() {
    val apiService = ApiService()
    var releases by remember { mutableStateOf<List<Release>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Fetch data on composable launch
    LaunchedEffect(true) {
        scope.launch {
            try {
                releases = apiService.fetchReleases()
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Search bar
        com.example.my_app.ui.screens.SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it }
        )

        // Display content
        if (isLoading) {
            com.example.my_app.ui.screens.ShimmerEffect() // Show shimmer effect while loading
        } else {
            val filteredReleases = releases.filter {
                it.tmdbType.contains("tv", ignoreCase = true) &&
                        it.title.contains(searchQuery, ignoreCase = true)
            }

            if (filteredReleases.isEmpty()) {
                Text(text = "No releases found", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 10.dp)
                ) {
                    items(filteredReleases) { release ->
                        Design(
                            img = release.posterUrl,
                            title = release.title,
                            date = release.sourceReleaseDate,
                            available = release.sourceName,
                            type = release.tmdbType,
                            id = release.tmdbId
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChanged(it) },
        label = { Text(text = "Search your favourite TV shows...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Composable
fun ShimmerEffect() {
    // Placeholder shimmer effect using a simple Box
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 10.dp)
    ) {
        items(6) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }
    }
}
