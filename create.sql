CREATE DATABASE artikeldb;

USE artikeldb;

CREATE TABLE artikel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
);