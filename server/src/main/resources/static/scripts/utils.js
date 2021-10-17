function setNames (cls) {
	const cells = jQuery("span." + cls)
	
	cells.css("display", "inline");
	cells.siblings().css("display", "none");
}