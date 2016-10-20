var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");
var csrfToken = $("meta[name='_csrf']").attr("content");

$(document).ready(function(){
	$(loadDomEvents);
});


function loadDomEvents(){
	checkUntilReady();
	$("#rollDicesBtn").click(rollDices);
}

var gameRoomId = $("#gameRoomId").html();
var remoteServer = $("#remoteServer").html();

var isReadyToPlay = false;

var headers = {};
headers[csrfHeader] = csrfToken;

var failAttemptsToInit = 0;

function checkIsReadyToPlay(){
	console.log("sending is ready message to " + remoteServer + "/init");
	$.ajax({
			url: remoteServer + "/init",
			success: checkIsReadyToPlayCallback,
			timeout: 1000,
			data: JSON.stringify({gameRoomId:gameRoomId}),
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			error: initFailure,
			headers: headers
		});
	return false;
}

function initFailure(xmlhttprequest, textstatus, message){
	if(textstatus == "timeout") {
		failAttemptsToInit++;
		console.log("Timeout CheckIsReady")
		if(failAttemptsToInit == 3) clearInterval(stopCheckIsReady);
	}
	else console.log("ABORTED - reason: " + textstatus + ", message: " + message);
} 

function checkIsReadyToPlayCallback(isReady){
	isReadyToPlay = Boolean(isReady);
	console.log("Game Room is "+ isReadyToPlay +" ready to play with");
	if(isReadyToPlay) {
		clearInterval(stopCheckIsReady);
		console.log("register to server");
		registerToBackgammonDispatcher();
	}
}

var stopCheckIsReady;

function checkUntilReady(){
	if(gameRoomId) stopCheckIsReady = setInterval(checkIsReadyToPlay, 1000);
}

/*****************************************/

function registerToBackgammonDispatcher(){
	$.ajax({
		url:"http://localhost:8080/backgammon-game-web/backgammon_dispatcher/register",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify({}),
		type: "POST",
		success: msgFromServer,
		timeout: 60000,
		error: registerFailure,
		headers: headers
	});
	return false;
}

var failAttemptsToRegister = 0;

function msgFromServer(obj){
	failAttemptsToRegister = 0;
	handleMoveFromServer(obj);
	registerToBackgammonDispatcher();
}

function registerFailure(xmlhttprequest, textstatus, message){
	console.log("ABORTED - reason: " + textstatus + ", message: " + message);
	failAttemptsToRegister++;
	if(failAttemptsToRegister < 3) registerToBackgammonDispatcher();
}

function handleMoveFromServer(obj){
	var from = obj.from.index; 
	var to = obj.to.index;
	
	if(from == -2 && to == -2){
		$("#txtFromServer").removeClass("hidden");
		$("#txtFromServer").append("White player it's your turn to play.");
		$("#rollDicesBtn").removeClass("hidden");
	}
}

/************************************************/

function rollDices(){
	$("#txtFromServer").addClass("hidden");
	$("#rollDicesBtn").addClass("hidden");
	
	$.ajax({
		url:"http://localhost:8080/backgammon-game-web/backgammon/roll_dices",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify({gameRoomId:gameRoomId}),
		type: "POST",
		success: dicePairFromServer,
		timeout: 60000,
		error: registerFailure,
		headers: headers
	});
	return false;
}

function dicePairFromServer(obj){
	failAttemptsToRegister = 0;
	$("#txtFromServer").removeClass("hidden");
	$("#txtFromServer").append("White player you rolled: " + obj.first.value + ":" + obj.second.value);
	registerToBackgammonDispatcher();
}





