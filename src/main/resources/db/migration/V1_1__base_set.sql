INSERT INTO release (id, marvel_name, dxp_name, mcu_name) VALUES (1, 'Base Set', 'Base Set', 'Phase 1');

INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 101, 'Black Widow', 'Steelvara the Light', 'Black Widow');
INSERT INTO hero (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 102, 'Captain America', 'Lord Cedric of the Citadel', 'Captain America');
--'"Hog" Dryll'

INSERT INTO scheme (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 101, 'The Legacy Virus', 'Bleed ''em White', 'Radioactive Palladium Poisoning');

INSERT INTO mastermind (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 101, 'Dr. Doom', 'Durissa the Dispossessed', null);
INSERT INTO mastermind (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 102, null, 'Epic Durissa the Dispossessed', null);
INSERT INTO mastermind (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 199, null, null, 'Iron Monger');

INSERT INTO villain (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 199, null, null, 'Iron Foes');

INSERT INTO henchman (release_id, id, marvel_name, dxp_name, mcu_name) VALUES (1, 199, 'Doombot Legion', 'Lord Cedric of the Citadel', null);