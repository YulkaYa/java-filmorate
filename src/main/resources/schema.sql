create table if not exists genres (
    genre_id integer not null primary key auto_increment,
    name varchar(255) not null unique,
    constraint genre_pk primary key (genre_id)
);

create table if not exists MPA (
    mpa_id integer not null primary key auto_increment,
    name varchar(255) not null unique,
    constraint mpa_pk primary key (mpa_id)
);

create table if not exists films (
    film_id bigint not null primary key auto_increment,
    name varchar(255) not null,
    releaseDate date,
    duration bigint,
    description varchar(200),
    mpa_id integer,
    constraint film_pk primary key (film_id),
    constraint constr_duration check (duration > 0),
    constraint films_fk_mpa foreign key (mpa_id) references MPA(mpa_id) on delete cascade on update cascade
);

create table if not exists users (
    user_id bigint not null primary key auto_increment,
    birthday date,
    login varchar(255) not null unique,
    name varchar(255) not null,
    email varchar(255) not null unique,
    constraint user_pk primary key (user_id)
);

create table if not exists genres_films (
    genre_id integer not null,
    film_id bigint not null,
    constraint genres_films_pk primary key (genre_id, film_id),
    constraint genres_films_fk_g foreign key (genre_id) references genres(genre_id) on delete cascade,
    constraint genres_films_fk_f foreign key (film_id) references films(film_id) on delete cascade
);

create table if not exists likes (
    user_id bigint not null,
    film_id bigint not null,
    constraint likes_pk primary key (user_id, film_id),
    constraint likes_fk_u foreign key (user_id) references users(user_id) on delete cascade,
    constraint likes_fk_f foreign key (film_id) references films(film_id) on delete cascade
);

create table if not exists friendship (
    user_id bigint not null,
    friend_id bigint not null,
    constraint friendship_pk primary key (user_id, friend_id),
    constraint friendship_fk_u foreign key (user_id) references users(user_id) on delete cascade,
    constraint friendship_fk_f foreign key (friend_id) references users(user_id) on delete cascade
);
