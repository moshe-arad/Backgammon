var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");
var csrfToken = $("meta[name='_csrf']").attr("content");

$(document).ready(function(){
	$(loadDomEvents);
});


function loadDomEvents(){
	checkUntilReady();
}

var gameRoomId = $("#gameRoomId").html();
var remoteServer = $("#remoteServer").html();

var isReadyToPlay = false;

function checkIsReadyToPlay(){
	console.log("sending is ready message to " + remoteServer + "/init/" + gameRoomId);
	$.ajax({
		url: remoteServer + "/init/" + gameRoomId,
		success: checkIsReadyToPlayCallback
	});
	
	return false;
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

var headers = {};
headers[csrfHeader] = csrfToken;

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

function registerFailure(){
	failAttemptsToRegister++;
	if(failAttemptsToRegister < 3) registerToBackgammonDispatcher();
}

function handleMoveFromServer(obj){
	var from = obj.from.index; 
	var to = obj.to.index;
	
	if(from == -2 && to == -2){
		$("#txtFromServer").removeClass("hidden");
		$("#txtFromServer").append("White player it's your turn to play.");
	}
}
//function loadDomEvents(){
//	$("#identifyBtn").click(identifyMeFunc);
//	$("#userMoveBtn").click(sendUserMove);
//	$("#registerBtn").click(sendRegisterMsg)
//}



//function identifyMeFunc(){
//	console.log("ajax111");
//	
//	var params = {userName:"moshe.arad", password:"Jpz2bc31#", enabled:"true"};
//	$.ajax({
//		url: "http://localhost:8080/backgammon-game-web/backgammon/identifyMe",
//		data: params,
//		dataType:"json",
//		success: showUserResult
//	});
//	return false;
//}
//
//function showUserResult(jsonAns){
//	console.log("ajax");
//	
////	var obj = $.parseJSON(jsonAns);
//	var msg = "Hello from " + jsonAns.loggedUserName +
//	", dummy user sent to server is:\n user name: " + 
//	jsonAns.basicUser.userName + "\n password: " + jsonAns.basicUser.password + 
//	" \n enabled: " + jsonAns.basicUser.enabled;
//	alert(msg);
//}
//
///**************************************************/
//
//var token = $("meta[name='_csrf']").attr("content");
//var header = $("meta[name='_csrf_header']").attr("content");
//
//function sendUserMove(){
//	
//	var basicUser = {userName:"andy.cole", password:"Jpz2bc31#", enabled:"true"};
//	var move = {move:{from:{index:"10"}, to:{index:"15"}}}; 
//	var userMove = {user:basicUser, move:move};
//	var param = userMove;
//	
//	console.log(move);
//	
//	$.ajax({
//		url:"http://localhost:8080/backgammon-game-web/backgammon/sendUserMove",
//		contentType: "application/json",
//		type: "POST",
//		data: JSON.stringify(param),
//		dataType: "json",
//		success: alertWhatSent,
//		beforeSend: function (xhr) {xhr.setRequestHeader(header, token);}
//	});
//	return false;
//}
//
//
//function alertWhatSent(jsonObj){
//	var msg = "move from " + jsonObj.move.from.index + 
//	" to " + jsonObj.move.to.index + ", message was sent to " + jsonObj.user.userName;
//	
//	alert(msg);
//}
//
//
//function sendRegisterMsg(){
//	$.ajax({
//		url:"http://localhost:8080/backgammon-game-web/backgammon_dispatcher/register",
//		contentType: "application/json",
//		dataType: "json",
//		data: JSON.stringify({}),
//		type: "POST",
//		success: alertWhatReceived,
//		beforeSend: function (xhr) {xhr.setRequestHeader(header, token);}
//	});
//	return false;
//}
//
//function alertWhatReceived(jsonObj){
//	var msg = "move from " + jsonObj.from.index + 
//	" to " + jsonObj.to.index;
//	
//	alert(msg);
//}














