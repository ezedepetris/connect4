create database if not exists connect4_development;
use connect4_development;

drop table if exists users;
create table users(
  id int not null auto_increment,
  email varchar(60) unique,
  first_name varchar(56),
  last_name varchar(56),
	constraint user_id primary key (id)
);

drop table if exists game;
create table games(
	id integer not null,
	grid_id integer not null,
	user1_id integer not null,
	user2_id integer not null
	constraint game_id primary key (id)
);

drop table if exists grids;
create table grids(
	id integer not null unique,
	constraint grid_id primary key (id)
);

drop table if exists cells;
create table cells(
	id integer not null,
	pos_x integer not null,
	pos_y integer not null,
	grid_id integer not null,
	user_id integer not null,
	constraint cells_id primary key (id)
);

drop table if exists games_users;
create table games_users(
	id integer not null,
	grid_id integer not null,
	user_id integer not null
	-- este teiene las claves foraneas a de usuario y la de tablero
);


