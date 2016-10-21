var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");
var csrfToken = $("meta[name='_csrf']").attr("content");

var gameRoomId = $("#gameRoomId").html();
var remoteServer = $("#remoteServer").html();

var isReadyToPlay = false;

var headers = {};
headers[csrfHeader] = csrfToken;

var failAttemptsToInit = 0;


$(document).ready(function(){
	$(loadDomEvents);
});

function loadDomEvents(){
	registerToBackgammonDispatcher();
	$("#rollDicesBtn").click(rollDices);
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
		error: failureFromServer,
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

function failureFromServer(xmlhttprequest, textstatus, message){
	console.log("ABORTED - reason: " + textstatus + ", message: " + message);
	failAttemptsToRegister++;
	if(failAttemptsToRegister < 3) registerToBackgammonDispatcher();
}

function handleMoveFromServer(obj){
	var token = obj.messageToken;
	
	switch(Number(token)){
		case 1:{
			handleBasicDetails(obj);
			break;
		}
		case 2:{
			handleDiceRolling(obj);
			break;
		}
		case 3:{
			handleInvalidMove(obj);
			break;
		}
		case 4:
			handleValidMove(obj);
			break;
		default:
			console.log("Failed to match token message from server.");
	}
}

function handleBasicDetails(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	
	if(color == "white" && Boolean(isYourTurn)){
		$("#txtFromServer").html("White player it's your turn to play. roll the dices.");
		$("#rollDicesBtn").removeClass("hidden");
	}
	else if(color == "white" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("This is Black's player turn to play.");
	}
	else if(color == "black" && Boolean(isYourTurn)){
		$("#txtFromServer").html("Black player it's your turn to play. roll the dices.");
		$("#rollDicesBtn").removeClass("hidden");
	}
	else if(color == "black" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("This is White's player turn to play.");
	}
	$("#txtFromServer").removeClass("hidden");	
}

function handleDiceRolling(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	var firstDice = obj.firstDice;
	var secondDice = obj.secondDice;
	
	if(color == "white" && Boolean(isYourTurn)){
		$("#txtFromServer").html("White player you rolled the dices and came up with, " + firstDice + ":" + secondDice);
		$("#rollDicesBtn").addClass("hidden");
	}
	else if(color == "white" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("White, Black player rolled the dices and came up with, " + firstDice + ":" + secondDice);
	}
	else if(color == "black" && Boolean(isYourTurn)){
		$("#txtFromServer").html("Black player you rolled the dices and came up with, " + firstDice + ":" + secondDice);
		$("#rollDicesBtn").addClass("hidden");
	}
	else if(color == "black" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("Black, White player rolled the dices and came up with, " + firstDice + ":" + secondDice);
	}
	
	$("table.board td").bind("click", selectMove);
}


function handleInvalidMove(){
	$("#txtFromServer").removeClass("hidden");
	$("#txtFromServer").html("You made invalid move, try again.");
}

function handleValidMove(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	var from = obj.from.index
	var to = obj.to.index;
	var columnSizeOnFrom = obj.columnSizeOnFrom;
	var columnSizeOnTo = obj.columnSizeOnTo;
	var isHasMoreMoves = obj.isHasMoreMoves;
	
	if(color == "white" && Boolean(isYourTurn)){
		$("#txtFromServer").html("White player you made a move from " + from + " to " + to); 
	}
	else if(color == "white" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("White, Black player made a move from " + from + " to " + to);
	}
	else if(color == "black" && Boolean(isYourTurn)){
		$("#txtFromServer").html("Black player you made a move from " + from + " to " + to);
	}
	else if(color == "black" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("Black, White player made a move from " + from + " to " + to);
	}
	$("#txtFromServer").removeClass("hidden");
	
	removePawnFrom(color, from , columnSizeOnFrom);
	addPawnTo(color, to , columnSizeOnTo);
	
	if(!Boolean(isHasMoreMoves)) $("table.board td").unbind("click", selectMove);
}

function removePawnFrom(color, from , columnSizeOnFrom){
	var arr = $("table.board td[class~='backgammon-col-" + from + "']");
	if(columnSizeOnFrom < 5){
		if(arr[0].hasClass("bottom-col-backgammon")){
			var item = arr[(arr.length - 1) + (5-columnSizeOnFrom)];
		}
		else{
			var item = arr[5-columnSizeOnFrom];
		}
		$(item).addClass("empty-cell");
		$(item).html("e");
	}
}

function addPawnTo(color, to , columnSizeOnTo){
	var arr = $("table.board td[class~='backgammon-col-" + to + "']");
	if(columnSizeOnTo <=5){
		if(arr[0].hasClass("bottom-col-backgammon")){
			var item = arr[(arr.length - 1) + (5-columnSizeOnFrom)];
		}
		else{
			var item = arr[5-columnSizeOnFrom];
		}
		$(item).removeClass("empty-cell");
		if(color == "white") $(item).html("W");
		else if(color == "black") $(item).html("B");
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
		success: successDices,
		timeout: 60000,
		error: failureDices,
		headers: headers
	});
	return false;
}

function successDices(text){
	console.log("Roll dices requested was accepted by server.")
}

function failureDices(){
	$("#txtFromServer").html("Failed to roll dices, try again.")
	$("#txtFromServer").removeClass("hidden");
	$("#rollDicesBtn").removeClass("hidden");
}

/*********************************************/
var from = undefined;
var to = undefined;

function selectMove(e){
	var col = $(e.target).attr("class");
	
	var beginIndex = col.search("backgammon-col-");
	col = col.substring(beginIndex, col.length);
	
	if(col.length == 17) col = col.substring(15,17);
	else col = col.substring(15,16);
	
	console.log("you selected column " + col);
	if(from === undefined) {
		$("#txtFromServer").html("You selected to move from column " + col);
		from = col;
	}
	else if(to === undefined){
		to = col;
		$("#txtFromServer").html("You selected to move from column " + col + 
				"to column " + to);
		sendMoveToServer(from,to);
	}
}

function sendMoveToServer(from,to){
	$("#txtFromServer").addClass("hidden");
	
//	var basicUser = {userName:"", password:"", enabled:""};
//	var move = {move:{from:{index:from}, to:{index:to}}}; 
//	var userMove = {user:basicUser, move:move};
//	var param = userMove;
	
	var move = {move:{from:{index:from}, to:{index:to}}}; 
	var param = {move:move, gameRoomId:gameRoomId};

	
	$.ajax({
		url:"http://localhost:8080/backgammon-game-web/backgammon/move",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify(param),
		type: "POST",
		success: successFromServer,
		timeout: 60000,
		error: failureFromServer,
		headers: headers
	});
	
	from = undefined;
	to = undefined;
	return false;
}

function successFromServer(){
	failAttemptsToRegister = 0;
}





