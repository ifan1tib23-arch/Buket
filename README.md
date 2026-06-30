# Buket Florist - Premium Mobile Web Catalog

Aplikasi Katalog Buket Premium (Mobile-First Web App) dengan checkout formulir langsung terintegrasi ke WhatsApp Admin.

## Fitur Utama

-   **Mobile-First Responsive Design**: Tampilan dirancang khusus agar menyerupai aplikasi native di HP, dengan grid yang dapat menyesuaikan diri secara mulus di tablet dan desktop.
-   **Katalog Interaktif**: Filter kategori horizontal, tombol pencarian cepat real-time, dan penyimpanan produk "Favorit" berbasis `localStorage` lokal.
-   **Formulir Pemesanan WhatsApp Mandiri**: Pelanggan dapat memasukkan detail order seperti:
    -   Nama Pemesan
    -   Nomor WhatsApp Pemesan (dengan validasi format Indonesia)
    -   Tanggal Pengiriman (dengan proteksi tanggal masa lampau)
    -   Pilihan Kertas Wrapping (Blush Pink, Gold, Midnight Black, Rustic, dll)
    -   Kartu Ucapan Custom (disertai pembatas karakter)
-   **Generator Pesan WhatsApp**: Otomatis menyusun teks format pemesanan rapi dan mengarahkan langsung ke WhatsApp Admin toko (+62 822-1616-8815) saat disubmit.

## Cara Deploy ke Vercel

Aplikasi ini sudah dikonfigurasi untuk dapat dideploy ke **Vercel** secara instan:

1.  Simpan seluruh perubahan Anda dan push kode ini ke repositori **GitHub** Anda.
2.  Masuk ke dashboard [Vercel](https://vercel.com).
3.  Klik **Add New** -> **Project**.
4.  Pilih repositori GitHub Anda.
5.  Vercel akan otomatis mengenali berkas `index.html` di root direktori.
6.  Klik **Deploy**! Aplikasi Anda akan online dalam beberapa detik tanpa perlu melakukan konfigurasi apa pun.
