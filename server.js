const express = require('express');
const bodyParser = require('body-parser');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const mysql = require('mysql2/promise');

const app = express();
const PORT = process.env.PORT || 8080;

// Middleware
app.use(bodyParser.json());

// Koneksi ke database MySQL
const dbConfig = {
    host: '203.175.8.151',
    user: 'xare7693_login', // Ganti dengan username MySQL Anda
    password: '@AdminGanteng123', // Ganti dengan password MySQL Anda
    database: 'xare7693_login' // Ganti dengan nama database Anda
};

async function connectToDatabase() {
    const connection = await mysql.createConnection(dbConfig);
    return connection;
}

app.post('/register', async (req, res) => {
    const { username, email, password } = req.body;

    const connection = await connectToDatabase();

    try {
        // Cek apakah username atau email sudah ada
        const [existingUsers] = await connection.execute('SELECT * FROM users WHERE username = ? OR email = ?', [username, email]);
        if (existingUsers.length > 0) {
            return res.status(409).json({ message: 'Username atau email sudah terdaftar' });
        }

        // Ambil ID terakhir yang digunakan
        const [lastIdResult] = await connection.execute('SELECT MAX(id) AS last_id FROM users');
        const lastId = lastIdResult[0].last_id || 0; // Jika tidak ada pengguna, mulai dari 0
        const newId = lastId + 1; // ID baru

        // Hash password sebelum menyimpan
        const hashedPassword = await bcrypt.hash(password, 10);

        // Simpan pengguna baru dengan ID yang ditentukan
        await connection.execute('INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)', [newId, username, email, hashedPassword]);

        res.status(201).json({ message: 'Pengguna berhasil terdaftar' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Terjadi kesalahan saat mendaftar pengguna' });
    } finally {
        await connection.end();
    }
});

// Endpoint untuk login
app.post('/login', async (req, res) => {
    const { username, email, password } = req.body;

    const connection = await connectToDatabase();

    try {
        // Temukan pengguna
        const [users] = await connection.execute('SELECT * FROM users WHERE email = ? AND username = ?', [email, username]);
        if (users.length === 0) {
            return res.status(401).json({ message: 'Username atau email salah' });
        }

        const user = users[0];

        // Periksa password
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Password salah' });
        }

        // Buat token
        const token = jwt.sign({ username: user.username, email: user.email }, 'secretKey', { expiresIn: '1h' });

        res.json({ message: 'Login berhasil', token });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Terjadi kesalahan saat login' });
    } finally {
        await connection.end();
    }
});

// Endpoint untuk melihat daftar pengguna
app.get('/users', async (req, res) => {
    const connection = await connectToDatabase();

    try {
        const [users] = await connection.execute('SELECT id, username, email FROM users');
        res.json(users);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Terjadi kesalahan saat mengambil daftar pengguna' });
    } finally {
        await connection.end();
    }
});

// Endpoint untuk menghapus pengguna berdasarkan ID
app.delete('/users/:id', async (req, res) => {
    const userId = req.params.id; // Ambil ID pengguna dari parameter URL

    const connection = await connectToDatabase();

    try {
        // Hapus pengguna dari database
        const [result] = await connection.execute('DELETE FROM users WHERE id = ?', [userId]);

        // Jika tidak ada baris yang dihapus, berarti ID tidak ditemukan
        if (result.affectedRows === 0) {
            return res.status(404).json({ message: 'Pengguna tidak ditemukan' });
        }

        res.json({ message: 'Pengguna berhasil dihapus' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Terjadi kesalahan saat menghapus pengguna' });
    } finally {
        await connection.end();
    }
});

// Rute untuk root
app.get('/', (req, res) => {
    res.send('HOREE BISA API LOGIN!!!');
});

// Jalankan server
app.listen(PORT, () => {
    console.log(`Server berjalan di http://localhost:${PORT}`);
});