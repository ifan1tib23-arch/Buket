package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val price: Double,
    val description: String,
    val imageResName: String, // Store name like "img_bouquet_all" instead of hardcoded Int to make it extremely robust
    val isFavorite: Boolean = false
)
