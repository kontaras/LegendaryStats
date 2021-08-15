/**
 * 
 */
window.tableIds = []

function setNames (cls) {
	for (const tableId of window.tableIds) {
		const table = jQuery("table#" + tableId)
		const cells = table.find("td.name").children().filter("span." + cls)
		
		cells.css("display", "inline");
		cells.siblings().css("display", "none");
	}
}