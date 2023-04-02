CREATE SEQUENCE account_ids AS BIGINT;
CREATE TABLE account
(
   id INT DEFAULT nextval('account_ids'),
   user_name VARCHAR UNIQUE NOT NULL,
   password VARCHAR NOT NULL,
   email VARCHAR,
   PRIMARY KEY (id)
);

CREATE TABLE play
(
   id BIGINT NOT NULL,
   player_id BIGINT NOT NULL,
   play_date DATE,
   outcome varchar NOT NULL,
   players varchar NOT NULL,
   scheme_id INT NOT NULL,
   mastermind_id INT NOT NULL,
   board_id INT NOT NULL,
   notes VARCHAR,
   misc_hero_id INT,
   PRIMARY KEY (id),
   FOREIGN KEY (player_id) REFERENCES account (id)
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
   FOREIGN KEY (play_id) REFERENCES play (id)
);
CREATE TABLE play_support
(
   support_id INT NOT NULL,
   play_id BIGINT  NOT NULL,
   PRIMARY KEY
   (
      support_id,
      play_id
   ),
   FOREIGN KEY (play_id) REFERENCES play (id)
);
CREATE TABLE play_starter
(
   starter_id INT NOT NULL,
   play_id BIGINT  NOT NULL,
   quantity INT NOT NULL,
   PRIMARY KEY
   (
      starter_id,
      play_id
   ),
   FOREIGN KEY (play_id) REFERENCES play (id)
);