/***  email validation ***/

var timeLeftForValidation;

function timeOutCheckEmail(){
	var emailMessage = $("form p.text-danger");
	hideElement(emailMessage);
	timeLeftForValidation = setTimeout(checkEmail, 3000);
}

function checkEmail(){
	clearTimeout(timeLeftForValidation);
	var emailMessage = $("form p.text-danger");
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


var timeOutPassword;

function timeOutCheckPassword(){
	hideElement($("#invalidPassword"));
	timeOutPassword = setTimeout(checkConfirmPasswordMatch, 3000);
}

function checkConfirmPasswordMatch(){
	clearTimeout(timeOutPassword);
	var originalPassword = $("form input[name='password']")[0];
	var confirmPassword = $("form[action$='register'] div:last input")[0];
	
	if(originalPassword.value != confirmPassword.value) showElement($("#invalidPassword"));
}

function clearPasswordMsg(){
	hideElement($("#invalidPassword"));
}

var  v =$("form[action$='register'] div").last()[0];




























