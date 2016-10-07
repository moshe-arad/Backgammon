package org.moshe.arad.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

//@Component
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

	private final Logger logger = LogManager.getLogger(GlobalHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Exception exception) {
		
		logger.error("Error was occurred Global Error Handler.");
		logger.error(exception.getMessage());
		logger.error(exception);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("global_error");
		return mav;
	}

}
