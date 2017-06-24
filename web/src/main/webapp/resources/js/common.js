//passwords equals
function validatePassword() {

	if ($("#password1").val() != $("#password2").val()){
		$("#password2")[0].setCustomValidity("Пароли не совпадают");
	}else{
		$("#password2")[0].setCustomValidity('');
	}
}

$(document).ready(function() {
	
	//set select option
	var value = $("#list").text();
	$("#values option[value='" + value + "']").attr("selected","selected");
	
	//passwords equals
	$("#password1").change(validatePassword);
	$("#password2").change(validatePassword);
	
	
	//owl carusel
	//doc: http://owlgraphic.com/owlcarousel/
	var owl = $(".carousel");
	owl.owlCarousel({
		items : 3
	});
	owl.on("mousewheel", ".owl-wrapper", function(e) {
		if (e.deltaY > 0) {
			owl.trigger("owl.prev");
		} else {
			owl.trigger("owl.next");
		}
		e.preventDefault();
	});
	$(".next_button").click(function() {
		owl.trigger("owl.next");
	});
	$(".prev_button").click(function() {
		owl.trigger("owl.prev");
	});

});