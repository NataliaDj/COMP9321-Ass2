drop table publications;
drop table users;
drop table admin;

create table publications (
	id integer not null generated always as identity,
	title varchar(40) not null,
	price integer not null,
	constraint chk_price check
	(price>=0),
	author varchar(20) not null,
	pub_type varchar(20) not null, 
	constraint chk_type check
	(pub_type='book/collection' or pub_type='journal' or pub_type='conference' or pub_type='editorship'),
	pub_year numeric(4,0),
	isbn varchar(20),
	picture varchar (100),
	pause boolean not null,
	primary key (id)
);

create table users (
	username varchar(20) not null,
	password varchar(20) not null, 
	email varchar(30) not null unique,
	nickname varchar(10) not null,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	birth_year numeric(4,0) not null, 
	address varchar(100) not null,
	credit_card bigint not null,
 	primary key (username)
);

create table admin (
	admin_id varchar(20) not null,
	pub_id integer not null,
	foreign key (admin_id) references users(username),
	foreign key (pub_id) references publications(id),
	primary key (admin_id, pub_id)
);