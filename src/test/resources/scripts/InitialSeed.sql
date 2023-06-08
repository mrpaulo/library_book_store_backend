/* 
 * Copyright (C) 2022 paulo.rodrigues
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Author:  paulo.rodrigues
 * Created: Jun 23, 2022
 */
--Clear
TRUNCATE TABLE address CASCADE;
TRUNCATE TABLE author CASCADE;
TRUNCATE TABLE author_books CASCADE;
TRUNCATE TABLE book CASCADE;
TRUNCATE TABLE book_subject CASCADE;
TRUNCATE TABLE city CASCADE;
TRUNCATE TABLE country CASCADE;
TRUNCATE TABLE language CASCADE;
TRUNCATE TABLE lbs_user CASCADE;
TRUNCATE TABLE publisher CASCADE;
TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE state_country CASCADE;
TRUNCATE TABLE user_role CASCADE;

ALTER SEQUENCE address_id_seq RESTART;
ALTER SEQUENCE author_id_seq RESTART;
ALTER SEQUENCE book_id_seq RESTART;
ALTER SEQUENCE city_id_seq RESTART;
ALTER SEQUENCE country_id_seq RESTART;
ALTER SEQUENCE language_id_seq RESTART;
ALTER SEQUENCE publisher_id_seq RESTART;
ALTER SEQUENCE state_id_seq RESTART;
ALTER SEQUENCE subject_id_seq RESTART;
ALTER SEQUENCE user_id_seq RESTART;

--Address
INSERT INTO country (id, name) 
values
 (1, 'Brasil'),
 (2, 'United States'),
 (3, 'Canada');

INSERT INTO state_country (country_id, id, name) 
values 
(1, 1, 'Santa Catarina'),
(1, 2, 'Rio Grande do Sul'),
(2, 3, 'California'),
(2, 4, 'Florida'),
(3, 5, 'Ontario'),
(3, 6, 'Britsh Columbia');

INSERT INTO city (country_id, state_id, id, name) 
values 
(1, 1, 1, 'Florianopolis'),
(1, 1, 2, 'Itajaí'),
(1, 2, 3, 'Taquara'),
(1, 2, 4, 'Gramado'),
(2, 3, 5, 'San Francisco'),
(2, 4, 6, 'Miami'),
(3, 5, 7, 'Ottawa'),
(3, 6, 8, 'Vancuver'),
(3, 6, 9, 'Victoria');

INSERT INTO address 
    (id,
    cep,
    coordination,    
    logradouro,
    name,
    neighborhood,
    number,
    referential_point,
    zip_code,
    city_id,
    create_by,
    create_at)
VALUES
(1,'88056001', null,'STREET', 'Celso Ramos', 'Centro', '1', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(2,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '2', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(3,'88056001', null,'STREET', 'Celso Ramos', 'Centro', '3', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(4,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '4', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(5,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '5', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(6,'88056001', null,'STREET', 'Celso Ramos', 'Centro', '6', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(7,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '7', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(8,'88056001', null,'SQUARE', 'Celso Ramos', 'Centro', '8', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(9,'88056001', null,'STREET', 'Celso Ramos', 'Centro', '9', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(10,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '10', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(11,'88056001', null,'SQUARE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(12,'88056001', null,'ALLEY', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(13,'88056001', null,'SQUARE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(14,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(15,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(16,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(17,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(18,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(19,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(20,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(21,'88056001', null,'ROAD', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(22,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(23,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(24,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(25,'88056001', null,'ROAD', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(26,'88056001', null,'ROAD', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(27,'88056001', null,'ROAD', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(28,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(29,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1, 'InitialSeed.sql', NOW()),
(30,'88056011', null,'STREET', 'Corujas', 'Cachoeira do Bom Jesus', '269', 'Atrás  mercado', null, 1, 'InitialSeed.sql', NOW()),
(31,'88056111', null,'ROAD', 'SC401', 'Cachoeira B. Jesus', '369', 'Lado mercado', null, 1, 'InitialSeed.sql', NOW()),
(32,'88056222', null,'ALLEY', 'Main', 'Downtown', '123', 'Next ', null, 5, 'InitialSeed.sql', NOW()),
(33,'88056333', null,'STREET', 'First', 'Downtown', '456', '', null, 6, 'InitialSeed.sql', NOW()),
(34,'88056333', null,'STREET', 'First', 'Downtown', '457', '', null, 6, 'InitialSeed.sql', NOW()),
(35,'88056333', null,'STREET', 'First', 'Downtown', '458', '', null, 6, 'InitialSeed.sql', NOW()),
(36,'88056333', null,'STREET', 'First', 'Downtown', '459', '', null, 6, 'InitialSeed.sql', NOW()),
(37,'88056333', null,'STREET', 'First', 'Downtown', '460', '', null, 6, 'InitialSeed.sql', NOW()),
(38,'88056333', null,'STREET', 'First', 'Downtown', '461', '', null, 6, 'InitialSeed.sql', NOW()),
(39,'88056333', null,'STREET', 'First', 'Downtown', '462', '', null, 6, 'InitialSeed.sql', NOW()),
(40,'88056333', null,'STREET', 'First', 'Downtown', '463', '', null, 6, 'InitialSeed.sql', NOW()),
(41,'88056333', null,'STREET', 'First', 'Downtown', '464', '', null, 6, 'InitialSeed.sql', NOW()),
(42,'88056444', null,'STREET', 'Second', 'Downtown', '789', 'Near', null, 7, 'InitialSeed.sql', NOW());

ALTER SEQUENCE address_id_seq RESTART WITH 43;

--Author
INSERT INTO author
(id,
birthdate,
name,
email,
sex,
address_id,
birth_city_id,
birth_country_id,
description,
create_by,
create_at)
VALUES
(1,'1947-09-21', 'Stephen King', 'stephenking@gmail.com', 'M', 1, 1, 1, 'Autor de terror, suspense e ficção', 'InitialSeed.sql', NOW()),
(2,'1947-08-02', 'James Patterson', 'jamespatterson@gmail.com', 'M', 2, 1, 1, 'Autor de ficção criminal', 'InitialSeed.sql', NOW()),
(3,'1949-10-07', 'John Grisham', 'johngrisham@gmail.com', 'M', 3, 1, 1, 'Autor de ficção legal', 'InitialSeed.sql', NOW()),
(4,'1964-11-17', 'Jeff Kinney', 'jeffkinney@gmail.com', 'M', 4, 1, 1, 'Autor de livros infantis', 'InitialSeed.sql', NOW()),
(5,'1971-03-24', 'Suzanne Collins', 'suzannecollins@gmail.com', 'F', 5, 1, 1, 'Autora de ficção distópica', 'InitialSeed.sql', NOW()),
(6,'1955-12-31', 'J.K. Rowling', 'jkrowling@gmail.com', 'F', 6, 1, 1, 'Autora de livros infanto-juvenis', 'InitialSeed.sql', NOW()),
(7,'1973-06-05', 'Dan Brown', 'danbrown@gmail.com', 'M', 7, 1, 1, 'Autor de ficção de suspense', 'InitialSeed.sql', NOW()),
(8,'1977-07-28', 'Dav Pilkey', 'davpilkey@gmail.com', 'M', 8, 1, 1, 'Autor de livros infantis', 'InitialSeed.sql', NOW()),
(9,'1954-09-21', 'Margaret Atwood', 'margaretatwood@gmail.com', 'F', 9, 1, 1, 'Autora de ficção distópica', 'InitialSeed.sql', NOW()),
(10,'1941-04-12', 'Tom Clancy', 'tomclancy@gmail.com', 'M', 10, 1, 1, 'Autor de ficção militar e política', 'InitialSeed.sql', NOW()),
(11,'1961-09-04', 'Sarah J. Maas', 'sarahjmaas@gmail.com', 'F', 11, 1, 1, 'Autora de ficção fantástica', 'InitialSeed.sql', NOW()),
(12,'1960-02-24', 'James Dashner', 'jamesdashner@gmail.com', 'M', 12, 1, 1, 'Autor de ficção distópica', 'InitialSeed.sql', NOW()),
(13,'1958-01-31', 'Anne Rice', 'annerice@gmail.com', 'F', 13, 1, 1, 'Autora de terror e ficção', 'InitialSeed.sql', NOW()),
(14,'1999-12-30', 'Carlos da Silva', 'pedro@santos.com.br', 'M', 14, 1, 1, 'Uma autor legal 2', 'InitialSeed.sql', NOW()),
(15,'1999-12-30', 'Daniel da Silva', 'pedro@santos.com.br', 'O', 15, 1, 1, 'Uma autor legal 2', 'InitialSeed.sql', NOW()),
(16,'1999-12-30', 'Fernando da Silva', 'pedro@santos.com.br', 'M', 16, 1, 1, 'Uma autor legal 2', 'InitialSeed.sql', NOW()),
(17,'1999-12-30', 'Leandro da Silva', 'pedro@santos.com.br', 'M', 17, 1, 1, 'Uma autor legal 2', 'InitialSeed.sql', NOW()),
(18,'1979-11-30', 'Ana Ferreira', 'maria@ferreira.com.br', 'O', 18, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW()),
(19,'1979-11-30', 'Beatriz Ferreira', 'maria@ferreira.com.br', 'F', 19, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW()),
(20,'1979-11-30', 'Claudia Ferreira', 'maria@ferreira.com.br', 'F', 20, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW()),
(21,'1979-11-30', 'Deise Ferreira', 'maria@ferreira.com.br', 'F', 21, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW()),
(22,'1979-11-30', 'Eliane Ferreira', 'maria@ferreira.com.br', 'F', 22, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW()),
(23,'1979-11-30', 'Tereza Ferreira', 'maria@ferreira.com.br', 'F', 23, 1, 1, 'Uma autor legal3', 'InitialSeed.sql', NOW());

ALTER SEQUENCE author_id_seq RESTART WITH 24;

--Publisher
INSERT INTO publisher
(id,
cnpj,
foundation_date,
name,
address_id,
create_by,
create_at)
VALUES
(1,'55650490000163', '1945-05-01', 'Penguin Random House', 24, 'InitialSeed.sql', NOW()),
(2,'09436964000151', '1925-02-01', 'HarperCollins Publishers', 25, 'InitialSeed.sql', NOW()),
(3,'55447123000167', '1994-03-01', 'Simon & Schuster', 26, 'InitialSeed.sql', NOW()),
(4,'44496946000166', '2000-01-01', 'Hachette Livre', 27, 'InitialSeed.sql', NOW()),
(5,'32093261000190', '1915-03-01', 'Wiley-Blackwell', 28, 'InitialSeed.sql', NOW()),
(6,'36910986000184', '1995-01-01', 'Oxford University Press', 29, 'InitialSeed.sql', NOW()),
(7,'41696768000129', '1921-01-01', 'Cambridge University Press', 30, 'InitialSeed.sql', NOW()),
(8,'71178676000118', '1900-01-01', 'Macmillan Publishers', 31, 'InitialSeed.sql', NOW()),
(9,'17183657000134', '1947-01-01', 'Scholastic Corporation', 32, 'InitialSeed.sql', NOW()),
(10,'95071070000147', '1836-06-04', 'John Wiley & Sons', 33, 'InitialSeed.sql', NOW()),
(11,'73342143000155', '1978-01-01', 'MIT Press', 34, 'InitialSeed.sql', NOW()),
(12,'55247651000172', '1952-01-01', 'Princeton University Press', 35, 'InitialSeed.sql', NOW()),
(13,'26834704000153', '1970-01-01', 'Springer Science+Business Media', 36, 'InitialSeed.sql', NOW()),
(14,'31165807000108', '1932-01-01', 'Harvard University Press', 37, 'InitialSeed.sql', NOW()),
(15,'97883664000188', '1967-01-01', 'University of California Press', 38, 'InitialSeed.sql', NOW()),
(16,'51927333000100', '1891-01-01', 'University of Chicago Press', 39, 'InitialSeed.sql', NOW()),
(17,'84857725000190', '2002-01-01', 'Taylor & Francis', 40, 'InitialSeed.sql', NOW()),
(18,'59190425000153', '2003-01-01', 'Pearson Education', 41, 'InitialSeed.sql', NOW()),
(19,'16715560000162', '2009-01-01', 'Editora Bloomsbury Publishing', 42, 'InitialSeed.sql', NOW());

ALTER SEQUENCE publisher_id_seq RESTART WITH 20;

--Book
INSERT INTO book_subject (id, description, name)
VALUES
(1, 'Fiction books that focus on romantic love between characters', 'Romance'),
(2, 'Books that provide information and guidance on starting and running a business', 'Business'),
(3, 'Books that provide information and guidance on teaching and learning', 'Education'),
(4, 'Books that provide advice and guidance on self-improvement and personal growth', 'Self-help'),
(5, 'Books that provide information and guidance on cooking and preparing food', 'Cookbooks'),
(6, 'Books that explore the lives and experiences of individuals and cultures', 'Biographies/Memoirs'),
(7, 'Books that provide information and guidance on health, fitness, and wellness', 'Health/Fitness'),
(8, 'Books that provide information and guidance on history and current events', 'History/Current Events'),
(9, 'Books that provide information and guidance on science and technology', 'Science/Technology'),
(10, 'Books that provide information and guidance on travel and exploration', 'Travel');

INSERT INTO language (id, name) 
VALUES 
(1, 'Português'),
(2, 'English'),
(3, 'Español');

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
    subject_id,
    adults_only,
    create_by,
    create_at)
VALUES
(1,'NEW', 1,'HARDCOVER', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Clean Code', 'Conheça Ti', 2, 1, 1, false, 'InitialSeed.sql', NOW()),
(2,'NEW', 2,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Domain Design Driven', 'Conheça Ti', 2, 2, 1, false, 'InitialSeed.sql', NOW()),
(3,'NEW', 1,'HARDCOVER', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Recursos Humanos', 'Conheça Ti', 2, 3, 3, false, 'InitialSeed.sql', NOW()),
(4,'NEW', 2,'HARDCOVER', 200, 'HTTPS://WWW.GOOGLE.COM', '2002-01-30', 4.7, 'Um livro bom', 'Inteligencia Positiva', 'Conheça Ti', 2, 4, 1, false, 'InitialSeed.sql', NOW()),
(5,'USED', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'A Arte da Sedução', 'Conheça Ti', 2, 5, 1, false, 'InitialSeed.sql', NOW()),
(6,'NEW', 2,'KINDLE_EDITION', 200, 'HTTPS://WWW.GOOGLE.COM', '2001-01-30', 4.7, 'Um livro bom', 'O Livro da Economia', 'Conheça Ti', 1, 6, 1, false, 'InitialSeed.sql', NOW()),
(7,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2001-01-30', 4.7, 'Um livro bom', 'Don Quixote', 'Conheça Ti', 1, 7, 1, false, 'InitialSeed.sql', NOW()),
(8,'USED', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Um Conto de Duas Cidades', 'Conheça Ti 2', 1, 8, 1, false, 'InitialSeed.sql', NOW()),
(9,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O alquimista', 'Conheça Ti 3', 1, 9, 1, false, 'InitialSeed.sql', NOW()),
(10,'NEW', 1,'KINDLE_EDITION', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Senhor dos Anéis', 'Conheça Ti', 1, 10, 1, false, 'InitialSeed.sql', NOW()),
(11,'NEW', 1,'KINDLE_EDITION', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'O Pequeno Príncipe', 'Conheça Ti 2', 1, 11, 1, false, 'InitialSeed.sql', NOW()),
(12,'USED', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Harry Potter e a Pedra Filosofal', 'Conheça Ti 3', 1, 12, 1, false, 'InitialSeed.sql', NOW()),
(13,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Código Da Vinci', 'Conheça Ti', 1, 13, 1, false, 'InitialSeed.sql', NOW()),
(14,'NEW', 1,'AUDIO_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Pense e Enriqueça', 'Conheça Ti 2', 1, 14, 1, false, 'InitialSeed.sql', NOW()),
(15,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Cem Anos de Solidão', 'Conheça Ti 3', 1, 15, 1, false, 'InitialSeed.sql', NOW()),
(16,'NEW', 1,'AUDIO_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Nome da Rosa', 'Conheça Ti', 1, 16, 1, false, 'InitialSeed.sql', NOW()),
(17,'COLLECTABLE', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Culpa É das Estrelas', 'Conheça Ti 2', 1, 17, 1, false, 'InitialSeed.sql', NOW()),
(18,'COLLECTABLE', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Boa Noite Lua', 'Conheça Ti 3', 1, 18, 1, false, 'InitialSeed.sql', NOW()),
(19,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'The Poky Little Puppy', 'Conheça Ti', 1, 19, 1, false, 'InitialSeed.sql', NOW()),
(20,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Os Pilares da Terra', 'Conheça Ti 2', 1, 2, 1, false, 'InitialSeed.sql', NOW()),
(21,'COLLECTABLE', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Perfume', 'Conheça Ti 3', 1, 3, 1, false, 'InitialSeed.sql', NOW()),
(22,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Profeta', 'Conheça Ti', 1, 4, 1, false, 'InitialSeed.sql', NOW()),
(23,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Noite', 'Conheça Ti 2', 1, 5, 1, false, 'InitialSeed.sql', NOW()),
(24,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'A Mulher Total', 'Conheça Ti 3', 1, 6, 1, false, 'InitialSeed.sql', NOW()),
(25,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Pequeno Manual Antirracista', 'Conheça Ti', 1, 7, 1, false, 'InitialSeed.sql', NOW()),
(26,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Mulheres que Correm com os Lobos', 'Conheça Ti 2', 1, 8, 1, false, 'InitialSeed.sql', NOW()),
(27,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', '1984', 'Conheça Ti 3', 1, 9, 1, false, 'InitialSeed.sql', NOW()),
(28,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Poder do Hábito', 'Conheça Ti', 1, 10, 1, false, 'InitialSeed.sql', NOW()),
(29,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'O Morro dos Ventos Uivantes', 'Conheça Ti 2', 1, 11, 1, false, 'InitialSeed.sql', NOW()),
(30,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Milagre da Manhã: O Segredo para Transformar Sua Vida (Antes das 8 Horas)', 'Conheça Ti 3', 1, 13, 1, false, 'InitialSeed.sql', NOW()),
(31,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Mais Esperto que o Diabo: O Mistério Revelado da Liberdade e do Sucesso', 'Conheça Ti', 1, 14, 1, false, 'InitialSeed.sql', NOW()),
(32,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'A Revolução dos Bichos: Um Conto de Fadas', 'Conheça Ti 2', 1, 15, 1, false, 'InitialSeed.sql', NOW()),
(33,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Racismo Estrutural', 'Conheça Ti 3', 1, 16, 1, false, 'InitialSeed.sql', NOW()),
(34,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'A Coragem de Ser Imperfeito', 'Conheça Ti', 1, 17, 1, false, 'InitialSeed.sql', NOW()),
(35,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Mindset: A Nova Psicologia do Sucesso', 'Conheça Ti 2', 1, 18, 1, false, 'InitialSeed.sql', NOW()),
(36,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'O Conto da Aia', 'Conheça Ti 3', 1, 3, 1, false, 'InitialSeed.sql', NOW()),
(37,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Do Mil ao Milhão – Sem Cortar o Cafezinho', 'Conheça Ti', 1, 19, 1, false, 'InitialSeed.sql', NOW()),
(38,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', '21 Lições Para o Século 21', 'Conheça Ti 2', 1, 2, 1, false, 'InitialSeed.sql', NOW()),
(39,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Essencialismo: A Disciplina da Busca por Menos', 'Conheça Ti 3', 1, 3, 1, false, 'InitialSeed.sql', NOW()),
(40,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'Os Segredos da Mente Milionária', 'Conheça Ti', 1, 4, 1, false, 'InitialSeed.sql', NOW()),
(41,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Escravidão – Vol. 1', 'Conheça Ti 2', 1, 5, 1, false, 'InitialSeed.sql', NOW()),
(42,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Um livro bom 3', 'Sapiens – Uma Breve História da Humanidade', 'Conheça Ti 3', 1, 6, 1, false, 'InitialSeed.sql', NOW()),
(43,'NEW', 1,'PRINTED_BOOK', 200, 'HTTPS://WWW.GOOGLE.COM', '2000-01-30', 4.7, 'Um livro bom', 'O Homem Mais Rico da Babilônia', 'Conheça Ti', 1, 7, 1, false, 'InitialSeed.sql', NOW()),
(44,'NEW', 1,'PRINTED_BOOK', 199, 'HTTPS://WWW.GOOGLE.COM', '2010-01-30', 4.6, 'Um livro bom 2', 'Madrugada Louca', 'Não recomendado para menores', 1, 8, 1, true, 'InitialSeed.sql', NOW()),
(45,'NEW', 1,'PRINTED_BOOK', 250, 'HTTPS://WWW.GOOGLE.COM', '2020-01-30', 4.8, 'Não recomendado para menores', 'Contos Picantes', 'Não recomendado para menores', 1, 9, 1, true, 'InitialSeed.sql', NOW());
ALTER SEQUENCE book_id_seq RESTART WITH 46;

INSERT INTO author_books
    (author_id,
    book_id
    )
VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10),
(10,24),
(11,11),
(11,25),
(12,12),
(12,26),
(13,13),
(13,27),
(14,14),
(14,28),
(15,15),
(15,29),
(15,37),
(16,16),
(16,30),
(16,38),
(17,17),
(17,31),
(17,39),
(18,18),
(18,32),
(18,40),
(19,19),
(19,33),
(19,41),
(20,20),
(20,34),
(20,42),
(21,21),
(21,35),
(21,43),
(22,22),
(22,36),
(22,44),
(23,23),
(23,37),
(23,45),
(22,45),
(21,44);

--User
insert into role (id, name) values
(1, 'ADMIN'),
(2, 'CLIENT'),
(3, 'OPERATOR');

insert into lbs_user
    (id,
     name,
     username,
     password,
     email,
     birthdate,
     cpf,
     sex,
     create_by,
     create_at)
values 
(1, 'Paulo Rodrigues', 'paulo', '{noop}1', 'teste@paulo.com', '1986-09-27', '52252641070', 'M', 'InitialSeed.sql', NOW()),
(2, 'Admin', 'admin', '{noop}123', 'teste@admin.com', NOW(), '45804502050', 'M', 'InitialSeed.sql', NOW()),
(3, 'Operator', 'operator', '{noop}123', 'operator@admin.com', NOW(), '07888952062', 'M', 'InitialSeed.sql', NOW()),
(4, 'Client Test', 'clientTest', '{noop}123', 'clientetest@admin.com', NOW(), '53632647062', 'M', 'InitialSeed.sql', NOW());

insert into user_role (user_id, role_id) values
(1,1),
(2,1),
(3,3),
(4,2);

ALTER SEQUENCE user_id_seq RESTART WITH 5;