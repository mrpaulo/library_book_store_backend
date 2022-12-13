 SELECT pg_terminate_backend(pid)
 FROM pg_stat_activity
 WHERE datname = 'test2_library_book_store'

drop database test2_library_book_store

create database test2_library_book_store