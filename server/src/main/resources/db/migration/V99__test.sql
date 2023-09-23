INSERT INTO account (user_name, password) VALUES ('bob', 'bob');

INSERT INTO play (player_id, outcome, mastermind_id, scheme_id, players, board_id)  VALUES (1, 'WIN_DEFEAT_MASTERMIND', 101, 101, 'SOLO', 101);  

INSERT INTO play (player_id, outcome, mastermind_id, scheme_id, players, board_id)  VALUES (1, 'LOSS_SCHEME', 102, 102, 'SOLO', 101);  

INSERT INTO play (player_id, outcome, notes, play_date, mastermind_id, scheme_id, players, board_id)  VALUES (1, 'DRAW', 'didn''t play', '2022-12-12', 101, 101, 'SOLO', 101);  

INSERT INTO play (player_id, outcome, mastermind_id, scheme_id, players, board_id)  VALUES (1, 'WIN_DEFEAT_MASTERMIND', 102, 103, 'SOLO', 101);  

  

INSERT INTO play_hero (play_id, hero_id) VALUES (1, 101); 

INSERT INTO play_hero (play_id, hero_id) VALUES (2, 101); 

INSERT INTO play_hero (play_id, hero_id) VALUES (2, 102); 

INSERT INTO play_hero (play_id, hero_id) VALUES (3, 101); 

INSERT INTO play_hero (play_id, hero_id) VALUES (3, 103); 

INSERT INTO play_hero (play_id, hero_id) VALUES (3, 104); 

INSERT INTO play_hero (play_id, hero_id) VALUES (4, 102); 

  

INSERT INTO play_villain (play_id, villain_id) VALUES (1, 101); 

INSERT INTO play_villain (play_id, villain_id) VALUES (2, 102); 

INSERT INTO play_villain (play_id, villain_id) VALUES (3, 103); 

INSERT INTO play_villain (play_id, villain_id) VALUES (4, 104); 

  

INSERT INTO play_henchman (play_id, henchman_id) VALUES (1, 101); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (1, 102); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (2, 103); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (2, 104); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (3, 101); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (4, 102); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (4, 103); 

INSERT INTO play_henchman (play_id, henchman_id) VALUES (2, 102); 
