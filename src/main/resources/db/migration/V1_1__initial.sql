CREATE TABLE IF NOT EXISTS `app_user` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(64),
    `email` varchar(120),
    `password` varchar(128),
    `confirmed` tinyint,
    unique index app_user_email_uindex (email)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS confirm_link
(
    id int auto_increment,
    user_id int not null,
    confirm_hash varchar(128) not null,
    constraint confirm_link_pk
        primary key (id),
    constraint confirm_link_app_user_id_fk
        foreign key (user_id) references app_user (id)
);

CREATE TABLE IF NOT EXISTS ip
(
    id int auto_increment,
    ip_address varchar(16) not null,
    constraint user_ip_pk
        primary key (id),
    unique index user_ip_ip_address_uindex (ip_address)
);

CREATE TABLE IF NOT EXISTS ip_application
(
    id int auto_increment,
    ip_id int not null,
    application_date date not null,
    total_application_point int null,
    constraint ip_application_pk
        primary key (id),
    constraint ip_application_user_ip_id_fk
        foreign key (ip_id) references ip (id),
    unique index ip_application_ip_id_application_date_index (ip_id, application_date)
);