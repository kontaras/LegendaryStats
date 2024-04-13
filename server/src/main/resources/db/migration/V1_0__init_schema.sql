CREATE SEQUENCE account_ids AS BIGINT;
CREATE TABLE account
(
   id INT DEFAULT nextval('account_ids'),
   user_name VARCHAR UNIQUE NOT NULL,
   password VARCHAR NOT NULL,
   email VARCHAR,
   PRIMARY KEY (id)
);

CREATE SEQUENCE play_ids AS BIGINT;
CREATE TABLE play
(
   id BIGINT DEFAULT nextval('play_ids'),
   player_id BIGINT NOT NULL,
   notes VARCHAR,
   outcome varchar NOT NULL,
   players varchar NOT NULL,
   mod_date TIMESTAMP DEFAULT NOW(),
   
   PRIMARY KEY (id),
   FOREIGN KEY (player_id) REFERENCES account (id)
);

CREATE TYPE component_type AS ENUM (
	'hero',
	'scheme',
	'mastermind',
	'villain',
	'henchman',
	'support',
	'board'
);

CREATE TABLE play_component (
	play_id BIGINT  NOT NULL,
	c_type component_type NOT NULL, 
	component_id INT NOT NULL,
	PRIMARY KEY (
		component_id,
		c_type,
		play_id
	),
	FOREIGN KEY (play_id) REFERENCES play (id)
);
CREATE INDEX play_component_index ON play_component(play_id);

CREATE TABLE play_starter
(
   play_id BIGINT  NOT NULL,
   starter_id INT NOT NULL,
   quantity INT NOT NULL,
   PRIMARY KEY
   (
      starter_id,
      play_id
   ),
   FOREIGN KEY (play_id) REFERENCES play (id)
);
