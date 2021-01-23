CREATE TABLE set (
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
	FOREIGN KEY (set_id) REFERENCES set(id)
);

CREATE TABLE mastermind (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar,
	set_id int,
	FOREIGN KEY (set_id) REFERENCES set(id)
);


CREATE TABLE villain (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar,
	set_id int,
	FOREIGN KEY (set_id) REFERENCES set(id)
);

CREATE TABLE henchman (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar,
	set_id int,
	FOREIGN KEY (set_id) REFERENCES set(id)
);

CREATE TABLE scheme (
	id int not null,
	marvel_name varchar,
	dxp_name varchar,
	mcu_name varchar,
	set_id int,
	FOREIGN KEY (set_id) REFERENCES set(id)
);

CREATE TABLE user (
	id long not null,
	userName varchar
	--password
);

CREATE TABLE play (
	id long not null,
	player_id long,
	outcome varchar,
	--scheme
	mastermind_id int,
	hero1_id int,
	hero2_id int,
	hero3_id int,
	hero4_id int,
	hero5_id int,
	hero6_id int,
	misc_hero_id int,
	villain1_id int,
	villain2_id int,
	villain3_id int,
	villain4_id int,
	henchman1_id int,
	henchman2_id int,
	henchman3_id int,
	FOREIGN KEY (player_id) REFERENCES user(id),
	FOREIGN KEY (mastermind_id) REFERENCES mastermind(id),
	FOREIGN KEY (hero1_id) REFERENCES hero(id),
	FOREIGN KEY (hero2_id) REFERENCES hero(id),
	FOREIGN KEY (hero3_id) REFERENCES hero(id),
	FOREIGN KEY (hero4_id) REFERENCES hero(id),
	FOREIGN KEY (hero5_id) REFERENCES hero(id),
	FOREIGN KEY (hero6_id) REFERENCES hero(id),
	FOREIGN KEY (misc_hero_id) REFERENCES hero(id),
	FOREIGN KEY (villain1_id) REFERENCES villain(id),
	FOREIGN KEY (villain2_id) REFERENCES villain(id),
	FOREIGN KEY (villain3_id) REFERENCES villain(id),
	FOREIGN KEY (villain4_id) REFERENCES villain(id),
	FOREIGN KEY (henchman1_id) REFERENCES henchman(id),
	FOREIGN KEY (henchman2_id) REFERENCES henchman(id),
	FOREIGN KEY (henchman3_id) REFERENCES henchman(id)
);