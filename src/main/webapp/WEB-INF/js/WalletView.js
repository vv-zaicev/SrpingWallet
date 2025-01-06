function loadTransactions(count) {
	var show = document.getElementById("show");
	var hide = document.getElementById("hide");
	var transactions = document.getElementById("transactions").children;
	


	if (transactions.length <= 5) {
		show.style.display = "none";
		hide.style.display = "none";
	} else if (count == 0) {
		for(let i = 0; i < transactions.length; i++){
			transactions[i].style.display = "flex";
		}
		hide.style.display = "inline-block";
		show.style.display = "none";
	} else {
		for(let i = count; i < transactions.length; i++){
			transactions[i].style.display = "none";
		}
		show.style.display = "inline-block";
		hide.style.display = "none";
	}

}


function changeTransactionType(select) {
	var o = select.options[select.selectedIndex];
	var type = o.value.toLowerCase() + "type";
	if (o.value == "INCOME")
		select.style.color = "green";
	else if (o.value == "EXPENSES")
		select.style.color = "red";
	else
		select.style.color = "#FFF";

	//var categoryList = document.getElementById("selectTransactionCategory");
	//var categories = categoryList.getElementsByTagName("option");

	//var changedType = categoryList.options[categoryList.selectedIndex].id.toLowerCase() != type;

	if (o.id == "nullType") {
		document.getElementById("placeholderType").selected = true;
	}

/*	for (var category of categories) {
		if (category.id == "placeholderCat") {
			if (changedType)
				category.selected = true;

			continue;
		}
		if (category.id.toLowerCase() == type || o.id == "placeholderType" || o.id == "nullType") {
			category.style.display = "block";

		} else if (category.id != "nullCat" && category.id != "placeholderCat") {
			category.style.display = "none";
		}
	}*/
}

function changeTransactionCat(select) {
	var o = select.options[select.selectedIndex];
	if (o.id == "nullCat") {
		document.getElementById("placeholderCat").selected = true;
	}
}


window.onload = function() {
	loadTransactions(5);

	var selectType = document.getElementById("selectTransactionType");
	changeTransactionType(selectType);
	var selectCat = document.getElementById("selectTransactionCategory");
	changeTransactionCat(selectCat)

	var coll = document.getElementById("collapsible");
	console.log(coll);
	coll.addEventListener("click", function() {
		this.classList.toggle("down");
		var content = document.getElementById("filters");
		if (content.style.maxHeight) {
			content.style.maxHeight = null;
		} else {
			content.style.maxHeight = content.scrollHeight + "px";
		}
	});


};

