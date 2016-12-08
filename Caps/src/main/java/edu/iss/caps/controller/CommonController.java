package edu.iss.caps.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ModelAndView authenticate(@ModelAttribute User user, HttpSession session, BindingResult result)  throws FailedAuthentication{
		ModelAndView mav = new ModelAndView("login");
		User u = uService.authenticate(user.getUserId(), user.getPassword());
		u.setPassword("***");
		session.setAttribute("user", u);
		switch (u.getRole()){
		case "Admin": mav=new ModelAndView("redirect:/admin/managelecturer");
				break;
		case "Lecturer": mav=new ModelAndView("redirect:/Lec/byid");
				break;
		case "Student": mav=new ModelAndView("redirect:/Course/listall");
		}
		return mav;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String sessionTest(HttpSession session) {
		User u=(User) session.getAttribute("user");
		return u.toString();
	}
	
	
}
