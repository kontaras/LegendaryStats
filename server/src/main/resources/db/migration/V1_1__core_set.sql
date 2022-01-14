INSERT INTO release (id, marvel_name, dxp_name, mcu_name) VALUES (1, 'Core Set', 'Core Set', 'Phase 1');

INSERT INTO team (id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 'Avengers', 'Orrin''s Wall', 'Avengers'),
	(2, 'S.H.I.E.L.D.', 'Fixers', 'S.H.I.E.L.D.'),
	(3, 'Spider Friends', 'The Intrepid', NULL),
	(4, 'Unaffiliated', 'Unaffiliated', 'Unaffiliated'),
	(5, 'X-Men', 'Magi-Nation', NULL);

INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name, team_id) VALUES
	(1, 101, 'Black Widow', 'Steelvara the Light', 'Black Widow', 1),
	(1, 102, 'Captain America', 'Lord Cedric of the Citadel', 'Captain America', 1),
	(1, 103, 'Cyclops', 'Disaray the Sufferer', NULL, 5),
	(1, 104, 'Deadpool', 'Nunchi', NULL, 4),
	(1, 105, 'Emma Frost', 'Doneya Petalfall', NULL, 5),
	(1, 106, 'Gambit', 'Makea the All-Knowing', NULL, 5),
	(1, 107, 'Hawkeye', 'Arcillo the Noble Hearted', 'Hawkeye', 1),
	(1, 108, 'Hulk', 'Cawr', 'Hulk', 1),
	(1, 109, 'Iron Man', 'Lollycooler', 'Iron Man', 1),
	(1, 110, 'Nick Fury', 'Mal Gravemore', 'Nick Fury', 2),
	(1, 111, 'Rogue', 'Ceridwen', NULL, 5),
	(1, 112, 'Spider-Man', 'Ryuhi', NULL, 3),
	(1, 113, 'Storm', 'Ordmantil the Shadow', NULL, 5),
	(1, 114, 'Thor', '"Hog" Dryll', 'Thor', 1),
	(1, 115, 'Wolverine', 'Kamina the Curious', NULL, 5);


INSERT INTO scheme (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'The Legacy Virus', 'Bleed ''em White', 'Radioactive Palladium Poisoning'),
	(1, 102, 'Midtown Bank Robbery', 'Pillage The Countryside', 'Destroy the Cities of Earth!'),
	(1, 103, 'Negative Zone Prison Breakout', 'Overwhelming Hordes', 'Asgard Under Siege'),
	(1, 104, 'Portals to the Dark Dimension', 'The Growing Darkness', 'Invade Asgard'),
	(1, 105, 'Replace Earth''s Leaders with Killbots', 'The Dead Shall Rise!', 'Replace Earth''s Leaders with HYDRA'),
	(1, 106, 'Secret Invasion of the Skrull Shapeshifters', 'The Lure of Voodoo', NULL),
	(1, 107, 'Super Hero Civil War', 'No More Heroes', 'Super Hero Civil War'),
	(1, 108, 'Unleash the Power of the Cosmic Cube', 'Apex of Power', 'Unleash the Power of the Cosmic Cube'),
	(1, 109, NULL, NULL, 'Enslave Minds with the Chitauri Scepter');

INSERT INTO mastermind (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Dr. Doom', 'Durissa the Dispossessed', NULL),
	(1, 102, NULL, 'Epic Durissa the Dispossessed', NULL),
	(1, 103, 'Loki', 'Terriskai, Terror of the Skies', 'Loki'),
	(1, 104, NULL, 'Epic Terriskai, Terror of the Skies', NULL),
	(1, 105, 'Magneto', 'Nax, Lord of Crimson Bog', NULL),
	(1, 106, NULL, 'Epic Nax, Lord of Crimson Bog', NULL),
	(1, 107, 'Red Skull', 'Kelila, Bender of Wills', 'Red Skull'),
	(1, 108, NULL, 'Epic Kelila, Bender of Wills', NULL),
	(1, 109, NULL, NULL, 'Iron Monger');

INSERT INTO villain (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Brotherhood', 'Sarco', NULL),
	(1, 102, 'Enemies of Asgard', 'Nortagem', 'Enemies of Asgard'),
	(1, 103, 'HYDRA', 'Ernak the Lethal', NULL),
	(1, 104, 'Masters of Evil', 'Void Rider', 'HYDRA'),
	(1, 105, 'Radiation', 'Force of Gehenna', NULL),
	(1, 106, 'Skrulls', 'Brigitte Blackbird', NULL),
	(1, 107, 'Spider-Foes', 'Teuthos', NULL),
	(1, 108, NULL, NULL, 'Chitauri'),
	(1, 109, NULL, NULL, 'Gamma Hunters'),
	(1, 110, NULL, NULL, 'Iron Foes');

INSERT INTO henchman (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Doombot Legion', 'Goblin Horde', 'Hammer Drone Army'),
	(1, 102, 'Hand Ninjas', 'Leprechauns', 'HYDRA Pilots'),
	(1, 103, 'Savage Land Mutates', 'Books of the Dead', 'HYDRA Spies'),
	(1, 104, 'Sentinel', 'Phyllo Assassins', 'Ten Rings Fanatics');
	
INSERT INTO support (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'S.H.I.E.L.D. Officer', 'Mayor Shufflebottom', 'S.H.I.E.L.D. Officer');
	
INSERT INTO starter (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'S.H.I.E.L.D.', 'Fixers', 'S.H.I.E.L.D.');

INSERT INTO board (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Heroic.', 'Heroic', 'Heroic');