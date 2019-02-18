create database demo;

create table tab_persons
(
	id int primary key auto_increment,
    pname varchar(20) not null,
    gender smallint default 0 not null,
    birth date not null
);

insert into tab_persons(pname, gender, birth)
values ('lisi', 1, STR_TO_DATE('1982-11-11', '%Y-%m-%d')),('diaocan', 0, STR_TO_DATE('1988-09-12', '%Y-%m-%d'));


SELECT STR_TO_DATE('1982-11-11', '%Y-%m-%d');

select * from tab_persons;