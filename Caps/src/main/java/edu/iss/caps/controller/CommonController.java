package edu.iss.caps.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.caps.exception.FailedAuthentication;
import edu.iss.caps.model.User;
import edu.iss.caps.service.UserService;

@RequestMapping("/home")
@Controller
public class CommonController {

	@Autowired
	UserService uService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String logic(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute User user, HttpSession session, BindingResult result,HttpServletResponse response,HttpServletRequest request)
			throws FailedAuthentication {
		ModelAndView mav = new ModelAndView("login");
		try{
			User u = uService.authenticate(user.getUserId(), user.getPassword());
			u.setPassword("***");
			session.setAttribute("user", u);
			response.sendRedirect(request.getContextPath()+"/home/redirectToRolesHomePage");
			
		} catch(Exception e){
			mav.addObject("errorMsg", "Please ensure you have entered the correct UserId and Password.");
		}		
		return mav;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String sessionTest(HttpSession session) {
		User u = (User) session.getAttribute("user");
		return u.toString();
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView homeindex(HttpSession session) {
		ModelAndView mav = new ModelAndView("index");
		String role = null;
		try {
			User u = (User) session.getAttribute("user");
			role = u.getRole();
		} catch (Exception e) {
			role = null;
		}
		mav.addObject("user", new User());
		mav.addObject("role", role);

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		// User u = (User) request.getSession().getAttribute("user");

//		status.setComplete();
//		request.removeAttribute("user", WebRequest.SCOPE_SESSION);
		session.invalidate();
		return "redirect:/home/index?action=logout";

	}

	@RequestMapping(value = "/redirectToRolesHomePage", method = RequestMethod.GET)
	public ModelAndView movein(HttpSession session) throws FailedAuthentication {

		ModelAndView mav = new ModelAndView("login");
		String move = null;
		try {
			User u = (User) session.getAttribute("user");
			move = u.getRole();
		} catch (Exception e) {
			move = "dontgo";
		}
		switch (move) {
		case "Admin":
			mav = new ModelAndView("redirect:/admin/managelecturer");
			break;
		case "Lecturer":
			mav = new ModelAndView("redirect:/Lec/viewallenrole");
			break;
		case "Student":
			mav = new ModelAndView("redirect:/Course/listall");
			break;
		default:
			mav = new ModelAndView("redirect:/home/login");

		}
		return mav;
	}

	@RequestMapping(value = "/map", method = RequestMethod.GET)

	public ModelAndView map(HttpSession session) {
		ModelAndView mav = new ModelAndView("map");
		return mav;
	}

}
