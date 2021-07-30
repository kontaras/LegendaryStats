/**
 * 
 */

function setNames (table, cls) {
	let cells = table.find("td.name").children().filter("span." + cls)
	
	cells.css("display", "inline");
	cells.siblings().css("display", "none");
}