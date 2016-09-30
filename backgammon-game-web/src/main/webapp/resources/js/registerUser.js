/***  load DOM events ***/

$(loadDomEvents);

function loadDomEvents(){
	
	$("#firstName").keydown(function(){
		timeOutCheckName($("#invalidFirstName"), $("#firstName"));
	});
	$("#firstName").focusout(function(){
		timeOutCheckName($("#invalidFirstName"), $("#firstName"));
	});
	
	$("#lastName").keydown(function(){
		timeOutCheckName($("#invalidLastName"), $("#lastName"));
	});
	$("#lastName").focusout(function(){
		timeOutCheckName($("#invalidLastName"), $("#lastName"));
	});
	
	$("#email").keydown(timeOutCheckEmail);
	$("#email").focusout(timeOutCheckEmail);
	
	$("#password").keydown(timeOutCheckPassword);
	$("#password").focusout(timeOutCheckPassword);
	
	$("#confirmPassword").keydown(timeOutCheckConfirmPassword);
	$("#confirmPassword").focusout(timeOutCheckConfirmPassword);
	
	//ajax calls
	
	$("#userName").keyup(timeOutCheckUserName);
	$("#userName").focusout(timeOutCheckUserName);
	
	$("#email").keyup(timeOutCheckEmailAvailable);
	$("#email").focusout(timeOutCheckEmailAvailable);
	
	$("#registerBtn").click(submitRegister);
}

var server = "http://localhost:8080";

/***  first & last names validation ***/

var timeOutNameValidation;

function timeOutCheckName(nameInvalidTag, nameTag){
	clearTimeout(timeLeftForValidation);
	hideElement(nameInvalidTag);
	timeLeftForValidation = setTimeout(function(){
		checkName(nameInvalidTag, nameTag)
	}, 3000);
}

function checkName(nameInvalidTag, nameTag){
	if(!isValidName(nameTag.val())) {
		showElement(nameInvalidTag);
		return false;
	}
	else return true;
}

function isValidName(name){
	var re = /[^A-Z|a-z| \\-]+/;
  	return !re.test(name);
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
	if(!isValidEmail(emailContent)) {
		emailMessage.text("Invalid email.");
		showElement(emailMessage);
		return false;
	}
	else return true;
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
	
	if(originalPassword.value != confirmPassword.value) {		
		showElement($("#invalidConfirmPassword"));
		return false;
	}
	else return true;
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
		return true;
	}
	else{
		var pMsg = $("#invalidPassword")[0];
		$(pMsg).html(msg);
		showElement($("#invalidPassword"));
		return false;
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
	var url = server + $("#userNameUrl").attr("href"); // + userName;
	
	userNameAjaxAnswer=false;
	clearTimeout(timeOutUserName);
	hideElement($("#invalidUserName"));
	timeOutUserName = setTimeout(function(){
		checkUserNameAvailable(url, {userName:userName});
		}, 3000);
}


function checkUserNameAvailable(url, userName){

	$.ajax(
			{
				url:url, 
				data:userName,
				method: "GET",
				success: userNameCompleteAjaxRequest
			}
		);
}

function userNameCompleteAjaxRequest(text){
	if(text == "") {
		userNameAjaxAnswer = true;
		hideElement($("#invalidUserName"));
	}
	else {
		userNameAjaxAnswer = false;;
		showElement($("#invalidUserName"));
	}
	$("#invalidUserName").text(text);
	
}

/***  email available ***/

var timeOutEmail;

function timeOutCheckEmailAvailable(){
	var email = $("#email").val();
	var url = server + $("#emailUrl").attr("href"); 
	
	if(isValidEmail(email)){
		emailAjaxAnswer = false;
		clearTimeout(timeOutEmail);
		hideElement($("#invalidEmail"));
		timeOutEmail = setTimeout(function(){
			checkEmailAvailable(url, {email:email});
			}, 3000);
	}
}

function checkEmailAvailable(url, email){

	$.ajax(
			{
				url:url, 
				data:email,
				method: "GET",
				success: emailCompleteAjaxRequest
			}
		);
}

function emailCompleteAjaxRequest(text){
	$("#invalidEmail").text(text);
	
	if(text == "") {
		emailAjaxAnswer = true;
		hideElement($("#invalidEmail"));
	}
	else {
		emailAjaxAnswer = false;;
		showElement($("#invalidEmail"));
	}
}

/***  submit register form  ***/

var userNameAjaxAnswer = false;
var emailAjaxAnswer = false;

function submitRegister(e){
	e.preventDefault();
	
	setTimeout(function(){
		if(checkName($("#invalidFirstName"), $("#firstName")) &&
				checkName($("#invalidLastName"), $("#lastName")) &&
				checkEmail() && 
				checkConfirmPasswordMatch() && 
				checkPasswordValidation() &&
				userNameAjaxAnswer &&
				emailAjaxAnswer) 
			$("#registerForm").submit();
	}, 3000);
}















