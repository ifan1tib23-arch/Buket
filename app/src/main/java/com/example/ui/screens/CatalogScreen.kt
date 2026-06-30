package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Product
import com.example.ui.theme.FloristPrimary
import com.example.ui.theme.FloristTextDark
import com.example.ui.theme.FloristTextLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    products: List<Product>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onProductSelected: (Int) -> Unit,
    onToggleFavorite: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "All Varian Bouquet",
        "Snack Tower & Money Tower",
        "Hampers",
        "Hias Hantaran Seserahan",
        "Kalung Uang",
        "Handmade Kawat Bulu"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Katalog Buket",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = FloristTextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cari dan temukan buket impian Anda di sini",
                fontSize = 12.sp,
                color = FloristTextLight
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar TextField
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                placeholder = { Text("Cari produk (Mawar, Tulip, Hampers...)", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Cari",
                        tint = FloristTextLight
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloristPrimary,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.6f),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_input")
            )
        }

        // Horizontal Category Chips Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategorySelected(category) },
                    label = { Text(text = category, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = FloristPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = FloristTextLight
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        selectedBorderColor = Color.Transparent,
                        borderColor = Color.LightGray.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("catalog_category_chip_${category.lowercase().replace(" ", "_")}")
                )
            }
        }

        // Product Count & Status
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${products.size} Produk Ditemukan",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = FloristTextLight
            )
        }

        // Product Grid List
        if (products.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Tidak ada produk",
                        tint = FloristTextLight.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Produk tidak ditemukan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Coba kata kunci pencarian lain atau pilih kategori lain.",
                        fontSize = 12.sp,
                        color = FloristTextLight,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductSelected(product.id) },
                        onToggleFavorite = { onToggleFavorite(product) }
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
    }
}
