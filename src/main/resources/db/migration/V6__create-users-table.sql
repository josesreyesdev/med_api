CREATE TABLE IF NOT EXISTS users(
    id bigint not null auto_increment,
    login varchar(100) not null,
    pass varchar(300) not null,
    role varchar(100) not null,

    primary key(id)
);