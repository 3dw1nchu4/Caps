package edu.iss.caps.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Convert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.LecturerDetail;
import edu.iss.caps.repository.CourseRepository;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.LecturerService;

@Controller
@RequestMapping("/Lec")

public class Lecturercontroller {

	
	

	@Autowired
	private EnrolmentService ens;

	@Autowired
	private CourseService cs;
	
	@RequestMapping(value="/sec",method = RequestMethod.GET)
	public String testMestod(HttpServletRequest request){
	    request.getSession().setAttribute("name", "L003");
	    
	    return "Sessionset";
	  }
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView viewtaught() {
		ModelAndView mav = new ModelAndView("courselist");
		List<Course> course = cs.findAllCourses();
		mav.addObject("list", course);
		return mav;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "NewFile";
	}

	@RequestMapping(value = "/byid", method = RequestMethod.GET)
	public ModelAndView viewtaughtbyid(HttpServletRequest request) {
		Object lecid=request.getSession().getAttribute("name");
		String s=(String) lecid;
		ModelAndView mav = new ModelAndView("courselist");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("list", course);
		return mav;
	}

	@RequestMapping(value = "/enrole", method = RequestMethod.GET)
	public ModelAndView employeeListPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("enrolelist");
		Object lecid=request.getSession().getAttribute("name");
		String s=(String) lecid;
		List<Enrolment> enrole = ens.findstudentbylecturerid(s);
		mav.addObject("Enlist", enrole);
		return mav;
	}
	
	
	@RequestMapping(value = "/enrole/{id}", method = RequestMethod.GET)
	public ModelAndView findonlyen(@PathVariable int id,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("enrolelist");
		Object lecid=request.getSession().getAttribute("name");
		String s=(String) lecid;
		List<Enrolment> enrole = ens.findbycid(id,s);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	 @RequestMapping(value = "/grade", method = RequestMethod.GET)
	 public ModelAndView gradeastudent() {
	 ModelAndView mav = new ModelAndView("gradinglist");
	 List<Enrolment> employeeList =ens.findungraded() ;
	 mav.addObject("Enlist", employeeList);
	 return mav;
	 }


	@RequestMapping(value = "/viewsp", method = RequestMethod.GET)
	public ModelAndView viewstudentperformance() {
		ModelAndView mav = new ModelAndView("studentperformance");
		List<Course> course = cs.findAllCourses();
		mav.addObject("list", course);
		return mav;
	}

}
