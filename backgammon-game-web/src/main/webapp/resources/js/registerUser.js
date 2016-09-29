/***  load DOM events ***/

$(loadDomEvents);

function loadDomEvents(){
	$("#email").keydown(timeOutCheckEmail);
	$("#password").keydown(timeOutCheckPassword);
	$("#confirmPassword").keydown(timeOutCheckConfirmPassword);
	$("#userName").keydown(timeOutCheckUserName)
}

/***  email validation ***/

var timeLeftForValidation;

function timeOutCheckEmail(){
	clearTimeout(timeLeftForValidation);
	var emailMessage = $("#invalidEmail");
	hideElement(emailMessage);
	timeLeftForValidation = setTimeout(checkEmail, 3000);
}

function checkEmail(){
	var emailMessage = $("#invalidEmail");
	var emailContent = $("form input[name='email']").val();
	if(!isValidEmail(emailContent)) showElement(emailMessage);
}

function isValidEmail(email){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  	return re.test(email);
}

function hideElement(element){
	element.addClass("hidden");
	element.removeClass("show");
}

function showElement(element){
	element.removeClass("hidden");
	element.addClass("show");
}

/***  password confirm validation ***/


var timeOutConfirmPassword;

function timeOutCheckConfirmPassword(){
	clearTimeout(timeOutConfirmPassword);
	hideElement($("#invalidConfirmPassword"));
	timeOutConfirmPassword = setTimeout(checkConfirmPasswordMatch, 3000);
}

function checkConfirmPasswordMatch(){
	var originalPassword = $("form input[name='password']")[0];
	var confirmPassword = $("form[action$='register'] div:last input")[0];
	
	if(originalPassword.value != confirmPassword.value) showElement($("#invalidConfirmPassword"));
}

/***  password constraints validation ***/

var timeOutPassword;

function timeOutCheckPassword(){
	clearTimeout(timeOutPassword);
	hideElement($("#invalidConfirmPassword"));
	hideElement($("#invalidPassword"));
	timeOutPassword = setTimeout(checkPasswordValidation, 3000);
}

function checkPasswordValidation(){
	var originalPassword = $("form input[name='password']")[0];
	
	var msg = isValidPassword(originalPassword.value);
	if(msg == "valid"){
		hideElement($("#invalidPassword"));
	}
	else{
		var pMsg = $("#invalidPassword")[0];
		$(pMsg).html(msg);
		showElement($("#invalidPassword"));
	}
}

function isValidPassword(password){
	
	if(password.length < 8) return "Password length must be at least 8 characters.";
	if(!twoNumbersValidation(password)) return "Password must contain at least 2 numbers.";
	if(!oneUpperCaseValidation(password)) return "Password must contain at least 1 upper case letter.";
	if(!oneUniqueCharacter(password)) return "Password must contain at least 1 unique character.";
	if(!threeLowerCaseValidation(password)) return "Password must contain at least 3 lower case letters.";
	
	return "valid";
}

function twoNumbersValidation(password){
	var pattern = /^[^(0-9)]*[0-9]{1}[^(0-9)]*[0-9]{1}.*/;
	return pattern.test(password);
}


function oneUpperCaseValidation(password){
	var pattern = /[A-Z]+/;
	return pattern.test(password);
}

function oneUniqueCharacter(password){
	var pattern = /[^(A-Z)|^(a-z)|^(0-9)]+/;
	return pattern.test(password);
}

function threeLowerCaseValidation(password){
	var pattern = /^[^(a-z)]*[a-z]{1}[^(a-z)]*[a-z]{1}[^(a-z)]*[a-z]{1}.*/;
	return pattern.test(password);
}

/***  user name available ***/

var timeOutUserName;

function timeOutCheckUserName(){
	var userName = $("#userName").val();
	var server = "http://localhost:8080";
	var url = server + $("#userNameUrl").attr("href") + userName;
	
	clearTimeout(timeOutUserName);
	hideElement($("#invalidUserName"));
	timeOutUserName = setTimeout(function(){
		checkUserNameAvailable(url, {userName:userName});
		}, 3000);
}

function validateUserName(){
	
}

function checkUserNameAvailable(url, userName){
	
	$("#invalidUserName").load(url, userName, showElement($("#invalidUserName")));
}





















