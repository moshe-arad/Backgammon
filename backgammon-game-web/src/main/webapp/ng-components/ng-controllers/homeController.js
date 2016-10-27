
(function(){
	
	function HomeController ($scope, $interval, $timeout, $cookies) {
		
		$scope.user = {};
		
		alert($cookies.get("XSRF-TOKEN"));
		
		angular.element("#cookie").val($cookies.get("XSRF-TOKEN"));
		
		$scope.myToken = $cookies.get("XSRF-TOKEN");
		
		var isEmailTaken;
		var stopEmailCheck;
		
		$scope.onMySubmit = mySubmit;
		
		function mySubmit(user){
			
		}
		/******** shared with view ************/
			
		$scope.onEmailInsert = checkValidEmail;
				
		$scope.onUserNameInsert = checkUserNameAvailable;
		
		$scope.onCheckValidPassword = checkValidPassword;
		
		$scope.onCheckPasswordsMatch = checkPasswordsMatch;
		
		/***** email *****/
		
		function checkValidEmail(user){
			
			if(angular.isDefined(stopEmailCheck)){
				if(isValidEmail(user.email)){
					 $interval.cancel(stopEmailCheck);
						stopEmailCheck = undefined;
				 }
				return;
			}
			
			 stopEmailCheck = $interval(function(user){
				
				if(isValidEmail(user.email)) 
				{											
					checkEmailAvailable(user);					
					console.log(isEmailTaken);
					if(!isEmailTaken) angular.element("#invalidEmail").addClass("hidden");
				}
				else{
					angular.element("#invalidEmail").html("Invalid email address.");
					angular.element("#invalidEmail").removeClass("hidden");
				}
			}, 3000, 0, false, user);
		}
		
		function checkEmailAvailable(user){
			$timeout(checkAvailable, 1000, false, {"email":user.email}, "/backgammon-game-web/email");
		}
		
		var isValidEmail = function (email){
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			
			if(!re.test(email)) return false;
			else return true;
		}
		
		/***** user name *****/
		
		function checkUserNameAvailable(user){		
			if(typeof user.user_name != "undefined" && user.user_name.length >=3)
			{
				console.log(user.user_name);
				
				$timeout(checkAvailable, 1000, 
						false, 
						{"userName":user.user_name}, 
						"/backgammon-game-web/user_name");
			}
				
		}
		
		function checkAvailable(param, url){

			$.ajax(
					{
						url:url, 
						data:param,
						method: "GET",
						success: successCheckAvailable
					}
				);
		}

		function successCheckAvailable(text){
			if(text == "") {
				angular.element("#invalidUserName").addClass("hidden");
				isEmailTaken=false;
			}
			else {
				if(text == "Email is not availbale.") {
					isEmailTaken = true;
					angular.element("#invalidEmail").html(text);
					angular.element("#invalidEmail").removeClass("hidden");
				}
				else if(text == "User name is not available."){
					angular.element("#invalidUserName").html("User name is not available.")
					angular.element("#invalidUserName").removeClass("hidden");
				}
			}
		}
		
		/************** password ************/
		
		function checkValidPassword(user){
			
			$interval(function(){
				if(typeof user.password != "undefined"){
					var result = isValidPassword(user.password);
					if(result != "valid") {
						angular.element("#invalidPassword").html(result);
						angular.element("#invalidPassword").removeClass("hidden")
					}
					else{
						angular.element("#invalidPassword").addClass("hidden")
					}
				}
			}, 3000, 0, false, user);
			
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
		
		function clearPasswordMessage(){
			
			if($scope.password_error != "") {
				$scope.password_error = "";
			}
		}
		
		/************* password match ***********/
		function checkPasswordsMatch(user){
			
			console.log(user.confirm);
			$interval(function(){
				if(angular.isDefined(user.confirm) && angular.isDefined(user.password)){					
					if(user.confirm != user.password){
						angular.element("#invalidConfirmPassword").html("Passwords does not match.");
						angular.element("#invalidConfirmPassword").removeClass("hidden");						
					}
					else{
						angular.element("#invalidConfirmPassword").addClass("hidden");
					}
				}
			}, 500, 0, false, user);
			
		}
	}
	
	backgammonApp.controller("HomeController", HomeController);
})();