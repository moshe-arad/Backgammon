$(document).ready(function(){
	$(loadDomEvents);
});

var selectedRow;

function loadDomEvents(){
	$("#openNewRoomBtn").click(showOpenNewRoomForm);
	$("#openRoomRow button:last").click(showAvialableGameRooms);
	$("tr").bind("mouseenter", setSuccess);
	$("tr").bind("mouseleave", removeSuccess);
	$("tr").bind("click",chooseRoom);
	$("#joinBtn button:last").bind("click", cancelSelection);
}


function showOpenNewRoomForm(){
	$("#avialableRoomsRow").addClass("hidden");
	$("#openRoomRow").removeClass("hidden");
}

function showAvialableGameRooms(){
	$("#openRoomRow").addClass("hidden");
	$("#avialableRoomsRow").removeClass("hidden");
}

function setSuccess(){
	$(this).addClass("success");
}

function removeSuccess(){
	$(this).removeClass("success");
}

function chooseRoom(){
	$("tr").unbind("mouseenter", setSuccess);
	$("tr").unbind("mouseleave", removeSuccess);
	
	selectedRow = $(this);
	$(this).addClass("success");
	$("tr").unbind("click",chooseRoom);
	$("#joinBtn").removeClass("hidden");
}

function cancelSelection(){
	selectedRow.removeClass("success");
	$("tr").bind("mouseenter", setSuccess);
	$("tr").bind("mouseleave", removeSuccess);
	$("tr").bind("click",chooseRoom);
	$("#joinBtn").addClass("hidden");
}