CREATE TABLE SET (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar
);

CREATE TABLE hero (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar,
	set_id int,
	FOREIGN KEY (set_id) REFERENCES SET(id)
);