
DELETE FROM book_subject;
INSERT INTO book_subject (id, description, name) 
VALUES (1, 'Romance', 'Romance');

DELETE FROM language;
INSERT INTO language (id, name) 
VALUES 
(1, 'Portugues'),
(2, 'English');

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
    title,
    subtitle,
    language_id,
    publisher_id,
    subject_id)
VALUES
(7,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Don Quixote', 'Conheça Ti', 1, 1, 1),
(8,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Um Conto de Duas Cidades', 'Conheça Ti 2', 1, 1, 1),
(9,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O alquimista', 'Conheça Ti 3', 1, 3, 1),
(10,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Senhor dos Anéis', 'Conheça Ti', 1, 1, 1),
(11,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'O Pequeno Príncipe', 'Conheça Ti 2', 1, 1, 1),
(12,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Harry Potter e a Pedra Filosofal', 'Conheça Ti 3', 1, 3, 1),
(13,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Código Da Vinci', 'Conheça Ti', 1, 1, 1),
(14,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Pense e Enriqueça', 'Conheça Ti 2', 1, 1, 1),
(15,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Cem Anos de Solidão', 'Conheça Ti 3', 1, 3, 1),
(16,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Nome da Rosa', 'Conheça Ti', 1, 1, 1),
(17,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Culpa É das Estrelas', 'Conheça Ti 2', 1, 1, 1),
(18,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Boa Noite Lua', 'Conheça Ti 3', 1, 3, 1),
(19,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'The Poky Little Puppy', 'Conheça Ti', 1, 1, 1),
(20,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Os Pilares da Terra', 'Conheça Ti 2', 1, 1, 1),
(21,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Perfume', 'Conheça Ti 3', 1, 3, 1),
(22,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Profeta', 'Conheça Ti', 1, 1, 1),
(23,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Noite', 'Conheça Ti 2', 1, 1, 1),
(24,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'A Mulher Total', 'Conheça Ti 3', 1, 3, 1),
(25,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Pequeno Manual Antirracista', 'Conheça Ti', 1, 1, 1),
(26,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Mulheres que Correm com os Lobos', 'Conheça Ti 2', 1, 1, 1),
(27,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', '1984', 'Conheça Ti 3', 1, 3, 1),
(28,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Poder do Hábito', 'Conheça Ti', 1, 1, 1),
(29,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'O Morro dos Ventos Uivantes', 'Conheça Ti 2', 1, 1, 1),
(30,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Milagre da Manhã: O Segredo para Transformar Sua Vida (Antes das 8 Horas)', 'Conheça Ti 3', 1, 3, 1),
(31,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Mais Esperto que o Diabo: O Mistério Revelado da Liberdade e do Sucesso', 'Conheça Ti', 1, 1, 1),
(32,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Revolução dos Bichos: Um Conto de Fadas', 'Conheça Ti 2', 1, 1, 1),
(33,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Racismo Estrutural', 'Conheça Ti 3', 1, 3, 1),
(34,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'A Coragem de Ser Imperfeito', 'Conheça Ti', 1, 1, 1),
(35,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Mindset: A Nova Psicologia do Sucesso', 'Conheça Ti 2', 1, 1, 1),
(36,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Conto da Aia', 'Conheça Ti 3', 1, 3, 1),
(37,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Do Mil ao Milhão – Sem Cortar o Cafezinho', 'Conheça Ti', 1, 1, 1),
(38,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', '21 Lições Para o Século 21', 'Conheça Ti 2', 1, 1, 1),
(39,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Essencialismo: A Disciplina da Busca por Menos', 'Conheça Ti 3', 1, 3, 1),
(40,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Os Segredos da Mente Milionária', 'Conheça Ti', 1, 1, 1),
(41,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Escravidão – Vol. 1', 'Conheça Ti 2', 1, 1, 1),
(42,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Sapiens – Uma Breve História da Humanidade', 'Conheça Ti 3', 1, 3, 1),
(43,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Homem Mais Rico da Babilônia', 'Conheça Ti', 1, 1, 1),
(44,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Harry Potter e a Pedra Filosofal', 'Conheça Ti 2', 1, 1, 1),
(45,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Test', 'Conheça Ti 3', 1, 3, 1);

--ALTER SEQUENCE book_id_seq RESTART WITH 46;

