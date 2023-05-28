--Dev DB
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'library_book_store_db'

drop database library_book_store_db

create database library_book_store_db