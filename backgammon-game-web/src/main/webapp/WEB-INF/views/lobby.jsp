<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
		<link rel="stylesheet" href='<spring:url value="/resources/css/lobby.css"/>' type="text/css" />
		<script src="<spring:url value="/resources/bootstrap/jquery-3.1.0.min.js" />"></script>
		<script src="<spring:url value="/resources/bootstrap/bootstrap.min.js" />" /></script>
		<script src="<spring:url value="/resources/js/logout.js" />"></script>
		<script src="<spring:url value="/resources/js/lobby.js" />"></script>
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
				<div class="col-lg-4"> </div>
				<div class="col-lg-4"> 
					<button id="openNewRoomBtn" type="button" class="btn btn-primary btn-lg btn-block">
						Open New Game Room
					</button>
					<br/>
				</div>
				<div class="col-lg-4"> </div>
			</div>
			
			<div id="avialableRoomsRow" class="row">
				<div class="col-lg-3"> </div>
				<div class="col-lg-6">
					<div id="joinBtn" class="row hidden">
						<div class="col-lg-2"> </div>
						<div class="col-lg-4">
							<button type="button" class="btn btn-success btn-lg btn-block">Join</button>
						</div>
						<div class="col-lg-4">
							<button type="button" class="btn btn-danger btn-lg btn-block">Cancel</button>
						</div>
						<div class="col-lg-2"> </div>
					</div>
					
					<spring:url value="/lobby/join" var="joinRoom"/>
					<p id="joinFormDestUrl" class="hidden">${joinRoom}</p>
					<form id="joinForm" method="POST" action="#" >
						<table class="table">
							<thead>
								<tr>
									<th>#</th>
									<th>Room Name</th>
									<th>Private Room</th>
									<th>Game Speed</th>
									<th class="hidden"></th>
								</tr>
							</thead>
							<tbody>	
								<c:set var="roomsSize" value="${gameRooms.size()}" />
								<c:if test="${roomsSize > 0}">
									<c:forEach var="i" begin="0" end="${roomsSize - 1}">
										<tr>
											<th scope="row"><c:out value="${i+1}"></c:out></th>
											<td>${gameRooms.get(i).getGameRoomName()}</td>
											<c:choose>
												<c:when test="${gameRooms.get(i).getIsPrivateRoom().equals(true)}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${gameRooms.get(i).getSpeed() == 0}">
													<td>High - 30 sec</td>
												</c:when>
												<c:when test="${gameRooms.get(i).getSpeed() == 1}">
													<td>Medium - 45 sec</td>
												</c:when>
												<c:otherwise>
													<td>Low - 60 sec</td>
												</c:otherwise>
											</c:choose>								
																				
											<td class="hidden">
												<c:out value="${gameRooms.get(i).getGameRoomId()}" />																							
											</td>												
										</tr>
									</c:forEach>
								</c:if>																																		
							</tbody>	
						</table>
						<sec:csrfInput/>
					</form>
				</div>
				<div class="col-lg-3"> </div>
			</div>
			
			<div id="openRoomRow" class="row hidden">
				<div class="col-lg-4"></div>
				<div class="col-lg-4">
					<spring:url value="/lobby/open" var="openRoom"/>
					<form:form method="POST" action="${openRoom}" modelAttribute="newGameRoom">
						<div class="form-group">
    						<label for="gameRoomName">Game Room Name</label>
    						<form:input type="text" path="gameRoomName" cssClass="form-control" id="gameRoomName" placeholder="Game Room Name" />
  						</div>
  						
  						<div class="form-group">
  							<label for="gameRoomName">Private Game Room</label>
  							<form:select path="isPrivateRoom" cssClass="form-control selectpicker">
  								<form:option value="0"><c:out value="${privateRoomOptions.get(0)}" /></form:option>
  								<form:option value="1"><c:out value="${privateRoomOptions.get(1)}" /></form:option>
  							</form:select>
  						</div>
  						
  						<div class="form-group">
  							<label for="speed">Game Speed</label>
  							<form:select path="speed" cssClass="form-control selectpicker" >
  								<form:option value="0"><c:out value="${speedOptions.get(0)}"></c:out></form:option>
  								<form:option value="1"><c:out value="${speedOptions.get(1)}"></c:out></form:option>
  								<form:option value="2"><c:out value="${speedOptions.get(2)}"></c:out></form:option>
  							</form:select>
  						</div>
  						
  						 <button type="submit" class="btn btn-primary">Ok</button>
  						 <button type="button" class="btn btn-primary">Cancel</button>
					</form:form>
				</div>
				<div class="col-lg-4"> </div>
			</div>
		</div>
	</body>
</html>