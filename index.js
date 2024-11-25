const express = require('express');
const mysql = require('mysql2');
const app = express();
const PORT = process.env.PORT || 8080;
app.use(express.json());

// Koneksi ke database MySQL di Cloud SQL
const db = mysql.createConnection({
    host: 'localhost', // Ganti dengan IP publik Cloud SQL Anda
    user: 'xare7693_artikel',       // Ganti dengan username Cloud SQL Anda
    password: '@AdminGanteng123',   // Ganti dengan password Cloud SQL Anda
    database: 'xare7693_artikel'       // Ganti dengan nama database Anda
});

// Cek koneksi
db.connect((err) => {
    if (err) {
        console.error('Error connecting to MySQL:', err);
        return;
    }
    console.log('Connected to MySQL database');
});

// Rute untuk mendapatkan artikel
app.get('/articles', (req, res) => {
    db.query('SELECT * FROM artikel', (err, results) => {
        if (err) {
            console.error('Error fetching artikel:', err);
            return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
        }

        const response = {
            status: 'success',
            message: 'Data retrieved successfully',
            data: results // Mengembalikan hasil dari database
        };

        res.status(200).json(response);
    });
});

// Endpoint untuk mendapatkan artikel berdasarkan ID
app.get('/articles/:id', (req, res) => {
    const articleId = req.params.id; // Mengambil ID dari parameter URL

    // Query untuk mengambil artikel berdasarkan ID
    const query = 'SELECT * FROM artikel WHERE id = ?';

    // Eksekusi query
    db.query(query, [articleId], (err, results) => {
        if (err) {
            console.error('Error retrieving article:', err);
            return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
        }

        // Memeriksa apakah artikel ditemukan
        if (results.length === 0) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        // Mengirimkan respon dengan artikel yang ditemukan
        res.status(200).json({ status: 'success', data: results[0] });
    });
});


// Endpoint untuk menambahkan artikel
app.post('/articles', (req, res) => {
    const { title, content, image_url } = req.body;

    // Validasi sederhana
    if (!title || !content || !image_url) {
        return res.status(400).json({ status: 'error', message: 'All fields are required' });
    }

    // Mendapatkan ID terakhir
    db.query('SELECT MAX(id) AS maxId FROM artikel', (err, results) => {
        if (err) {
            console.error('Error fetching max ID:', err);
            return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
        }

        const newId = results[0].maxId ? results[0].maxId + 1 : 1; // Jika tidak ada ID, mulai dari 1

        const query = 'INSERT INTO artikel (id, title, content, image_url) VALUES (?, ?, ?, ?)';
        db.query(query, [newId, title, content, image_url], (err, results) => {
            if (err) {
                console.error('Error inserting article:', err);
                return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
            }

            res.status(201).json({ status: 'success', message: 'Article added successfully', articleId: newId });
        });
    });
});

// Endpoint untuk mengedit artikel berdasarkan ID
app.put('/articles/:id', (req, res) => {
    const articleId = req.params.id; // Mengambil ID dari parameter URL
    const { title, content, image_url } = req.body; // Mengambil data dari body permintaan

    // Query untuk memperbarui artikel
    const query = 'UPDATE artikel SET title = ?, content = ?, image_url = ? WHERE id = ?';

    // Eksekusi query
    db.query(query, [title, content, image_url, articleId], (err, results) => {
        if (err) {
            console.error('Error updating article:', err);
            return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
        }

        // Memeriksa apakah ada baris yang terpengaruh
        if (results.affectedRows === 0) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        // Mengirimkan respon sukses
        res.status(200).json({ status: 'success', message: 'Article updated successfully' });
    });
});

// Endpoint untuk menghapus artikel berdasarkan ID
app.delete('/articles/:id', (req, res) => {
    const articleId = req.params.id; // Mengambil ID dari parameter URL

    // Query untuk menghapus artikel
    const query = 'DELETE FROM artikel WHERE id = ?';

    // Eksekusi query
    db.query(query, [articleId], (err, results) => {
        if (err) {
            console.error('Error deleting article:', err);
            return res.status(500).json({ status: 'error', message: 'Internal Server Error' });
        }

        // Memeriksa apakah ada baris yang terpengaruh
        if (results.affectedRows === 0) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        // Mengirimkan respon sukses
        res.status(200).json({ status: 'success', message: 'Article deleted successfully' });
    });
});


// Rute untuk root
app.get('/', (req, res) => {
    res.send('HOREE BISA API ARTIKEL!!!');
});

// Mulai server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});