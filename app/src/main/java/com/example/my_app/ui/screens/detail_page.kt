package com.example.my_app.ui.screens
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.placeholder
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.material.*
import com.google.firebase.appdistribution.gradle.ApiService
import kotlinx.coroutines.delay

@Composable
fun DetailPage(
    id: Int?,
    imageUrl: String,
    title: String,
    onBackPress: () -> Unit
) {
    var mediaDetails by remember { mutableStateOf<Media?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isLiked by remember { mutableStateOf(false) }

    // Fetch Data
    LaunchedEffect(id) {
        if (id != null) {
            try {
                delay(1500) // Simulating API call delay
                mediaDetails = ApiService().fetchDetail(id.toString())  // Implement fetchDetail in ApiService
            } catch (e: Exception) {
                Log.e("DetailPage", "Error fetching details: $e")
            }
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movie Details") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isLiked = !isLiked }) {
                        Icon(
                            if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red else Color.Gray
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            PosterImage(imageUrl = imageUrl, isLoading = isLoading)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = mediaDetails?.title ?: title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(visible = !isLoading) {
                Text(
                    text = mediaDetails?.plotOverview ?: "No description available",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            AnimatedVisibility(visible = !isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Released On: ${mediaDetails?.releaseDate ?: "Unknown"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun PosterImage(imageUrl: String, isLoading: Boolean) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val isImageLoading = painter.state is AsyncImagePainter.State.Loading

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)
                )
            )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading || isImageLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.Gray.copy(alpha = 0.3f)
                    )
            )
        }
    }
}
