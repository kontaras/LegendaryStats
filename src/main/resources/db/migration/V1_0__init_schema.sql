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
	user_name varchar
	--password
);

CREATE TABLE play (
	id long not null,
	player_id long,
	outcome varchar,
	scheme_id int,
	mastermind_id int,
	misc_hero_id int,
	villain1_id int,
	villain2_id int,
	villain3_id int,
	villain4_id int,
	henchman1_id int,
	henchman2_id int,
	henchman3_id int,
	FOREIGN KEY (player_id) REFERENCES user(id),
	FOREIGN KEY (scheme_id) REFERENCES scheme(id),
	FOREIGN KEY (mastermind_id) REFERENCES mastermind(id),
	FOREIGN KEY (misc_hero_id) REFERENCES hero(id),
	FOREIGN KEY (villain1_id) REFERENCES villain(id),
	FOREIGN KEY (villain2_id) REFERENCES villain(id),
	FOREIGN KEY (villain3_id) REFERENCES villain(id),
	FOREIGN KEY (villain4_id) REFERENCES villain(id),
	FOREIGN KEY (henchman1_id) REFERENCES henchman(id),
	FOREIGN KEY (henchman2_id) REFERENCES henchman(id),
	FOREIGN KEY (henchman3_id) REFERENCES henchman(id)
);

CREATE TABLE play_hero (
	hero_id int,
	play_id long,
	FOREIGN KEY (hero_id) REFERENCES hero(id),
	FOREIGN KEY (play_id) REFERENCES play(id)
);

CREATE TABLE play_villain (
	villain_id int,
	play_id long,
	FOREIGN KEY (villain_id) REFERENCES villain(id),
	FOREIGN KEY (play_id) REFERENCES play(id)
);

CREATE TABLE play_henchman (
	henchman_id int,
	play_id long,
	FOREIGN KEY (henchman_id) REFERENCES henchman(id),
	FOREIGN KEY (play_id) REFERENCES play(id)
);
