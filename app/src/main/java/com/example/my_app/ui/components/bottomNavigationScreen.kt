package com.example.my_app.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.my_app.ui.screens.MoviePage
import com.example.my_intern_project.R
import com.example.my_intern_project.mainpages.TvShowPage

// Define the BottomNavigationScreen composable
@Composable
fun BottomNavigationScreen(navController: NavController) {
    val selectedIndex = remember { mutableStateOf(0) }
    val screens = listOf("Movies", "TV Shows")

    Scaffold(
        bottomBar = {
            FloatingBottomNavigationBar(
                selectedIndex = selectedIndex.value,
                onItemSelected = { selectedIndex.value = it },
                screens = screens
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "movies"
            ) {
                composable("movies") { MoviePage() }
                composable("tv_shows") { TvShowPage() }
            }
        }
    }
}

@Composable
fun FloatingBottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    screens: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        // Floating Bottom Navigation Bar container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(30.dp)
                )
                .padding(8.dp)
        ) {
            BottomNavigation(
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 8.dp
            ) {
                screens.forEachIndexed { index, label ->
                    BottomNavigationItem(
                        selected = selectedIndex == index,
                        onClick = { onItemSelected(index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = if (index == 0) R.drawable.ic_movie else R.drawable.ic_tv),
                                contentDescription = null
                            )
                        },
                        label = { Text(text = label) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationScreenPreview() {
    BottomNavigationScreen(navController = rememberNavController())
}
