 CREATE TABLE address 
 (
    id bigint PRIMARY KEY NOT NULL,  
    cep character varying(8),
    coordination character varying(20),
    create_at timestamp without time zone,
    create_by character varying(255),
    logradouro character varying(255),
    name character varying(100),
    neighborhood character varying(100),
    number character varying(9),
    referential_point character varying(200),
    update_at timestamp without time zone,
    update_by character varying(255),
    zip_code character varying(12),
    city_id bigint
 );

 CREATE TABLE author 
 (
    id bigint PRIMARY KEY NOT NULL,  
    description character varying(255),
    book_id bigint
 );

 CREATE TABLE author_books 
 (
    author_id bigint,
    book_id bigint
 );

 CREATE TABLE book 
 (
    id bigint PRIMARY KEY NOT NULL,  
    condition character varying(255),
    create_at timestamp without time zone,
    create_by character varying(255),
    edition integer NOT NULL,
    format character varying(255),
    length integer NOT NULL,
    link character varying(100),
    publish_date date,
    rating double precision,
    review character varying(500),
    subtitle character varying(100),
    title character varying(100),
    update_at timestamp without time zone,
    update_by character varying(255),
    language_id bigint,
    publisher_id bigint,
    subject_id bigint
 );

 CREATE TABLE book_subject 
 (
    id bigint PRIMARY KEY NOT NULL,  
    description character varying(300),
    name character varying(100)
 );

 CREATE TABLE city 
 (
    id bigint PRIMARY KEY NOT NULL,  
    ibge_code character varying(10),
    name character varying(100),
    country_id bigint,
    state_id bigint
 );

 CREATE TABLE company 
 (
    id bigint PRIMARY KEY NOT NULL,  
    cnpj character varying(14),
    create_at timestamp without time zone,
    create_by character varying(255),
    foundation_date date,
    name character varying(100),
    update_at timestamp without time zone,
    update_by character varying(255),
    address_id bigint
 );

 CREATE TABLE country 
 (
    id bigint PRIMARY KEY NOT NULL,  
    name character varying(100)
 );

 CREATE TABLE language 
 (
    id bigint PRIMARY KEY NOT NULL,  
    name character varying(100)
 );

 CREATE TABLE person 
 (
    id bigint PRIMARY KEY NOT NULL,  
    birthdate date,
    cpf character varying(11),
    create_at timestamp without time zone,
    create_by character varying(255),
    email character varying(100),
    name character varying(100),
    sex character varying(1),
    update_at timestamp without time zone,
    update_by character varying(255),
    address_id bigint,
    birth_city_id bigint,
    birth_country_id bigint
 );

 

 CREATE TABLE state_country 
 (
    id bigint PRIMARY KEY NOT NULL,  
    name character varying(100),
    country_id bigint
 );