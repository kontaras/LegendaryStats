CREATE TABLE release
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   PRIMARY KEY (id)
);
CREATE TABLE hero
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   release_id int,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE mastermind
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   release_id int,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE villain
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   release_id int,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE henchman
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   release_id int,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE scheme
(
   id int not null,
   marvel_name varchar,
   dxp_name varchar,
   mcu_name varchar,
   release_id int,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE user
(
   id long not null,
   user_name varchar,
   PRIMARY KEY (id) --password
);
CREATE TYPE game_outcome AS ENUM
(
   'WIN',
   'LOSS',
   'DRAW'
);
CREATE TYPE player_count AS ENUM
(
   'SOLO',
   'ADVANCED',
   'TWO',
   'THREE',
   'FOUR',
   'FIVE'
);
CREATE TABLE play
(
   id long not null,
   player_id long,
   outcome game_outcome,
   players player_count,
   scheme_id int,
   mastermind_id int,
   misc_hero_id int,
   notes varchar,
   PRIMARY KEY (id),
   FOREIGN KEY (player_id) REFERENCES user (id),
   FOREIGN KEY (scheme_id) REFERENCES scheme (id),
   FOREIGN KEY (mastermind_id) REFERENCES mastermind (id),
   FOREIGN KEY (misc_hero_id) REFERENCES hero (id)
);
CREATE TABLE play_hero
(
   hero_id int,
   play_id long,
   PRIMARY KEY
   (
      hero_id,
      play_id
   ),
   FOREIGN KEY (hero_id) REFERENCES hero (id),
   FOREIGN KEY (play_id) REFERENCES play (id)
);
CREATE TABLE play_villain
(
   villain_id int,
   play_id long,
   PRIMARY KEY
   (
      villain_id,
      play_id
   ),
   FOREIGN KEY (villain_id) REFERENCES villain (id),
   FOREIGN KEY (play_id) REFERENCES play (id)
);
CREATE TABLE play_henchman
(
   henchman_id int,
   play_id long,
   PRIMARY KEY
   (
      henchman_id,
      play_id
   ),
   FOREIGN KEY (henchman_id) REFERENCES henchman (id),
   FOREIGN KEY (play_id) REFERENCES play (id)
);