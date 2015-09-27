drop table admin;
drop table publications;
drop table users;

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
	first_name varchar(20),
	last_name varchar(20),
	birth_year numeric(4,0), 
	address varchar(100),
	credit_card bigint,
	account_activated numeric(1,0) DEFAULT 0,
 	primary key (username) 
 	
);

create table admin (
	admin_id varchar(20) not null,
	pub_id integer not null,
	foreign key (admin_id) references users(username),
	foreign key (pub_id) references publications(id),
	primary key (admin_id, pub_id)
);