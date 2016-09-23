$(document).ready(function(){
	
	$("#logoutLink").click(function(e){
		e.preventDefault();
		$("#logoutForm").submit();
	});
});