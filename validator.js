/**
 * 
 */

function validate(setup) {
	const commander = setup["commander"];
	const allies = setup["allies"];
	const foes = setup["foes"];
	const henchmen = setup["henchmen"];
	const plot = setup["plot"];
	const players = setup["players"];

	const rules = playerCountRules(players);

	modScheme(plot, rules)

	return validateCommander(commander, foes, henchmen);
}

function playerCountRules(players) {
	let foes, henchmen, heroes;
	let playerCount = players;
	switch (players) {
	case "solo":
	case "advancedSolo":
		foes = 1;
		henchmen = 1;
		heroes = 3;
		playerCount = "1";
		break;
	case"2":
		foes = 2;
		henchmen = 1;
		heroes = 5;
		break;
	case"3":
		foes = 3;
		henchmen = 1;
		heroes = 5;
		break;
	case"4":
		foes = 3;
		henchmen = 2;
		heroes = 5;
		break;
	case"5":
		foes = 4;
		henchmen = 2;
		heroes = 6;
		break;
	default:
		throw "Invalid player count";
	}
	return {
		"foes" : foes,
		"henchmen" : henchmen,
		"heroes" : heroes,
		"players" : playerCount
	};
}

function validateCommander(commander, foes, henchmen) {
	switch (commander) {
	case "Base_DrDoom":
		return validateCommanderFoes(true, "Base_DoombotLegion", henchmen)
		break;
	case "Base_Loki":
		return validateCommanderFoes(false, "Base_EnemiesOfAsgard", foes)
		break;
	case "Base_Magneto":
		return validateCommanderFoes(false, "Base_Brotherhood", foes)
		break;
	case "Base_RedSkull":
		return validateCommanderFoes(false, "Base_Hydra", foes)
		break;
	default:
		throw "Unknown commander";
	}
}

function validateCommanderFoes(hench, foe, foes) {
	if (!foes.includes(foe)) {
		if (hench) {
			throw "Mandatory foe " + foe;
		} else {
			throw "Mandatory hench " + foe;
		}
	}
}

function modScheme(scheme, rules) {
	switch (scheme) {
	case "Base_NegativeZonePrisonBreakout":
		rules["henchmen"] += 1;
		if (rules["players"] == "1") {
			throw "Not enough players for scheme";
		}
		break;
	case "Base_SecretInvasionOfTheSkrullShapeshifters":
		//TODO: Hero in Villain deck
		break;
	case "Base_SuperHeroCivilWar":
		if (rules["players"] == "1") {
			throw "Not enough players for scheme";
		} else if (rules["players"] == "2") {
			rules["heroes"] = 4;
		}
		break;
	// Schemes with not rules tweaks
	case "Base_TheLegacyVirus":
	case "Base_MidtownBankRobbery":
	case "Base_ReplaceEarthsLeadersWithKillbots":
	case "Base_PortalsToDarkDimension":
	case "Base_UnleashThePowerOfTheCosmicCube":
		break;
	default:
		throw "Unknown scheme";
	}
}

alert(validate({
	"commander" : "Base_Loki",
	"foes" : [ "Base_EnemiesOfAsgard" ],
	"henchmen" : [],
	"plot" : "Base_NegativeZonePrisonBreakout",
	"players" : "2"
}));
