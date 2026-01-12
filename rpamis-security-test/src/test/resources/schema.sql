create table test_version
(
    id      bigint auto_increment
        primary key,
    name    varchar(50) null,
    id_card varchar(255) null,
    phone   varchar(255) null,
    version int default 0 null,
    constraint id
        unique (id)
);

create table test_nest
(
    id           int auto_increment
        primary key,
    user_account varchar(255) null
);