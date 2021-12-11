var cardSets = {}

cardSets["villains"] = /*[[${villainsMap}]]*/ {};
cardSets["henchmen"] = /*[[${henchmenMap}]]*/ {};

function prettyCardSet(cardSet) {
	const type = cardSet.setType.name;
	const id =  cardSet.setId;
	
	var lookupTable = null;
	
	switch(type) {
		case "HENCHMAN":
			lookupTable = cardSets["henchmen"]
			break;
		case "VILLAIN":
			lookupTable = cardSets["villains"]
			break;
	}
	
	if (lookupTable === null || !(id in lookupTable)){
		return type + " " + id;
	} else {
		var name = lookupTable[id].marvelName;
		if (!name || name.length === 0 ) {
			name = lookupTable[id].dxpName;
		}
		if (!name || name.length === 0 ) {
			name = lookupTable[id].mcuName;
		}
		if (!name || name.length === 0 ) {
			name = type + " " + id;
		}
		
		return name
	}
}