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