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
    referencial_point,    
    zip_code,
    city_id)
VALUES
(1,'88056001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1),
(2,'88056011', null,'STREET', 'Corujas', 'Cachoeira do Bom Jesus', '269', 'Atrás  mercado', null, 1),
(3,'88056111', null,'ROD', 'SC401', 'Cachoeira B. Jesus', '369', 'Lado mercado', null, 1);

ALTER SEQUENCE address_id_seq RESTART WITH 4;

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
    description)
VALUES
(4,'1989-01-30', 'João da Silva', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(5,'1999-12-30', 'Pedro dos Santos', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(6,'1989-01-30', 'João dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(7,'1989-01-30', 'Andre dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(8,'1989-01-30', 'Carlos dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(9,'1989-01-30', 'Daniel dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(10,'1989-01-30', 'Fernando dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(11,'1989-01-30', 'Leandro dos Santos', 'joao@silva.com.br', 'M', 1, 1, 1, 'Uma autor legal'),
(12,'1999-12-30', 'Pedro da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(13,'1999-12-30', 'Andre da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(14,'1999-12-30', 'Carlos da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(15,'1999-12-30', 'Daniel da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(16,'1999-12-30', 'Fernando da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(17,'1999-12-30', 'Leandro da Silva', 'pedro@santos.com.br', 'M', 2, 1, 1, 'Uma autor legal 2'),
(18,'1979-11-30', 'Ana Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3'),
(19,'1979-11-30', 'Beatriz Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3'),
(20,'1979-11-30', 'Claudia Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3'),
(21,'1979-11-30', 'Deise Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3'),
(22,'1979-11-30', 'Eliane Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3'),
(23,'1979-11-30', 'Tereza Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1, 'Uma autor legal3');

ALTER SEQUENCE author_id_seq RESTART WITH 24;

--Publisher
INSERT INTO publisher 
    (id,
    cnpj,
    create_date,
    name,
    address_id)
VALUES
(1,'55650490000163', '1980-01-30', 'Editora A', 2),
(2,'09436964000151', '2015-11-30', 'Editora R', 2),
(3,'55447123000167', '1990-12-30', 'Empresa X', 2),
(4,'44496946000166', '2005-11-30', 'Editora B', 2),
(5,'32093261000190', '2005-11-30', 'Editora C', 2),
(6,'36910986000184', '2005-11-30', 'Editora D', 2),
(7,'41696768000129', '2005-11-30', 'Editora E', 2),
(8,'71178676000118', '2008-11-30', 'Editora F', 2),
(9,'17183657000134', '2008-11-30', 'Editora G', 2),
(10,'95071070000147', '2008-11-30', 'Editora H', 2),
(11,'73342143000155', '2020-11-30', 'Editora I', 2),
(12,'55247651000172', '2020-11-30', 'Editora J', 2),
(13,'26834704000153', '2020-11-30', 'Editora K', 2),
(14,'31165807000108', '2020-11-30', 'Editora L', 2),
(15,'97883664000188', '2019-11-30', 'Editora M', 2),
(16,'51927333000100', '2019-11-30', 'Editora N', 2),
(17,'84857725000190', '2019-11-30', 'Editora O', 2),
(18,'59190425000153', '2015-11-30', 'Editora P', 2),
(19,'16715560000162', '2015-11-30', 'Editora Q', 2);

ALTER SEQUENCE publisher_id_seq RESTART WITH 20;


--Book
INSERT INTO book_subject (id, description, name) 
VALUES (1, 'Romance', 'Romance');

INSERT INTO language (id, name) 
VALUES 
(1, 'Portugues'),
(2, 'English');

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

ALTER SEQUENCE book_id_seq RESTART WITH 46;

--User
insert into role (id, name) values
(1, 'ADMIN'),
(3, 'OPERATOR'),
(2, 'CLIENT');

insert into lbs_user (id, name, username, password) values 
(1, 'Paulo Rodrigues', 'paulo', '{noop}1');

insert into user_role (user_id, role_id) values
(1,1);