function changeTransactionType(select) {
	var o = select.options[select.selectedIndex];
	if (o.value == "INCOME")
		select.style.color = "green";
	else if(o.value == "EXPENSES")
		select.style.color = "red";
	else {
		select.style.color = "#FFF"
	}
}

window.onload = function() {
	var s = document.getElementById("selectTransactionType");
	changeTransactionType(s);
};