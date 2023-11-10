create table if not exists admin
(
    id       int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(20) not null
)
    charset = utf8;

create table if not exists book
(
    ISBN            varchar(60) not null
        primary key,
    Category_number varchar(20) null,
    Name            varchar(20) null,
    Category        varchar(20) null,
    price           float       null,
    number          int         null
);

create table if not exists borrowedbook
(
    ISBN        varchar(60) not null
        primary key,
    borrower1   varchar(20) null,
    expireDate1 varchar(30) null,
    borrower2   varchar(20) null,
    expireDate2 varchar(30) null,
    borrower3   varchar(20) null,
    expireDate3 varchar(30) null,
    borrower4   varchar(20) null,
    expireDate4 varchar(30) null
);

create table if not exists nobody
(
    id       int auto_increment
        primary key,
    username varchar(20) not null,
    password varchar(20) not null
)
    charset = utf8;


