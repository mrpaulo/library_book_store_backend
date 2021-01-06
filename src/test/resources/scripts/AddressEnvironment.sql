INSERT INTO country (id, name) values (1, 'Brasil');
INSERT INTO state_country (id, name) values (1, 'Santa Catarina');
INSERT INTO city (id, name) values (1, 'Florianopolis');

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
