(function () {
	function authenticationProvider ($http) {
		
		this.isAuthenticated = function(callback) {
			$http.post("/backgammon-game-web/authenticate")
				.success(function (data,status) {
					callback(null, data);
				})
				.error(function (data, status, headers, conf) {
                    // just send back the error
                    callback(data);
				});
		};

	}
	
	backgammonApp.service("authenticationProvider", authenticationProvider);
		
})();