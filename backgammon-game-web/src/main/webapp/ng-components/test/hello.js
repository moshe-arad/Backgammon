angular.module('backgammonApp', [ 'ngRoute' ]) // ... omitted code
.controller('navigation',

  function($rootScope, $scope, $http, $location) {

  var authenticate = function(credentials, callback) {
	
	var headers = credentials ? "username="+credentials.username+"&password="+credentials.password+"&submit=Login"
    : {};
    
//	  var headers = credentials ? "username=moshe.arad&password=Jpz2bc31#&submit=Login"
//			    : {};
    $http({
        method: 'POST',
        url: "/login", //$window.domaineName + 'j_spring_security_check',
        data: credentials,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          }
    })  
    .success(function(data, status){
    	if (data.name) {
            $rootScope.authenticated = true;
          } else {
            $rootScope.authenticated = false;
          }
          callback && callback();
    })
    .error(function(data, status){
    	$rootScope.authenticated = false;
        callback && callback();
    })
    
//    $http.get('user', {headers : headers}).success(function(data) {
//      if (data.name) {
//        $rootScope.authenticated = true;
//      } else {
//        $rootScope.authenticated = false;
//      }
//      callback && callback();
//    }).error(function() {
//      $rootScope.authenticated = false;
//      callback && callback();
//    });

  }

  authenticate();
  $scope.credentials = {};
  $scope.login = function() {
	  console.log("ttttttttttttttttttt");
      authenticate($scope.credentials, function() {
        if ($rootScope.authenticated) {
          $location.path("/");
          $scope.error = false;
        } else {
          $location.path("/login");
          $scope.error = true;
        }
      });
  };
});