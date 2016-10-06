<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Arad's Backgammon Game</title>
		<link rel="stylesheet" href='<spring:url value="/resources/bootstrap/bootstrap.min.css"/>' type="text/css" />
		<link rel="stylesheet" href='<spring:url value="/resources/css/home.css"/>' type="text/css" />
		<script src="<spring:url value="/resources/bootstrap/jquery-3.1.0.min.js" />"></script>
		<script src="<spring:url value="/resources/bootstrap/bootstrap.min.js" />" /></script>
	</head>
	
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		
		<div class="container">
			<h1>An error was encountered and handled by a global Exception Handler Resolver.</h1>
			<br/>
			
			<form id="logoutForm" method="POST" action='<c:url value="/logout?msgShow=false"/>'>
				<button type="submit" class="btn btn-primary btn-lg">Go Back</button>
				<sec:csrfInput/>
			</form>
		</div>
	</body>
</html>