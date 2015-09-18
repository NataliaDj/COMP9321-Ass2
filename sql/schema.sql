drop table book;
drop table users;
drop table admin;

create table book (
	id int not null generated always as identity,
	title varchar(20) not null,
	price int not null,
	constraint chk_price check
	(price>=0),
	author varchar(20) not null,
	pub_type varchar(20) not null, 
	constraint chk_type check
	(pub_type='book/collection' or pub_type='journal' or pub_type='conference' or pub_type='editorship'),
	pub_year int,
	isbn varchar(20),
	picture varchar (100),
	pause boolean not null,
	primary key (id)
);

create table users (
	username varchar(20) not null unique,
	password varchar(20) not null, 
	email varchar(30) not null,
	nickname varchar(10) not null,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	birth_year int(4) not null, 
	address varchar(100) not null,
	credit_card varchar(20) not null,
 	primary key (username)
);

create table admin (
	foreign key (user_id) references users(username),
	foreign key (book_id) references book(id),
);