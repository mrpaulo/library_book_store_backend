INSERT INTO book_subject (id, description, name) 
VALUES (1, 'Romance', 'Romance');

INSERT INTO publisher 
    (id,
    cnpj,
    create_date,
    name,
    address_id)
VALUES
(2,'55650490000163', '2000-01-30', 'Editora A', 2),
(3,'55447123000167', '2001-12-30', 'Empresa X', 2),
(4,'44496946000166', '2005-11-30', 'Editora B', 2),
(5,'32093261000190', '2005-11-30', 'Editora C', 2),
(6,'36910986000184', '2005-11-30', 'Editora D', 2),
(7,'41696768000129', '2005-11-30', 'Editora E', 2),
(8,'71178676000118', '2005-11-30', 'Editora F', 2),
(9,'17183657000134', '2005-11-30', 'Editora G', 2),
(10,'95071070000147', '2005-11-30', 'Editora H', 2),
(11,'73342143000155', '2005-11-30', 'Editora I', 2),
(12,'55247651000172', '2005-11-30', 'Editora J', 2),
(13,'26834704000153', '2005-11-30', 'Editora K', 2),
(14,'31165807000108', '2005-11-30', 'Editora L', 2),
(15,'97883664000188', '2005-11-30', 'Editora M', 2),
(16,'51927333000100', '2005-11-30', 'Editora N', 2),
(17,'84857725000190', '2005-11-30', 'Editora O', 2),
(18,'59190425000153', '2005-11-30', 'Editora P', 2),
(19,'16715560000162', '2005-11-30', 'Editora Q', 2),
(20,'09436964000151', '2005-11-30', 'Editora R', 2);

ALTER SEQUENCE publisher_id_seq RESTART WITH 21;

INSERT INTO publisher (id, description) 
VALUES
(1, 'Uma editora confiavel'),
(3, 'Uma editora confiavel 2');
