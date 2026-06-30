package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.Product
import com.example.data.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface Screen {
    object Home : Screen
    object Catalog : Screen
    object CaraPesan : Screen
    object Contact : Screen
    data class Detail(val productId: Int) : Screen
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    // Screen navigation state
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Backstack history to support back navigation!
    private val screenHistory = mutableListOf<Screen>()

    // Filter states
    private val _selectedCategory = MutableStateFlow("All Varian Bouquet")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Products Flow combined with filters
    val products: StateFlow<List<Product>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProductRepository(database.productDao())

        // Seed products if database is empty
        viewModelScope.launch {
            repository.seedIfNeeded()
        }

        // Combine categories and search queries to reactively filter products
        products = combine(
            _selectedCategory,
            _searchQuery,
            repository.allProducts
        ) { category, query, allProds ->
            var filtered = if (category == "All Varian Bouquet") {
                allProds
            } else {
                allProds.filter { it.category == category }
            }

            if (query.isNotEmpty()) {
                filtered = filtered.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
                }
            }
            filtered
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    // Navigation helper
    fun navigateTo(screen: Screen) {
        val current = _currentScreen.value
        if (current != screen) {
            screenHistory.add(current)
            _currentScreen.value = screen
        }
    }

    // Go back helper
    fun navigateBack(): Boolean {
        return if (screenHistory.isNotEmpty()) {
            _currentScreen.value = screenHistory.removeAt(screenHistory.size - 1)
            true
        } else {
            false
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Toggle favorite and save in Room Database
    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product.copy(isFavorite = !product.isFavorite))
        }
    }

    // Get specific product by ID for Detail Screen
    fun getProductById(id: Int): Flow<Product?> {
        return repository.getProductById(id)
    }
}
