--*
-- id: 0002 - adding_adults_only_field_on_book.sql
-- author: Paulo
-- date: 20/12/2022
-- note: Adding a new boolean field to show if a book is under 18 forbidden
--*
ALTER TABLE book ADD COLUMN "adults_only" BOOLEAN DEFAULT FALSE;