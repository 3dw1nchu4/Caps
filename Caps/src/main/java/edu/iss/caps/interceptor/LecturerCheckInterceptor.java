package edu.iss.caps.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.iss.caps.model.User;

public class LecturerCheckInterceptor extends HandlerInterceptorAdapter  {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {		
		
		if(request.getSession().getAttribute("user")!=null){
			User u=(User) request.getSession().getAttribute("user");
			if (!u.getRole().equals("Lecturer")){
				response.sendRedirect("/caps/home/login");
			}
		}
		else if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("/caps/home/login");
		}
		return true;
		
		
	}
}
