function verify() {
	const json = formToJson();
	if (json != null) {
		const play = rules.games.lmdbg.rules.model.Play.Companion.playFromString(json);
		const errors = rules.games.lmdbg.rules.verifier.verify(play);
		if(errors.size === 0) {
			alert("Valid play");
		} else {
			const iter = errors.iterator();
			var message = "";
			while(iter.hasNext()) {
				const error = iter.next();
				message += error.getMessage() + "\n";
				const cards = error.getCardSets().iterator();
				while(cards.hasNext()) {
					const set = cards.next();
					message += "\t" + prettyCardSet(set) + "\n";
				}
			}
			alert (message);
		}
	}
}

function formToJson() {
	const play = {};
	const scheme = jQuery('input:radio[name=scheme]:checked').val();
	if (scheme != undefined) {
		play["scheme"] = parseInt(scheme);
	} else {
		alert("Missing scheme");
		return null;
	}
	
	const mastermind = jQuery('input:radio[name=mastermind]:checked').val();
	if (mastermind != undefined) {
		play["mastermind"] = parseInt(mastermind);
	} else {
		alert("Missing mastermind");
		return null;
	}
	
	const players = jQuery('select[name=players]').val()
	if (players != undefined) {
		play["players"] = players;
	} else {
		alert("Missing player count.");
		return null;
	}
	
	const supports = []
	jQuery('input:checkbox[name=supports]:checked').each(function() {supports.push(parseInt(this.value))})
	play["supports"] = supports;
	
	const starters = {}
	jQuery('input:text[name=starters]').each(function() {
		const starterId = this.id.slice("starters_".length)
		starters[parseInt(starterId)] = parseInt(this.value)
	});
	play["starters"] = starters;
	
	const heroes = []
	jQuery('input:checkbox[name=heroes]:checked').each(function() { heroes.push(parseInt(this.value))})
	play["heroes"] = heroes;
	
	const villains = []
	jQuery('input:checkbox[name=villains]:checked').each(function() {villains.push(parseInt(this.value))})
	play["villains"] = villains;
	
	const henchmen = []
	jQuery('input:checkbox[name=henchmen]:checked').each(function() {henchmen.push(parseInt(this.value))})
	play["henchmen"] = henchmen;
	
	const outcome = jQuery('select[name=outcome]').val()
	if (outcome != undefined) {
		play["outcome"] = outcome;
	} else {
		alert("Missing game outcome.");
		return null;
	}
	
	return JSON.stringify(play);
}

function onPlayersChange(value) {
	var numStaters;
	if (value === "SOLO" || value === "ADVANCED_SOLO") {
		numStaters = 1;
	} else if (value === "TWO") {
		numStaters = 2;
	} else if (value === "THREE") {
		numStaters = 3;
	} else if (value === "FOUR") {
		numStaters = 4;
	} else if (value === "FIVE") {
		numStaters = 5;
	}
	
	jQuery('input:text[id=starters_101]').val(numStaters);
}

jQuery(document).ready(function() {
	jQuery('select[name=players]').on('change', function() {
		onPlayersChange(this.value);
	});
	
	onPlayersChange(jQuery('select[name=players]').val());
});
