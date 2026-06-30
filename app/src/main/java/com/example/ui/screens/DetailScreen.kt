package com.example.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Product
import com.example.ui.theme.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onToggleFavorite: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Form states
    var namaPemesan by remember { mutableStateOf("") }
    var nomorHp by remember { mutableStateOf("") }
    var tanggalPengiriman by remember { mutableStateOf("") }
    var warnaWrap by remember { mutableStateOf("Dusty Pink") }
    var isiKartu by remember { mutableStateOf("") }

    // Validation visual error flags
    var namaError by remember { mutableStateOf(false) }
    var hpError by remember { mutableStateOf(false) }
    var tanggalError by remember { mutableStateOf(false) }

    // Dropdown list
    val wrapColors = listOf(
        "Dusty Pink",
        "Sage Green",
        "Kraft Vintage (Rustic)",
        "Soft Blue",
        "Luxury Black & Gold",
        "Gold Velvet",
        "Cream Linen"
    )
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // DatePicker trigger
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            tanggalPengiriman = "$dayOfMonth-${month + 1}-$year"
            tanggalError = false
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FloristBackground)
    ) {
        // Custom Header top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag("detail_back_button")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali",
                    tint = FloristTextDark
                )
            }
            Text(
                text = "Detail Produk",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = FloristTextDark,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            IconButton(
                onClick = { onToggleFavorite(product) },
                modifier = Modifier.testTag("detail_favorite_button")
            ) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit",
                    tint = if (product.isFavorite) FloristPrimary else FloristTextLight
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Product Image Section
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
                        .height(280.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image Available")
                }
            }

            // Info Card
            Card(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(bottom = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    // Category & Rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.category,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloristSecondary,
                            modifier = Modifier
                                .background(FloristSecondary.copy(alpha = 0.12f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Favorit saya: ",
                                fontSize = 11.sp,
                                color = FloristTextLight
                            )
                            Icon(
                                imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = FloristPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title
                    Text(
                        text = product.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = FloristTextDark,
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Price
                    Text(
                        text = formatIndonesianPrice(product.price),
                        color = FloristPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Divider
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    Text(
                        text = "Deskripsi Produk",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = product.description,
                        fontSize = 13.sp,
                        color = FloristTextLight,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Order Checkout Form Section
                    Text(
                        text = "Formulir Pemesanan (WhatsApp)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloristTextDark
                    )
                    Text(
                        text = "Lengkapi detail pesanan Anda di bawah ini untuk checkout langsung via WhatsApp.",
                        fontSize = 11.sp,
                        color = FloristTextLight
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input 1: Nama Pemesan
                    Text(
                        text = "Nama Pemesan *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (namaError) FloristError else FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = namaPemesan,
                        onValueChange = {
                            namaPemesan = it
                            if (it.isNotEmpty()) namaError = false
                        },
                        placeholder = { Text("Masukkan nama lengkap Anda") },
                        singleLine = true,
                        isError = namaError,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloristPrimary,
                            unfocusedBorderColor = if (namaError) FloristError else Color.LightGray.copy(alpha = 0.6f),
                            errorBorderColor = FloristError,
                            focusedContainerColor = FloristBackground.copy(alpha = 0.3f),
                            unfocusedContainerColor = FloristBackground.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_nama")
                    )
                    AnimatedVisibility(visible = namaError) {
                        Text(
                            text = "Nama Pemesan tidak boleh kosong!",
                            color = FloristError,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input 2: Nomor HP Pemesan
                    Text(
                        text = "Nomor WhatsApp Pemesan *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (hpError) FloristError else FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = nomorHp,
                        onValueChange = {
                            nomorHp = it
                            if (it.isNotEmpty()) hpError = false
                        },
                        placeholder = { Text("Contoh: 081234567890") },
                        singleLine = true,
                        isError = hpError,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloristPrimary,
                            unfocusedBorderColor = if (hpError) FloristError else Color.LightGray.copy(alpha = 0.6f),
                            errorBorderColor = FloristError,
                            focusedContainerColor = FloristBackground.copy(alpha = 0.3f),
                            unfocusedContainerColor = FloristBackground.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_hp")
                    )
                    AnimatedVisibility(visible = hpError) {
                        Text(
                            text = "Nomor WhatsApp tidak valid (harus dimulai dengan 08/62/+62 dan memiliki 9-14 digit)!",
                            color = FloristError,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input 3: Tanggal Pengiriman
                    Text(
                        text = "Tanggal Pengiriman *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (tanggalError) FloristError else FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = tanggalPengiriman,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("Pilih tanggal pengiriman buket") },
                        isError = tanggalError,
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            IconButton(
                                onClick = { datePickerDialog.show() },
                                modifier = Modifier.testTag("datepicker_trigger")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Pilih Tanggal",
                                    tint = FloristPrimary
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloristPrimary,
                            unfocusedBorderColor = if (tanggalError) FloristError else Color.LightGray.copy(alpha = 0.6f),
                            errorBorderColor = FloristError,
                            focusedContainerColor = FloristBackground.copy(alpha = 0.3f),
                            unfocusedContainerColor = FloristBackground.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() }
                            .testTag("input_tanggal")
                    )
                    AnimatedVisibility(visible = tanggalError) {
                        Text(
                            text = "Tanggal Pengiriman wajib dipilih!",
                            color = FloristError,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input 4: Warna Wrap
                    Text(
                        text = "Pilihan Warna Kertas Wrap *",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = warnaWrap,
                            onValueChange = { },
                            readOnly = true,
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                Text(
                                    text = "UBAH",
                                    color = FloristPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clickable { isDropdownExpanded = true }
                                        .padding(12.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FloristPrimary,
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.6f),
                                focusedContainerColor = FloristBackground.copy(alpha = 0.3f),
                                unfocusedContainerColor = FloristBackground.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isDropdownExpanded = true }
                                .testTag("input_wrap")
                        )

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .background(Color.White)
                        ) {
                            wrapColors.forEach { color ->
                                DropdownMenuItem(
                                    text = { Text(text = color, fontSize = 14.sp) },
                                    onClick = {
                                        warnaWrap = color
                                        isDropdownExpanded = false
                                    },
                                    modifier = Modifier.testTag("wrap_item_${color.lowercase().replace(" ", "_")}")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input 5: Isi Kartu Ucapan
                    Text(
                        text = "Isi Kartu Ucapan (Opsional)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloristTextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = isiKartu,
                        onValueChange = { isiKartu = it },
                        placeholder = { Text("Contoh: Happy Birthday Ibuku Sayang, Semoga Sehat Selalu! Dari: Budi") },
                        minLines = 3,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloristPrimary,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.6f),
                            focusedContainerColor = FloristBackground.copy(alpha = 0.3f),
                            unfocusedContainerColor = FloristBackground.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_kartu")
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Checkout CTA Button
                    Button(
                        onClick = {
                            // Validation logic
                            val isNamaValid = namaPemesan.trim().isNotEmpty()
                            val isTanggalValid = tanggalPengiriman.trim().isNotEmpty()

                            // Phone validation: must match Indonesian mobile phone standard
                            val hpRegex = Regex("^(08|\\+62|62)[0-9]{8,12}$")
                            val isHpValid = nomorHp.trim().matches(hpRegex)

                            namaError = !isNamaValid
                            tanggalError = !isTanggalValid
                            hpError = !isHpValid

                            if (isNamaValid && isTanggalValid && isHpValid) {
                                triggerWhatsAppCheckout(
                                    context = context,
                                    productName = product.name,
                                    productCategory = product.category,
                                    productPrice = product.price,
                                    nama = namaPemesan.trim(),
                                    hp = nomorHp.trim(),
                                    tanggal = tanggalPengiriman.trim(),
                                    wrap = warnaWrap,
                                    kartu = isiKartu.trim()
                                )
                            } else {
                                Toast.makeText(context, "Mohon lengkapi formulir dengan benar", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)), // WhatsApp Green
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("order_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Display a beautiful WhatsApp simple icon
                            Text(
                                text = "💬 Pesan Sekarang via WhatsApp",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

private fun triggerWhatsAppCheckout(
    context: Context,
    productName: String,
    productCategory: String,
    productPrice: Double,
    nama: String,
    hp: String,
    tanggal: String,
    wrap: String,
    kartu: String
) {
    val floristPhone = "+6281234567890" // The standard florist business number
    val formattedPrice = formatIndonesianPrice(productPrice)

    val cardMessage = if (kartu.isEmpty()) "-(Tidak ada kartu ucapan)-" else kartu

    val message = """
        Halo Buket Florist! Saya ingin melakukan pemesanan dengan detail berikut:
        
        🌸 *PESANAN PRODUK* 🌸
        • Nama Produk: $productName
        • Kategori: $productCategory
        • Harga: $formattedPrice
        
        📦 *DETAIL PENGIRIMAN* 📦
        • Nama Pemesan: $nama
        • No. HP Pemesan: $hp
        • Tanggal Pengiriman: $tanggal
        • Pilihan Warna Wrap: $wrap
        • Isi Kartu Ucapan: $cardMessage
        
        Mohon diproses ya, terima kasih! ✨
    """.trimIndent()

    try {
        val url = "https://api.whatsapp.com/send?phone=$floristPhone&text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            // Prevent crash if whatsapp or browser is not available
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)

        // Show a sweet success toast / trigger simulated automatic confirmation
        Toast.makeText(context, "Membuka WhatsApp... Pesanan Anda sedang diproses!", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Gagal membuka WhatsApp. Mohon periksa koneksi Anda.", Toast.LENGTH_LONG).show()
    }
}
