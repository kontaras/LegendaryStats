function verify() {
	const play = formToPlay();
	const errors = rules.games.lmdbg.rules.verifier.verify(play);
	const messages = rules.games.lmdbg.rules.utils.errorsToString(errors).toArray()
	if(messages.length > 0) {
		alert (messages.join("\n"));
		return false;
	}
	
	return true;
}

function formToPlay() {
	const play = rules.games.lmdbg.rules.model.Play.Play_init_$Create$();
	const scheme = jQuery('input:radio[name=scheme]:checked').val();
	if (scheme != undefined) {
		play.scheme = parseInt(scheme);
	}
	
	const mastermind = jQuery('input:radio[name=mastermind]:checked').val();
	if (mastermind != undefined) {
		play.mastermind = parseInt(mastermind);
	}
	
	const board = jQuery('input:radio[name=board]:checked').val();
	if (board != undefined) {
		play.board = parseInt(board);
	}
	
	const players = jQuery('select[name=players]').val()
	if (players != undefined) {
		play.players = players
		// play.players = rules.games.lmdbg.rules.model.PlayerCount.valueOf(players);
	}
	
	const supports = []
	jQuery('input:checkbox[name=supports]:checked').each(function() {supports.push(parseInt(this.value))})
	play.supports = supports;
	//play.supports = rules.games.lmdbg.rules.model.Play.Companion.createSetFromJsArray(supports);
	
	const starters = {}
	jQuery('input:text[name^=starters]').each(function() {
		const starterId = this.id.slice("starters_".length)
		starters[parseInt(starterId)] = parseInt(this.value)
	});
	play.starters = starters;
	
	const heroes = []
	jQuery('input:checkbox[name=heroes]:checked').each(function() { heroes.push(parseInt(this.value))})
	play.heroes = heroes;
	// play.heroes = rules.games.lmdbg.rules.model.Play.Companion.createSetFromJsArray(heroes);
	
	const villains = []
	jQuery('input:checkbox[name=villains]:checked').each(function() {villains.push(parseInt(this.value))})
	play.villains = villains;
	// play.villains = rules.games.lmdbg.rules.model.Play.Companion.createSetFromJsArray(villains);
	
	const henchmen = []
	jQuery('input:checkbox[name=henchmen]:checked').each(function() {henchmen.push(parseInt(this.value))})
	play.henchmen = henchmen;
	// play.henchmen = rules.games.lmdbg.rules.model.Play.Companion.createSetFromJsArray(henchmen);
	
	const outcome = jQuery('select[name=outcome]').val()
	if (outcome != undefined) {
		play.outcome = outcome;
	}
	
	// Cannot encode starters. Try again in Kotlin 2.0.
	const json = JSON.stringify(play)
	return rules.games.lmdbg.rules.model.Play.Companion.playFromString(json);
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
