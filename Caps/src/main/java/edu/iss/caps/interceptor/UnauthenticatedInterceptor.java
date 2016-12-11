package edu.iss.caps.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UnauthenticatedInterceptor extends HandlerInterceptorAdapter  {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {		
		
		if(request.getSession().getAttribute("user")==null)
			response.sendRedirect("/caps/home/login");
			
		return true;
		
		
	}
}

