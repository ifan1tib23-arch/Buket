package com.example.data

import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return if (category == "All Varian Bouquet") {
            productDao.getAllProducts()
        } else {
            productDao.getProductsByCategory(category)
        }
    }

    fun getProductById(id: Int): Flow<Product?> = productDao.getProductById(id)

    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun seedIfNeeded() {
        val count = productDao.countProducts()
        if (count == 0) {
            val seedProducts = listOf(
                // 1. All Varian Bouquet
                Product(
                    name = "Buket Mawar Pink Pastel (Rose Elegance)",
                    category = "All Varian Bouquet",
                    price = 150000.0,
                    description = "Buket bunga mawar pink pastel segar yang dirangkai dengan gaya Korea premium, dibungkus dengan kertas wrap berkualitas tinggi. Sangat cocok untuk kado ulang tahun, wisuda, atau anniversary.",
                    imageResName = "img_bouquet_all"
                ),
                Product(
                    name = "Buket Peony Luxury Dream",
                    category = "All Varian Bouquet",
                    price = 275000.0,
                    description = "Rangkaian bunga peony mewah dipadukan dengan bunga baby's breath segar dan daun eucalyptus. Memberikan kesan romantis, manis, dan elegan.",
                    imageResName = "img_bouquet_all"
                ),
                Product(
                    name = "Buket Tulip Pastel Blossom",
                    category = "All Varian Bouquet",
                    price = 185000.0,
                    description = "Buket bunga tulip segar berkualitas tinggi dengan perpaduan warna pastel lembut yang menawan. Cocok untuk mengutarakan rasa kasih sayang.",
                    imageResName = "img_bouquet_all"
                ),

                // 2. Snack Tower & Money Tower
                Product(
                    name = "Snack Tower Ferrero Wisuda",
                    category = "Snack Tower & Money Tower",
                    price = 320000.0,
                    description = "Menara cokelat Ferrero Rocher mewah bertingkat, dihias dengan pita satin, bunga kering, dan boneka kelinci wisuda mini yang sangat menggemaskan.",
                    imageResName = "img_snack_tower"
                ),
                Product(
                    name = "Money Tower Kemakmuran (2 Tingkat)",
                    category = "Snack Tower & Money Tower",
                    price = 480000.0,
                    description = "Menara uang tiruan/asli (by request) disusun cantik 2 tingkat, didekorasi dengan bunga mawar sabun wangi dan lampu LED estetik.",
                    imageResName = "img_snack_tower"
                ),

                // 3. Hampers
                Product(
                    name = "Hampers Idul Fitri Premium Box",
                    category = "Hampers",
                    price = 450000.0,
                    description = "Kotak kayu hampers eksklusif berisi 3 toples kue kering premium, sajadah travel, kartu ucapan custom, dan hiasan bunga kering rustic.",
                    imageResName = "img_hampers_seserahan"
                ),
                Product(
                    name = "Hampers Sweet Blossom Baby",
                    category = "Hampers",
                    price = 295000.0,
                    description = "Hampers bayi berisi handuk lembut microfiber, set baju bayi katun organik, empeng premium, dan hiasan bunga kering dalam keranjang rotan yang manis.",
                    imageResName = "img_hampers_seserahan"
                ),

                // 4. Hias Hantaran Seserahan
                Product(
                    name = "Acrylic Seserahan Tray Classy",
                    category = "Hias Hantaran Seserahan",
                    price = 180000.0,
                    description = "Jasa sewa dan hias boks akrilik seserahan pernikahan dengan bunga artifisial premium, dedaunan hijau, dan tatanan isi hantaran yang sangat rapi nan mewah.",
                    imageResName = "img_hampers_seserahan"
                ),
                Product(
                    name = "Royal Gold Hantaran Wedding",
                    category = "Hias Hantaran Seserahan",
                    price = 240000.0,
                    description = "Jasa dekorasi hantaran pernikahan bertema emas mewah (Royal Gold) dengan alas satin premium, dihiasi bunga mawar sutra dan mutiara.",
                    imageResName = "img_hampers_seserahan"
                ),

                // 5. Kalung Uang
                Product(
                    name = "Kalung Uang Graduasi Classy",
                    category = "Kalung Uang",
                    price = 110000.0,
                    description = "Kalung uang lembaran (menggunakan uang mainan/asli by request) yang dilipat rapi membentuk kipas cantik, digabungkan dengan anyaman pita satin berkilau untuk wisuda.",
                    imageResName = "img_snack_tower"
                ),
                Product(
                    name = "Kalung Uang Floral Premium",
                    category = "Kalung Uang",
                    price = 165000.0,
                    description = "Kalung uang kipas dikombinasikan dengan anyaman bunga baby's breath segar dan melati asli, memberikan aroma wangi khas yang sangat berkesan.",
                    imageResName = "img_snack_tower"
                ),

                // 6. Handmade Kawat Bulu
                Product(
                    name = "Buket Tulip Kawat Bulu (Fuzzy Pipe)",
                    category = "Handmade Kawat Bulu",
                    price = 85000.0,
                    description = "Buket bunga tulip buatan tangan (handmade) menggunakan kawat bulu (pipe cleaner) beludru berkualitas tinggi. Awet selamanya, sangat lucu, unik, dan cocok untuk dekorasi meja.",
                    imageResName = "img_bouquet_all"
                ),
                Product(
                    name = "Fuzzy Lavender Bouquet Aesthetic",
                    category = "Handmade Kawat Bulu",
                    price = 75000.0,
                    description = "Rangkaian bunga lavender ungu buatan tangan dari kawat bulu lembut, dirangkai dengan daun sage hijau dan dibungkus dengan kertas wrap premium bergaya vintage.",
                    imageResName = "img_bouquet_all"
                )
            )
            productDao.insertProducts(seedProducts)
        }
    }
}
