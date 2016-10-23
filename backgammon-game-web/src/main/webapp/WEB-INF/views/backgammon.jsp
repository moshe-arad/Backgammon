<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<sec:csrfMetaTags />
		<title>Arad's Backgammon Game</title>
		<link rel="stylesheet" href='<spring:url value="/resources/bootstrap/bootstrap.min.css"/>' type="text/css" />
		<link rel="stylesheet" href='<spring:url value="/resources/css/home.css"/>' type="text/css" />
		<link rel="stylesheet" href='<spring:url value="/resources/css/backgammon.css"/>' type="text/css" />
		<script src="<spring:url value="/resources/bootstrap/jquery-3.1.0.min.js" />"></script>
		<script src="<spring:url value="/resources/bootstrap/bootstrap.min.js" />" /></script>
		<script src="<spring:url value="/resources/js/logout.js" />"></script>		
	</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<p id="gameRoomId" class="hidden"><c:out value="${gameRoomId}" /></p>
		<p id="remoteServer" class="hidden"><spring:url value="/backgammon"></spring:url></p>
		
		<div class="container">
			<div class="row">
				<div class="col-lg-5"></div>
				<div class="col-lg-2"><h1>Board</h1></div>
				<div class="col-lg-5"></div>		
			</div>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="col-lg-2">
					<p id="whiteEaten" class="lead text-success backgammon-col-24">Outs of white</p>
				</div>
				<div class="col-lg-2"></div>
				<div class="col-lg-2">
					<p id="txtFromServer" class="bg-primary"></p>
				</div>
				<div class="col-lg-2">
					<button id="rollDicesBtn" type="button" class="btn btn-success hidden">Roll dices</button>
				</div>
				<div class="col-lg-2"></div>
				<div class="col-lg-2">
					<p id="blackEaten" class="lead text-success backgammon-col--1">Outs of black</p>
				</div>					
			</div>
		</div>
		
		<div class="container">
			<div class="row">			
				<c:choose>
					<c:when test='${player == "white"}'>
						<jsp:include page="white_board.jsp"></jsp:include>											
					</c:when>
					<c:otherwise>
						<jsp:include page="black_board.jsp"></jsp:include>
					</c:otherwise>
				</c:choose>	
			</div>	
		</div>	
		
	<script src="<spring:url value="/resources/js/backgammon.js" />"></script>	
	</body>
</html>