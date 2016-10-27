
var backgammonApp = angular.module("backgammonApp", [ "ngRoute", 'ngCookies' ]);

backgammonApp.config(function ($routeProvider, $httpProvider) {
	
	$routeProvider
//		.when("/", {templateUrl: "/backgammon-game-web/ng/ng-partials/home_template.html"})
//		.when("/register", {templateUrl: "/backgammon-game-web/register"});
	.when("/", {templateUrl: "/backgammon-game-web/ng/ng-partials/home_template.html"})
	.when("/lobby/", {templateUrl: "/backgammon-game-web/ng/ng-partials/lobby.html"});
});