INSERT INTO country (id, name) 
values
 (1, 'Brasil'),
 (2, 'United States'),
 (3, 'Canada');

INSERT INTO state_country (country_id, id, name) 
values 
(1, 1, 'Santa Catarina'),
(1, 2, 'Rio Grande'),
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
(1,'88056-001', null,'AVENUE', 'Celso Ramos', 'Centro', '69', 'Frente mercado', null, 1),
(2,'88056-011', null,'STREET', 'Corujas', 'Cachoeira do Bom Jesus', '269', 'Atrás  mercado', null, 1),
(3,'88056-111', null,'ROD', 'SC401', 'Cachoeira do Bom Jesus', '369', 'Lado mercado', null, 1);
