<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Arad's Backgammon Game</title>
		<link rel="stylesheet" href='<spring:url value="/resources/bootstrap/bootstrap.min.css"/>' type="text/css" />
		<link rel="stylesheet" href='<spring:url value="/resources/css/home.css"/>' type="text/css" />
		<script src="<spring:url value="/resources/bootstrap/jquery-3.1.0.min.js" />"></script>
		<script src="<spring:url value="/resources/bootstrap/bootstrap.min.js" />" /></script>
		<script src="<spring:url value="/resources/js/logout.js" />"></script>
	</head>
	<body> --%>
		<nav class="navbar navbar-inverse">
			<div class="container">
				<div class="row">
					<div class="col-lg-5">
						<div class="navbar-header"><h1>Arad's Backgammon Game</h1></div>
					</div>
					<sec:authorize access="authenticated" var="authenticated"></sec:authorize>
					<div class="col-lg-7">
						<c:choose>
							<c:when test="${authenticated}">
								<div class="navbar-right">
									<p class="lead">Welcome 
										<sec:authentication property="name"/>
										&nbsp;|&nbsp; 
										<a id="logoutLink" href="#">Logout</a>
										<form id="logoutForm" method="POST" action='<c:url value="/logout"/>'>
											<sec:csrfInput/>
										</form>
									</p>										
								</div>
							</c:when>
							<c:otherwise>
								<c:url value="/login" var="loginVar"/>
								<form id="loginForm" class="navbar-form navbar-right" method="POST" action="${loginVar}">
					
									<div class="form-group">
										<label for="userNameLogin">User Name:</label>
										<br/>
										<input type="text" class="form-control" name="userNameLogin" placeholder="User Name">
									</div>
					
									<span>&nbsp;&nbsp;&nbsp;</span>
					
									<div class="form-group">
										<label for="password">Password:</label>
										<br/>
										<input type="password" class="form-control" name="passwordLogin" placeholder="Password">
									</div>
					
									<span>&nbsp;&nbsp;&nbsp;</span>
					
									<div class="form-group">
										<br/>
										<button type="submit" class="btn btn-primary">Log In</button>
									</div>
					
									<c:if test="${param.error != null}">
										<p>Invalid Username or Password.</p>
									</c:if>
					
									<c:if test='${param.logout.equals("true")}'>
										<p>You have successfully logged out.</p>
									</c:if>
									<sec:csrfInput/>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</nav>