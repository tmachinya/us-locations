CREATE table test (
    id serial  primary key,
    name char(50),
    amount numeric(9,6)
);

insert into test (name, amount) values ('Brighton', 200);
