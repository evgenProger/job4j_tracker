create table if not exists items
(
    id serial primary key not null,
    name varchar(70),
    description text,
    created bigint
);