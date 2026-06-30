package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.*
import com.example.ui.theme.FloristBackground
import com.example.ui.theme.FloristPrimary
import com.example.ui.theme.FloristTextDark
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    // Handle System Back Button
    BackHandler(enabled = currentScreen != Screen.Home) {
        val handled = viewModel.navigateBack()
        if (!handled) {
            // Default action if history is empty
            viewModel.navigateTo(Screen.Home)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FloristBackground,
        topBar = {
            // Simple Elegant Top App Bar (Only visible when not on PDP Detail page to allow full-image bleed)
            if (currentScreen !is Screen.Detail) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "🌸 BUKET FLORIST",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = FloristPrimary,
                            letterSpacing = 1.sp
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.testTag("main_app_bar")
                )
            }
        },
        bottomBar = {
            // Sticky bottom navigation bar
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .navigationBarsPadding() // Keep safe from bottom system bar
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = currentScreen == Screen.Home,
                    onClick = { viewModel.navigateTo(Screen.Home) },
                    icon = {
                        Icon(
                            imageVector = if (currentScreen == Screen.Home) Icons.Default.Home else Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = { Text("Beranda", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FloristPrimary,
                        selectedTextColor = FloristPrimary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = FloristPrimary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.testTag("nav_item_home")
                )

                NavigationBarItem(
                    selected = currentScreen == Screen.Catalog,
                    onClick = { viewModel.navigateTo(Screen.Catalog) },
                    icon = {
                        Icon(
                            imageVector = if (currentScreen == Screen.Catalog) Icons.Default.List else Icons.Outlined.List,
                            contentDescription = "Katalog"
                        )
                    },
                    label = { Text("Katalog", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FloristPrimary,
                        selectedTextColor = FloristPrimary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = FloristPrimary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.testTag("nav_item_catalog")
                )

                NavigationBarItem(
                    selected = currentScreen == Screen.CaraPesan,
                    onClick = { viewModel.navigateTo(Screen.CaraPesan) },
                    icon = {
                        Icon(
                            imageVector = if (currentScreen == Screen.CaraPesan) Icons.Default.Info else Icons.Outlined.Info,
                            contentDescription = "Cara Pesan"
                        )
                    },
                    label = { Text("Cara Pesan", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FloristPrimary,
                        selectedTextColor = FloristPrimary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = FloristPrimary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.testTag("nav_item_carapesan")
                )

                NavigationBarItem(
                    selected = currentScreen == Screen.Contact,
                    onClick = { viewModel.navigateTo(Screen.Contact) },
                    icon = {
                        Icon(
                            imageVector = if (currentScreen == Screen.Contact) Icons.Default.Phone else Icons.Outlined.Phone,
                            contentDescription = "Kontak"
                        )
                    },
                    label = { Text("Kontak", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = FloristPrimary,
                        selectedTextColor = FloristPrimary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = FloristPrimary.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.testTag("nav_item_contact")
                )
            }
        }
    ) { innerPadding ->
        // Render current screen based on navigation state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        products = products,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.selectCategory(it) },
                        onProductSelected = { viewModel.navigateTo(Screen.Detail(it)) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onNavigateToCatalog = { viewModel.navigateTo(Screen.Catalog) }
                    )
                }
                is Screen.Catalog -> {
                    CatalogScreen(
                        products = products,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.selectCategory(it) },
                        searchQuery = searchQuery,
                        onSearchQueryChanged = { viewModel.setSearchQuery(it) },
                        onProductSelected = { viewModel.navigateTo(Screen.Detail(it)) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                    )
                }
                is Screen.CaraPesan -> {
                    CaraPesanScreen()
                }
                is Screen.Contact -> {
                    ContactScreen()
                }
                is Screen.Detail -> {
                    val productState = viewModel.getProductById(screen.productId)
                        .collectAsStateWithLifecycle(initialValue = null)

                    productState.value?.let { product ->
                        DetailScreen(
                            product = product,
                            onBackClick = { viewModel.navigateBack() },
                            onToggleFavorite = { viewModel.toggleFavorite(it) }
                        )
                    } ?: Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator(color = FloristPrimary)
                    }
                }
            }
        }
    }
}
