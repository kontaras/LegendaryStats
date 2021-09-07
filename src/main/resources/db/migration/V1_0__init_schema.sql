CREATE TABLE release
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   PRIMARY KEY (id)
);
CREATE TABLE hero
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   release_id INT  NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE mastermind
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   release_id INT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE villain
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   release_id INT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE henchman
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   release_id INT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE scheme
(
   id INT NOT NULL,
   marvel_name VARCHAR,
   dxp_name VARCHAR,
   mcu_name VARCHAR,
   release_id INT NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (release_id) REFERENCES release (id)
);
CREATE TABLE "user"
(
   id BIGINT NOT NULL,
   user_name VARCHAR NOT NULL,
   --password
   PRIMARY KEY (id)
);
CREATE TYPE game_outcome AS ENUM
(
   'WIN',
   'LOSS',
   'DRAW',
   'INCOMPLETE'
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
   id BIGINT NOT NULL,
   player_id BIGINT NOT NULL,
   outcome game_outcome NOT NULL,
   players player_count NOT NULL,
   scheme_id INT NOT NULL,
   mastermind_id INT NOT NULL,
   misc_hero_id INT,
   notes VARCHAR,
   PRIMARY KEY (id),
   FOREIGN KEY (player_id) REFERENCES "user" (id),
   FOREIGN KEY (scheme_id) REFERENCES scheme (id),
   FOREIGN KEY (mastermind_id) REFERENCES mastermind (id),
   FOREIGN KEY (misc_hero_id) REFERENCES hero (id)
);
CREATE TABLE play_hero
(
   hero_id INT NOT NULL,
   play_id BIGINT NOT NULL,
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
   villain_id INT NOT NULL,
   play_id BIGINT NOT NULL,
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
   henchman_id INT NOT NULL,
   play_id BIGINT  NOT NULL,
   PRIMARY KEY
   (
      henchman_id,
      play_id
   ),
   FOREIGN KEY (henchman_id) REFERENCES henchman (id),
   FOREIGN KEY (play_id) REFERENCES play (id)
);