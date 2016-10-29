
var backgammonApp = angular.module("backgammonApp", [ "ngRoute" , "ngCookies"]);

backgammonApp.config(function ($routeProvider) {
	
	$routeProvider
//		.when("/", {templateUrl: "/backgammon-game-web/ng/ng-partials/home_template.html"})
//		.when("/register", {templateUrl: "/backgammon-game-web/register"});
	.when("/", {controller: "HomeController", templateUrl: "/backgammon-game-web/ng/app/partials/home.html"})
	.when("/lobby/", {templateUrl: "/backgammon-game-web/ng/app/partials/lobby.html"});

	//.when("/header", {controller: "HeaderController", templateUrl: "/backgammon-game-web/ng/app/partials/header.html"})
});