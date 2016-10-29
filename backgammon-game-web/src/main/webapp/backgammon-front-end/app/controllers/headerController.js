(function(){

	function HeaderController ($scope, authenticationProvider) {

		$scope.authentication_error = "";
		$scope.authentication = "";
		$scope.user_name = "";

		var init = function () {
			authenticationProvider.isAuthenticated( function(err, data) {				
				if (err) {
                	$scope.authentication_error = "Unexpected error while authenticating user " + err.message;
            	} else {
                	if(data.authenticated == true){
                		$scope.authentication = true;
                		$scope.user_name = data.user_name;             
                	}
				}
			});
		}
	}

	backgammonApp.controller("HeaderController", HeaderController);
})();