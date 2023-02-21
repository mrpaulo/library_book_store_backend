

--selects
select * from address a;

select * from author au;

select * from author_books ab;

select * from book b;

select * from book_subject bs;

select * from city c;

select * from country co;

select * from flyway_schema_history fsh;

select * from "language" l;

select * from lbs_user lu;

select * from publisher p;

select * from "role" r;

select * from state_country sc;

select * from user_role ur;

--select from test2_library_book_store
select * from book b where title = 'GroovySpockTest_gfkgjoemf48';
select * from author au where name = 'GroovySpockTest_gfkgjoemf48';
select * from address a where name = 'GroovySpockTest_gfkgjoemf48';
select * from publisher p where name = 'GroovySpockTest_gfkgjoemf48';

delete from author where name = 'GroovySpockTest_gfkgjoemf48';
delete from address where name = 'GroovySpockTest_gfkgjoemf48';

--selects join
select * from book b
join publisher p on p.id = b.publisher_id
where p.cnpj = '77072141000144';

update publisher set description = 'test' where id = 21;

delete from author_books where book_id in (47,46);
delete from book where id in (47,46);


select a.name, p."name", b.title, b.*
from book b
join publisher p on p.id = b.publisher_id
join author_books ab ON ab.book_id = b.id
join author a on ab.author_id = a.id
order by a.name;


  SELECT b.id   , b.title   , b.subtitle   , b.language_id  , b.publisher_id  , b.subject_id  , b.review  , b.link  , b.format  , b.condition  , b.edition  , b.publish_date  , b.rating  , b.length
  FROM BOOK b
  LEFT JOIN  PUBLISHER pu ON pu.id = b.publisher_id
  join author_books ab ON ab.book_id = b.id
  join author a on ab.author_id = a.id
  WHERE 1 = 1
  AND LOWER(a.name) like LOWER(CONCAT('%ana%'));

delete from lbs_user where id = 99999

DROP TABLE IF EXISTS person;
