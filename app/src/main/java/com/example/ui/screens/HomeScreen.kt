package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Screen
import com.example.data.Product
import com.example.ui.theme.FloristPrimary
import com.example.ui.theme.FloristSecondary
import com.example.ui.theme.FloristTextDark
import com.example.ui.theme.FloristTextLight

@Composable
fun HomeScreen(
    products: List<Product>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onProductSelected: (Int) -> Unit,
    onToggleFavorite: (Product) -> Unit,
    onNavigateToCatalog: () -> Unit,
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

    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        // Hero Banner Span
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Hero Card Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    val bannerResId = context.resources.getIdentifier(
                        "img_hero_bouquet",
                        "drawable",
                        context.packageName
                    )
                    if (bannerResId != 0) {
                        Image(
                            painter = painterResource(bannerResId),
                            contentDescription = "Florist Hero Banner",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(FloristPrimary, FloristSecondary)))
                        )
                    }

                    // Soft Gradient Overlay for text readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                    startY = 100f
                                )
                            )
                    )

                    // Hero Text
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Buket Florist Premium",
                                color = Color(0xFFFFD700),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Sentuhan Indah untuk Setiap Momen",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Pesan cepat, mudah & langsung terhubung ke WhatsApp",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Category Scroll Label Span
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Kategori Pilihan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloristTextDark
                    )
                    Text(
                        text = "Lihat Semua",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = FloristPrimary,
                        modifier = Modifier
                            .clickable { onNavigateToCatalog() }
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Category Chips Scroll Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelected(category) },
                            label = { Text(text = category, fontSize = 12.sp) },
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
                            modifier = Modifier.testTag("category_chip_${category.lowercase().replace(" ", "_")}")
                        )
                    }
                }
            }
        }

        // Section Title Span
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Produk Unggulan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FloristTextDark,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
        }

        // Product Cards
        items(products) { product ->
            ProductCard(
                product = product,
                onProductClick = { onProductSelected(product.id) },
                onToggleFavorite = { onToggleFavorite(product) }
            )
        }

        // Footer spacer
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onProductClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductClick() }
            .testTag("product_card_${product.id}")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            val imgResId = context.resources.getIdentifier(
                product.imageResName,
                "drawable",
                context.packageName
            )
            if (imgResId != 0) {
                Image(
                    painter = painterResource(imgResId),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(Color.LightGray)
                )
            }

            // Category tag
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = product.category,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Favorite Button
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(100.dp))
                    .size(32.dp)
                    .testTag("favorite_button_${product.id}")
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = if (product.isFavorite) FloristPrimary else FloristTextLight,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = FloristTextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.description,
                fontSize = 11.sp,
                color = FloristTextLight,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatIndonesianPrice(product.price),
                    color = FloristPrimary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun formatIndonesianPrice(price: Double): String {
    return String.format("Rp %,.0f", price).replace(",", ".")
}
