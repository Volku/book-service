CREATE DATABASE IF NOT EXISTS dev;
USE dev;
CREATE TABLE IF NOT EXISTS book(
    id int,
    title TEXT not null,
    author TEXT not null,
    published_date DATE
);
CREATE INDEX idx_author ON book(author);
