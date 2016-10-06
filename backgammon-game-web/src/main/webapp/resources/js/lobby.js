
$(loadDomEvents);

function loadDomEvents(){
	$("#openNewRoomBtn").click(showOpenNewRoomForm);
	$("#openRoomRow button:last").click(showAvialableGameRooms);
}


function showOpenNewRoomForm(){
	$("#avialableRoomsRow").addClass("hidden");
	$("#openRoomRow").removeClass("hidden");
}

function showAvialableGameRooms(){
	$("#openRoomRow").addClass("hidden");
	$("#avialableRoomsRow").removeClass("hidden");
}