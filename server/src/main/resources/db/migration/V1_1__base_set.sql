INSERT INTO release (id, marvel_name, dxp_name, mcu_name) VALUES (1, 'Base Set', 'Base Set', 'Phase 1');

INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Black Widow', 'Steelvara the Light', 'Black Widow'),
	(1, 102, 'Captain America', 'Lord Cedric of the Citadel', 'Captain America'),
	(1, 103, 'Cyclops', 'Disaray the Sufferer', NULL),
	(1, 104, 'Deadpool', 'Nunchi', NULL),
	(1, 105, 'Emma Frost', 'Doneya Petalfall', NULL),
	(1, 106, 'Gambit', 'Makea the All-Knowing', NULL),
	(1, 107, 'Hawkeye', 'Arcillo the Noble Hearted', 'Hawkeye'),
	(1, 108, 'Hulk', 'Cawr', 'Hulk'),
	(1, 109, 'Iron Man', 'Lollycooler', 'Iron Man'),
	(1, 110, 'Nick Fury', 'Mal Gravemore', 'Nick Fury'),
	(1, 111, 'Rogue', 'Ceridwen', NULL),
	(1, 112, 'Spider-Man', 'Ryuhi', NULL),
	(1, 113, 'Storm', 'Ordmantil the Shadow', NULL),
	(1, 114, 'Thor', '"Hog" Dryll', 'Thor'),
	(1, 115, 'Wolverine', 'Kamina the Curious', NULL);


INSERT INTO scheme (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'The Legacy Virus', 'Bleed ''em White', 'Radioactive Palladium Poisoning'),
	(1, 102, 'Midtown Bank Robbery', 'Pillage The Countryside', 'Destroy the Cities of Earth!'),
	(1, 103, 'Negative Zone Prison Breakout', 'Overwhelming Hordes', 'Asgard Under Siege'),
	(1, 104, 'Portals to the Dark Dimension', 'The Growing Darkness', 'Invade Asgard'),
	(1, 105, 'Replace Earth''s Leaders with Killbots', 'The Dead Shall Rise!', 'Replace Earth''s Leaders with HYDRA'),
	(1, 106, 'Secret Invasion of the Skrull Shapeshifters', 'The Lure of Voodoo', 'Enslave Minds with the Chitauri Scepter'),
	(1, 107, 'Super Hero Civil War', 'No More Heroes', 'Super Hero Civil War'),
	(1, 108, 'Unleash the Power of the Cosmic Cube', 'Apex of Power', 'Unleash the Power of the Cosmic Cube');

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
