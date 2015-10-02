drop table admin;
drop table shopping_cart;
drop table publications;
drop table sellers;
drop table buyers;
drop table people;

create table people(
	username varchar(32) not null,
	password varchar(32) not null, 
	email varchar(32) not null unique,
	first_name varchar(32),
	last_name varchar(32),
	birth_year numeric(4,0), 
	address varchar(100),
	account_activated numeric(1,0) DEFAULT 0,
	ban boolean not null,
 	primary key (username)
);

create table buyers(
	buyer_id varchar(32) not null,
	credit_card bigint,
	foreign key(buyer_id) references people(username),
	primary key(buyer_id)
);

create table sellers(
	seller_id varchar(32) not null,
	paypal varchar(20) not null,
	foreign key(seller_id) references people(username),
	primary key(seller_id)
);

create table publications (
	id integer not null generated always as identity,
	title varchar(100) not null,
	price integer not null,
	constraint chk_price check
	(price>=0),
	author varchar(200) not null,
	pub_type varchar(20) not null, 
	constraint chk_type check
	(pub_type='book/collection' or pub_type='journal' or pub_type='conference' or pub_type='editorship'),
	pub_year numeric(4,0),
	isbn varchar(100),
	picture varchar (300),
	pause boolean not null,
	seller_id varchar(32),
	foreign key(seller_id) references sellers(seller_id),
	primary key (id)
);

create table shopping_cart(
	cart_id integer not null generated always as identity,
	added timestamp not null,
	removed timestamp,
	purchased timestamp,
	publication_key integer not null,
	buyer_key varchar(32) not null,
	foreign key(publication_key) references publications(id),
	foreign key(buyer_key) references buyers(buyer_id),
	primary key(cart_id)
);

create table admin (
	id varchar(20) not null,
	password varchar(32) not null,
	primary key (id)
);
