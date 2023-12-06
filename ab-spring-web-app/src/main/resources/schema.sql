CREATE TABLE IF NOT EXISTS users (
    id SERIAL not null primary key,
    name varchar(127),
    login varchar(127) NOT NULL,
    password varchar(127) NOT NULL,
    url varchar(255)
);