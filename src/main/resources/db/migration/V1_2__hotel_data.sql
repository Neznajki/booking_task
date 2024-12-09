CREATE TABLE IF NOT EXISTS hotel
(
    id int auto_increment,
    user_id int not null,
    name varchar(64) not null,
    address varchar(64) not null,
    floors int not null,
    constraint hotel_app_user_id_fk
        foreign key (user_id) references app_user (id),
    constraint hotel_pk
        primary key (id),
    unique index hotel_name_u (name)
);

CREATE TABLE IF NOT EXISTS hotel_room
(
    id int auto_increment,
    hotel_id int not null,
    name varchar(64) not null,
    floor int not null,
    max_persons int not null,
    constraint hotel_app_hotel_id_fk
        foreign key (hotel_id) references hotel (id),
    constraint hotel_room_pk
        primary key (id),
    unique index hotel_room_u (name, floor)
);

CREATE TABLE IF NOT EXISTS hotel_room_reservation
(
    id int auto_increment,
    room_id int not null,
    user_id int not null,
    visit_date date not null,
    constraint hotel_room_reservation_hotel_room_id_fk
        foreign key (room_id) references hotel_room (id),
    constraint hotel_room_reservation_app_user_id_fk
        foreign key (user_id) references app_user (id),
    constraint hotel_room_reservation_pk
        primary key (id),
    unique index hotel_room_reservation_u (room_id, visit_date)
);