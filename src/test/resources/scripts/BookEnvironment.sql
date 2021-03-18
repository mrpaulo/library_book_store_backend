
DELETE FROM book_subject;
INSERT INTO book_subject (id, description, name) 
VALUES (1, 'Romance', 'Romance');

DELETE FROM language;
INSERT INTO language (id, name) 
VALUES (1, 'Portugues');

DELETE FROM book;
INSERT INTO book 
    (id,
    condition,
    edition,
    format,
    length,
    link,
    publish_date,
    rating,
    review,
    subtitle,
    title,
    language_id,
    publisher_id,
    subject_id)
VALUES
(1,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Uma viagem pelo conhecimento', 'Conheça Ti', 1, 1, 1),
(2,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Uma viagem pelo conhecimento', 'Conheça Ti 2', 1, 1, 1),
(3,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Uma viagem pelo conhecimento', 'Conheça Ti 3', 1, 3, 1);


DELETE FROM author_books;
INSERT INTO author_books
(author_id, books_id) 
VALUES
(1, 1),
(3, 1),
(1, 2),
(1, 3);
