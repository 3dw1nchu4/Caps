package edu.iss.caps.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.EnrolmentService;

@Controller
@RequestMapping(value = "/Student")
public class StudentController {
	@Autowired
	private EnrolmentService ens;
	@Autowired
	private CourseService crs;

	@RequestMapping(value = "/sec", method = RequestMethod.GET)
	public String testMestod(HttpServletRequest request) {
		request.getSession().setAttribute("name", "S003");

		return "Sessionset";
	}

	@RequestMapping(value = "/grade", method = RequestMethod.GET)
	public ModelAndView studentviewgrade(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("list-grade");
		Object sid = request.getSession().getAttribute("name");
		String s = (String) sid;
		List<Enrolment> grades = ens.findCourseBySID(s);

		mav.addObject("grlist", grades);
		return mav;
	}

	@RequestMapping(value = "/course/view", method = RequestMethod.GET)
	public ModelAndView studentviewcourse(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("view-course");
		Object sid = request.getSession().getAttribute("name");
		String s = (String) sid;
		ArrayList<Course> courses = crs.findbynotcop(s);

		mav.addObject("crlist", courses);
		return mav;
	}

	// ModelAndView mav = new ModelAndView("login");
	// if (us.getSessionId() != null) {
	// mav = new ModelAndView("/list-grade");
	// mav.addObject("sgrade",
	// eService.findCoursesByEID(us.getEmployee().getEmployeeId()));
	// return mav;
	// }
	// return mav;

}
