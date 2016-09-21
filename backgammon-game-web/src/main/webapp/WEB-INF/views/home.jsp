<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Insert title here</title>
	<link rel="stylesheet" href='<spring:url value="/resources/bootstrap/bootstrap.min.css"/>' type="text/css" />
	<link rel="stylesheet" href='<spring:url value="/resources/css/home.css"/>' type="text/css" />
	<script src="<spring:url value="/resources/bootstrap/jquery-3.1.0.min.js" />"></script>
	<script src="<spring:url value="/resources/bootstrap/bootstrap.min.js" />" /></script>
	</head>
	<body>
		<nav class="navbar navbar-inverse">
			<div class="container">
				<div class="row">
					<div class="col-lg-1 col-md-1"></div>
					<div class="col-lg-10 col-md-10">
						<div class="navbar-header"><h1>Arad's Backgammon Game</h1></div>
						<div class="navbar-right"><h3>Welcome</h3></div>
					</div>
					<div class="col-lg-1 col-md-1"></div>
				</div>
			</div>
		</nav>
		
		<div class="jumbotron">
			<div class="container">
				<h1>Hello & Welocme to Arad's Arcade Games.</h1>
				<p>This is an demo project, 
				which gives you the ability to play backgammon against 
				other players online.</p>
				<p>Go write a head and register. If you're already familiar with
				 this demo project click start play button.</p>
				 <h3>Enjoy!!! ;-)</h3>
				 <br/>
				 <button type="button" class="btn btn-primary">Sign-in</button>
				 &nbsp;&nbsp;&nbsp;
				 <button type="button" class="btn btn-primary">Register</button>
			</div>
		</div>
	</body>
</html>