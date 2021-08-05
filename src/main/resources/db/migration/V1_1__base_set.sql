INSERT INTO release (id, marvel_name, dxp_name, mcu_name) VALUES (1, 'Base Set', 'Base Set', 'Phase 1');

INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Black Widow', 'Steelvara the Light', 'Black Widow'),
	(1, 102, 'Captain America', 'Lord Cedric of the Citadel', 'Captain America'),
	(1, 103, 'Cyclops', 'Disaray the Sufferer', ''),
	(1, 104, 'Deadpool', 'Nunchi', ''),
	(1, 105, 'Emma Frost', 'Doneya Petalfall', ''),
	(1, 106, 'Gambit', 'Makea the All-Knowing', ''),
	(1, 107, 'Hawkeye', 'Arcillo the Noble Hearted', 'Hawkeye'),
	(1, 108, 'Hulk', 'Cawr', 'Hulk'),
	(1, 109, 'Iron Man', 'Lollycooler', 'Iron Man'),
	(1, 110, 'Nick Fury', 'Mal Gravemore', 'Nick Fury'),
	(1, 111, 'Rogue', 'Ceridwen', ''),
	(1, 112, 'Spider-Man', 'Ryuhi', ''),
	(1, 113, 'Storm', 'Ordmantil the Shadow', ''),
	(1, 114, 'Thor', '"Hog" Dryll', 'Thor'),
	(1, 115, 'Wolverine', 'Kamina the Curious', '');


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
	(1, 101, 'Dr. Doom', 'Durissa the Dispossessed', null),
	(1, 102, null, 'Epic Durissa the Dispossessed', null),
	(1, 103, 'Loki', 'Terriskai, Terror of the Skies', 'Loki'),
	(1, 104, null, 'Epic Terriskai, Terror of the Skies', null),
	(1, 105, 'Magneto', 'Nax, Lord of Crimson Bog', null),
	(1, 106, null, 'Epic Nax, Lord of Crimson Bog', null),
	(1, 107, 'Red Skull', 'Kelila, Bender of Wills', 'Red Skull'),
	(1, 108, null, 'Epic Kelila, Bender of Wills', null),
	(1, 109, null, null, 'Iron Monger');

INSERT INTO villain (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Brotherhood', 'Sarco', ''),
	(1, 102, 'Enemies of Asgard', 'Nortagem', 'Enemies of Asgard'),
	(1, 103, 'HYDRA', 'Ernak the Lethal', ''),
	(1, 104, 'Masters of Evil', 'Void Rider', 'HYDRA'),
	(1, 105, 'Radiation', 'Force of Gehenna', ''),
	(1, 106, 'Skrulls', 'Brigitte Blackbird', ''),
	(1, 107, 'Spider-Foes', 'Teuthos', ''),
	(1, 108, null, null, 'Chitauri'),
	(1, 109, null, null, 'Gamma Hunters'),
	(1, 110, null, null, 'Iron Foes');

INSERT INTO henchman (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(1, 101, 'Doombot Legion', 'Goblin Horde', 'Hammer Drone Army'),
	(1, 102, 'Hand Ninjas', 'Leprechauns', 'HYDRA Pilots'),
	(1, 103, 'Savage Land Mutates', 'Books of the Dead', 'HYDRA Spies'),
	(1, 104, 'Sentinel', 'Phyllo Assassins', 'Ten Rings Fanatics');