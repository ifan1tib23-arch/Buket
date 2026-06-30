package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CaraPesanScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Cara Pemesanan",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = FloristTextDark
        )
        Text(
            text = "Ikuti langkah mudah memesan buket impian Anda",
            fontSize = 12.sp,
            color = FloristTextLight
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Steps cards
        StepItem(
            stepNumber = "1",
            title = "Pilih Buket Cantik Anda",
            description = "Telusuri katalog produk premium kami. Kami menyediakan bouquet bunga mawar, tulip segar, hampers, seserahan, hingga kerajinan kawat bulu (fuzzy pipe cleaner).",
            icon = Icons.Default.List
        )

        Spacer(modifier = Modifier.height(14.dp))

        StepItem(
            stepNumber = "2",
            title = "Lengkapi Detail Formulir",
            description = "Klik produk untuk masuk ke Detail Produk. Isi formulir pemesanan dengan melengkapi Nama Anda, Nomor HP WhatsApp, Tanggal Pengiriman, Warna kertas wrapping, dan Isi Kartu Ucapan custom.",
            icon = Icons.Default.Edit
        )

        Spacer(modifier = Modifier.height(14.dp))

        StepItem(
            stepNumber = "3",
            title = "Kirim Order via WhatsApp",
            description = "Klik tombol 'Pesan via WhatsApp'. Aplikasi akan secara otomatis menyusun dan memformat pesan pesanan Anda yang rapi, lalu membuka aplikasi WhatsApp langsung ke admin kami.",
            icon = Icons.Default.Send
        )

        Spacer(modifier = Modifier.height(14.dp))

        StepItem(
            stepNumber = "4",
            title = "Konfirmasi & Pengiriman",
            description = "Admin kami akan memverifikasi pesanan, mengonfirmasi ongkos kirim, memberikan instruksi transfer pembayaran, lalu buket indah Anda siap dikirimkan tepat waktu! ✨",
            icon = Icons.Default.CheckCircle
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun StepItem(
    stepNumber: String,
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("step_item_$stepNumber")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Step badge circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(FloristPrimary)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = FloristSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloristTextDark
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = FloristTextLight,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun ContactScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Hubungi Kami",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = FloristTextDark
        )
        Text(
            text = "Informasi lengkap toko florist & media sosial kami",
            fontSize = 12.sp,
            color = FloristTextLight
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Store Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = "🌸 Buket Florist Shop",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = FloristTextDark
                )
                Spacer(modifier = Modifier.height(12.dp))

                ContactInfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "Alamat Toko",
                    value = "Jl. Melati Indah No. 45, Kebayoran Baru, Jakarta Selatan, 12160"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ContactInfoRow(
                    icon = Icons.Default.Info,
                    label = "Jam Operasional",
                    value = "Senin - Sabtu: 08:00 - 20:00 WIB\nMinggu: 09:00 - 15:00 WIB"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ContactInfoRow(
                    icon = Icons.Default.Phone,
                    label = "WhatsApp Admin",
                    value = "+62 812-3456-7890"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Sosial Media & Tautan Cepat",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = FloristTextDark,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Quick Social Links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialButton(
                text = "Instagram",
                backgroundColor = Color(0xFFE1306C),
                onClick = { openUrl(context, "https://instagram.com") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_instagram")
            )
            SocialButton(
                text = "TikTok",
                backgroundColor = Color(0xFF000000),
                onClick = { openUrl(context, "https://tiktok.com") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_tiktok")
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SocialButton(
                text = "Google Maps",
                backgroundColor = Color(0xFF4285F4),
                onClick = { openUrl(context, "https://maps.google.com") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_maps")
            )
            SocialButton(
                text = "WhatsApp Chat",
                backgroundColor = Color(0xFF25D366),
                onClick = { openUrl(context, "https://wa.me/6281234567890") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_wa")
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Commitment Banner Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = FloristSecondary.copy(alpha = 0.08f)),
            border = BorderStroke(1.dp, FloristSecondary.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Garansi Kesegaran 100%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloristSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Semua bunga potong kami dijaga kesegarannya dan dirangkai dengan penuh kasih sayang oleh florist profesional berpengalaman.",
                    fontSize = 11.sp,
                    color = FloristTextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ContactInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = FloristPrimary,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = FloristTextLight
            )
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = FloristTextDark,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun SocialButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier.height(46.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

private fun openUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Tidak dapat membuka tautan ini.", Toast.LENGTH_SHORT).show()
    }
}
