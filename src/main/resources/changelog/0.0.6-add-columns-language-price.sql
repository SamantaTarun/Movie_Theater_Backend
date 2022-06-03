Alter table movies
add column price DECIMAL,
add column language varchar(50);


update movies
set price = 100.0;

update movies
set language='English';
