var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");
var csrfToken = $("meta[name='_csrf']").attr("content");

var gameRoomId = $("#gameRoomId").html();
var remoteServer = $("#remoteServer").html();

var headers = {};
headers[csrfHeader] = csrfToken;

var failAttemptsToInit = 0;

var whiteEatenNum = 0;
var blackEatenNum = 0;

$(document).ready(function(){
	$(loadDomEvents);
});

function loadDomEvents(){
	registerToBackgammonDispatcher();
	$("#rollDicesBtn").click(rollDices);
}

/**************** roll dices  *************************/

function rollDices(){
//	$("#txtFromServer").addClass("hidden");
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


function failureDices(jqXHR, textStatus, errorThrown){
	$("#txtFromServer").html("Failed to roll dices, try again. ABORTED - textStatus=" + textstatus + ", errorThrown= " + errorThrown);
	$("#txtFromServer").removeClass("hidden");
	$("#rollDicesBtn").removeClass("hidden");
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
		timeout: 63000,
		error: failureFromServer,
		headers: headers
	});
	return false;
}

function msgFromServer(obj){
	handleMoveFromServer(obj);
	if(!Boolean(isHaveWinner)) registerToBackgammonDispatcher();
	else console.log("Register requests has stopped.")
}

function failureFromServer(jqXHR, textStatus, errorThrown){
	$("#txtFromServer").html(" ABORTED - textStatus=" + textStatus + ", errorThrown= " + errorThrown);
	console.log(" ABORTED - textStatus=" + textStatus + ", errorThrown= " + errorThrown);
	registerToBackgammonDispatcher();
}

function handleMoveFromServer(obj){
	var token = obj.messageToken;
	
	console.log("handle message, token = " +token + " Number(token) = " + Number(token));
	
	switch(Number(token)){
		case 1:{
			handleBasicDetails(obj);
			confirmMessageArrival(obj);
			break;
		}
		case 2:{
			handleDiceRolling(obj);
			confirmMessageArrival(obj);
			break;
		}
		case 3:{
			handleInvalidMove(obj);
			confirmMessageArrival(obj);
			break;
		}
		case 4:{
			handleValidMove(obj);
			confirmMessageArrival(obj);
			break;
		}
		case 5:{
			console.log("Ignoring this empty message.");
			break;
		}
		case 6:{
			confirmMessageArrival(obj);
			$("#rollDicesBtn").removeClass("hidden");
			break;
		}
		case 10:{
			handleWinner(obj);
			break;
		}
		default:{
			console.log("Failed to match token message from server.");
			break;
		}
	}
}

function confirmMessageArrival(obj){
	
	var confirm = {color:obj.color, isYourTurn:obj.isYourTurn, token:obj.messageToken, uuid:obj.uuid}; 
	var param = {confirm:confirm, gameRoomId:gameRoomId};

	if(obj.messageToken != 5){
		
		$.ajax({
			url:"http://localhost:8080/backgammon-game-web/backgammon_dispatcher/confirm",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify(param),
			type: "POST",
			success: successPassMove,
			timeout: 60000,
			headers: headers
		});
		
		return false;
	}
}

function handleBasicDetails(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	
	if(color == "white" && Boolean(isYourTurn)){
		$("#txtFromServer").html("White player it's your turn to play. roll the dices.");
//		$("#rollDicesBtn").removeClass("hidden");
	}
	else if(color == "white" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("This is Black's player turn to play.");
	}
	else if(color == "black" && Boolean(isYourTurn)){
		$("#txtFromServer").html("Black player it's your turn to play. roll the dices.");
//		$("#rollDicesBtn").removeClass("hidden");
	}
	else if(color == "black" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("This is White's player turn to play.");
	}
	$("#txtFromServer").removeClass("hidden");	
}

var firstDice;
var secondDice;

function handleDiceRolling(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	var isCanPlay = obj.canPlay;
	
	console.log("color = " +color + " isYourTurn = " + isYourTurn);
	firstDice = obj.firstDice;
	secondDice = obj.secondDice;
	console.log("first dice = " +firstDice + " secondDice = " + secondDice);
	
	$("#txtFromServer").removeClass("hidden");	
	
	if(!Boolean(isCanPlay)){
		$("#txtFromServer").html("you rolled the dices and came up with, " + firstDice + ":" + secondDice + ". But the turn passed to other player because you can't make a valid play.");
		return;
	}
	
	if(color == "white" && Boolean(isYourTurn)){
		$("#txtFromServer").html("White player you rolled the dices and came up with, " + firstDice + ":" + secondDice);
//		$("#rollDicesBtn").addClass("hidden");
	}
	else if(color == "white" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("White, Black player rolled the dices and came up with, " + firstDice + ":" + secondDice);
	}
	else if(color == "black" && Boolean(isYourTurn)){
		$("#txtFromServer").html("Black player you rolled the dices and came up with, " + firstDice + ":" + secondDice);
//		$("#rollDicesBtn").addClass("hidden");
	}
	else if(color == "black" && !Boolean(isYourTurn)){
		$("#txtFromServer").html("Black, White player rolled the dices and came up with, " + firstDice + ":" + secondDice);
	}
	
	$("table.board td").bind("click", selectMove);
	$("#whiteEaten").bind("click", selectMove);
	$("#blackEaten").bind("click", selectMove);
	
	
	$("#whiteOut").bind("click", selectMove);
	$("#blackOut").bind("click", selectMove);
}

function handleInvalidMove(){
	from = undefined;
	to = undefined;
	$("#txtFromServer").removeClass("hidden");
	$("#txtFromServer").html("Invalid move, try again. Dice were " + firstDice + ":" + secondDice);
}

function handleValidMove(obj){
	var color = obj.color;
	var isYourTurn = obj.isYourTurn;
	var from = obj.from;
	var to = obj.to;
	var columnSizeOnFrom = obj.columnSizeOnFrom;
	var columnSizeOnTo = obj.columnSizeOnTo;
	var isHasMoreMoves = obj.hasMoreMoves;
	var isEaten = obj.eaten;
	
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
	
	if(color == "white" && isEaten == true && isYourTurn == true) {
		blackEatenNum++;
		$("#blackEaten").html("You have " + blackEatenNum + "eaten pawns");
	}
	else if(color == "white" && isEaten == true && isYourTurn == false) {
		whiteEatenNum++;
		$("#whiteEaten").html("You have " + whiteEatenNum + "eaten pawns");
	}
	else if(color == "black" && isEaten == true && isYourTurn == true) {
		whiteEatenNum++;
		$("#whiteEaten").html("You have " + whiteEatenNum + "eaten pawns");
	}
	else if(color == "black" && isEaten == true && isYourTurn == false) {
		blackEatenNum++;
		$("#blackEaten").html("You have " + blackEatenNum + "eaten pawns");
	}
	
	removePawnFrom(color, from , columnSizeOnFrom);
	if(to != -1 || to !=24) addPawnTo(color, to , columnSizeOnTo, isYourTurn);
	
	if(!Boolean(isHasMoreMoves)){
		$("table.board td").unbind("click", selectMove);
		$("#whiteEaten").unbind("click", selectMove);
		$("#blackEaten").unbind("click", selectMove);
		$("#whiteOut").unbind("click", selectMove);
		$("#blackOut").unbind("click", selectMove);
		$("#txtFromServer").html("");
	}
	else{
		$("#txtFromServer").append("Enter your next move.");
	}
}

function removePawnFrom(color, from , columnSizeOnFrom){
	var arr = $("table.board td[class~='backgammon-col-" + from + "']");
	if(from == 24){
		whiteEatenNum--;
		if(whiteEatenNum == 0){
			$("#whiteEaten").html("Eaten of white");
		}
		else{
			$("#whiteEaten").html("You have " + whiteEatenNum + "eaten pawns");
		}
	}
	else if(from == -1){
		blackEatenNum--;
		if(blackEatenNum == 0){
			$("#blackEaten").html("Eaten of black");
		}
		else{
			$("#blackEaten").html("You have " + blackEatenNum + "eaten pawns");
		}
	}
	else if(columnSizeOnFrom < 5){
		if($(arr[0]).hasClass("bottom-col-backgammon")){
			var item = arr[arr.length - (5-columnSizeOnFrom)];
		}
		else{
			var item = arr[5 - 1 - columnSizeOnFrom];
		}
		$(item).addClass("empty-cell");
		$(item).html("e");
	}
}

function addPawnTo(color, to , columnSizeOnTo, isYourTurn){
	var arr = $("table.board td[class~='backgammon-col-" + to + "']");
	if(columnSizeOnTo <=5){
		if($(arr[0]).hasClass("bottom-col-backgammon")){
			var item = arr[columnSizeOnTo-1];
		}
		else{
			var item = arr[5-columnSizeOnTo];
		}
		$(item).removeClass("empty-cell");
		
		if(isYourTurn == true){
			if(color == "white") $(item).html("W");
			else if(color == "black") $(item).html("B");
		}
		else{
			if(color == "white") $(item).html("B");
			else if(color == "black") $(item).html("W");
		}
		
	}
}

/************************************************/



/*********************************************/
var from = undefined;
var to = undefined;

function selectMove(e){
	var col = $(e.target).attr("class");
	
	
	var beginIndex = col.search("backgammon-col-");
	col = col.substring(beginIndex, beginIndex +17).trim();
	
	if(col.length == 17) col = col.substring(15,17);
	else col = col.substring(15,16);
	
	if(from === undefined) {
		$("#txtFromServer").html("From column = " + col);
		from = col;
		console.log("from was selected = " +from);
	}
	else if(to === undefined){
		to = col;
		$("#txtFromServer").html("You selected to move from column " + from + " to column " + to);
		console.log("to was selected = " + to);
		sendMoveToServer(from,to);
	}
}

function sendMoveToServer(from,to){
	var move = {move:{from:{index:from}, to:{index:to}}}; 
	var param = {move:move, gameRoomId:gameRoomId};

	
	$.ajax({
		url:"http://localhost:8080/backgammon-game-web/backgammon/move",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify(param),
		type: "POST",
		success: successPassMove,
		timeout: 60000,
		headers: headers
	});
	
	return false;
}

function successPassMove(){
	from = undefined;
	to = undefined;
}

var isHaveWinner;

function handleWinner(obj){
	var color = obj.color;
	var isWinner = obj.winner;
	
	if(Boolean(isWinner)) $("#txtFromServer").html(color + " player you are the winner of this game.")
	else $("#txtFromServer").html(color + " player you lost this game. Looser.");
	
	isHaveWinner = true;
}



