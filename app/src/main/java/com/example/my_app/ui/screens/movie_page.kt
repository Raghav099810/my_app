package com.example.my_app.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.my_app.ui.service.ApiService
import com.example.my_intern_project.components.Design
import com.example.my_app.ui.models.Release

@Composable
fun MoviePage() {
    val apiService = ApiService()
    var releases by remember { mutableStateOf<List<Release>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Fetch data when the screen is displayed
    LaunchedEffect(true) {
        try {
            releases = apiService.fetchReleases()
        } catch (e: Exception) {
            // Handle the error appropriately
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Search Bar
        SearchBar(searchQuery) { query -> searchQuery = query }

        // Movies List with shimmer or content
        if (isLoading) {
            ShimmerEffect()  // Show shimmer effect while loading
        } else {
            val filteredReleases = releases.filter {
                (it.tmdbType == "movie" || it.tmdbType == "short") &&
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
                            id = release.id
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
        onValueChange = onSearchQueryChanged,
        label = { Text("Search your favourite movies...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = false),
        keyboardActions = KeyboardActions.Default,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Composable
fun ShimmerEffect() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 10.dp)
    ) {
        items(6) { // Show 6 shimmer items while loading
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.2f)) // Shimmer effect
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMoviePage() {
    MoviePage()
}
