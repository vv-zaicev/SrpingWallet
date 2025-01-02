window.onload = function() {
	visiblePopup(false)
};

function visiblePopup(isVisible){
	var popup = document.getElementById("popup");
	
	if (isVisible){
		popup.style.display = "flex";
	} else {
		popup.style.display = "none";
	}
}