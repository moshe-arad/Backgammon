<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href='<spring:url value="/resources/css/lobby.css"/>' type="text/css" />
	</head>
	<body>
		
		<jsp:include page="header.jsp"></jsp:include>
		
		<div class="container">
			<div class="row center-block">
				<div class="col-lg-5"> </div>
				<div class="col-lg-2"> 
					<p id="title" class="text-center text-warning">Lobby</p>
				</div>
				<div class="col-lg-5"> </div>
			</div>
			<div class="row">
				<div class="col-lg-5"> </div>
				<div class="col-lg-2"> 
					<a class="btn btn-primary btn-lg btn-block" href='<spring:url value="/backgammon" />' role="button">
						Play
					</a>
				</div>
				<div class="col-lg-5"> </div>
			</div>
		</div>
		
	</body>
</html>