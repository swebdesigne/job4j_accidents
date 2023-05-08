CREATE TABLE IF NOT EXISTS users
(
    id           serial primary key,
    username     VARCHAR(50)  NOT NULL unique,
    password     VARCHAR(100) NOT NULL,
    enabled      boolean default true,
    authority_id int          not null references authorities (id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$Sm/y7VoTLQ5MYuxNTX0Ml./0KMZMV3qHl/iXwYuJGQx4j/vC5z9Le',
        (select id from authorities where authority = 'ROLE_ADMIN'));