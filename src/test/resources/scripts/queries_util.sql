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
select * from book b where title = 'GroovySpockTest'
select * from author au where name = 'GroovySpockTest'
select * from address a where name = 'GroovySpockTest'
select * from publisher p where name = 'GroovySpockTest'

delete from author where name = 'GroovySpockTest'
delete from address where name = 'GroovySpockTest'

--selects join
select * from book b 
join publisher p on p.id = b.publisher_id 
where p.cnpj = '77072141000144'


