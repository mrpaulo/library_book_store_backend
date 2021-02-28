INSERT INTO person 
    (id,
    cpf,
    birthdate,
    name,
    email,
    sex,
    address_id,
    birth_city_id,
    birth_country_id)
VALUES
(1,'40445502045', '1989-01-30', 'João da Silva', 'joao@silva.com.br', 'M', 1, 1, 1),
(2,'36126697022', '1999-12-30', 'Pedro dos Santos', 'pedro@santos.com.br', 'M', 2, 1, 1),
(3,'33464171078', '1979-11-30', 'Maria Ferreira', 'maria@ferreira.com.br', 'F', 3, 1, 1);

INSERT INTO author (id, description) 
VALUES
(1, 'Uma autor legal'),
(3, 'Uma editora legal 2');