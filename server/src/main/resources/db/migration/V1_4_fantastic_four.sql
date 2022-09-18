INSERT INTO release (id, marvel_name, dxp_name, mcu_name) VALUES 
	(3, 'Core Set', 'The Vigilant', NULL);

INSERT INTO team (id, marvel_name, dxp_name, mcu_name) VALUES
	(8, 'Fantastic Four', ' The Vigilant', NULL);
	
INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name, team_id) VALUES
	(3, 301, 'Human Torch', 'Jasper', NULL, 8),
	(3, 302, 'Invisible Woman', 'Yumi', NULL, 8),
	(3, 303, 'Mr. Fantastic', 'Felix', NULL, 8),
	(3, 304, 'Silver Surfer', 'Solas, The Protector',  NULL, 4),
	(3, 305, 'Thing', 'Yooka Leilei', NULL, 8);

INSERT INTO scheme (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(3, 301, 'Bathe Earth in Cosmic Rays', 'War of Attrition', NULL),
	(3, 302, 'Flood the Planet with Melted Glaciers', 'All Will Despair', NULL),
	(3, 303, 'Invincible Force Field', 'Flood of Fear', NULL),
	(3, 304, 'Pull Reality Into the Negative Zone', 'Reality Shift', NULL);
	
INSERT INTO mastermind (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(3, 301, 'Galactus', 'Eltamin', NULL),
	(3, 302, NULL, 'Epic Eltamin', NULL),
	(3, 303, 'Mole Man', 'Zohar', NULL),
	(3, 304, NULL, 'Epic Zohar', NULL);
	
INSERT INTO villain (release_id, id, marvel_name, dxp_name, mcu_name) VALUES
	(3, 301, 'Heralds of Galactus', 'Alistair Wolfe', NULL),
	(3, 302, 'Subterranea', 'Rooth', NULL);
