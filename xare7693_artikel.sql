-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 27 Nov 2024 pada 18.08
-- Versi server: 10.6.17-MariaDB-cll-lve
-- Versi PHP: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `xare7693_artikel`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `artikel`
--

CREATE TABLE `artikel` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data untuk tabel `artikel`
--

INSERT INTO `artikel` (`id`, `title`, `content`, `image_url`) VALUES
(1, 'Judul Artikel Pertama', 'Ini adalah isi dari artikel pertama.', 'http://example.com/image1.jpg'),
(2, 'Judul Artikel Kedua', 'Ini adalah isi dari artikel kedua.', 'http://example.com/image2.jpg'),
(3, 'Judul Artikel Ketiga', 'Ini adalah isi dari artikel ketiga.', 'http://example.com/image3.jpg'),
(4, 'Judul Artikel Keempat', 'Ini adalah isi dari artikel keempat.', 'http://example.com/image4.jpg'),
(5, 'Judul Artikel Kelima', 'Ini adalah isi dari artikel kelima.', 'http://example.com/image5.jpg'),
(6, 'Judul Artikel Keenam', 'Ini adalah isi dari artikel keenam.', 'http://example.com/image6.jpg'),
(7, 'Judul Artikel Ketujuh', 'Ini adalah isi dari artikel ketujuh.', 'http://example.com/image7.jpg'),
(8, 'Judul Artikel Kedelapan', 'Ini adalah isi dari artikel kedelapan.', 'http://example.com/image8.jpg'),
(9, 'Judul Artikel Kesembilan', 'Ini adalah isi dari artikel kesembilan.', 'http://example.com/image9.jpg'),
(10, 'Judul Artikel Kesepuluh', 'Ini adalah isi dari artikel kesepuluh.', 'http://example.com/image10.jpg');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `artikel`
--
ALTER TABLE `artikel`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `artikel`
--
ALTER TABLE `artikel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
