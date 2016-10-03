package org.moshe.arad.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("logoutHandler")
public class BackgammonLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, 
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if(request.getParameter("msgShow") != null && request.getParameter("msgShow").equals("false")){ 
			redirectResponse(request, response, 
					"http://" + request.getServerName() + ":" + request.getServerPort() + "/backgammon-game-web/home?logout=false");
		}
		else{
			redirectResponse(request, response,
					"http://" + request.getServerName() + ":" + request.getServerPort() + "/backgammon-game-web/home?logout=true");
		}
	}

	private void redirectResponse(HttpServletRequest request, HttpServletResponse response, String destination) {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", destination);
	}
	
}
