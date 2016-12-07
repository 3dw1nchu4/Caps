package edu.iss.caps.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.LecturerDetail;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.LecturerService;
import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.UserService;

/*import edu.iss.cats.exception.CourseNotFound;
import edu.iss.cats.model.Course;
import edu.iss.cats.model.CourseEvent;
import edu.iss.cats.service.CourseEventService;
import edu.iss.cats.service.CourseService;
import edu.iss.cats.validator.CourseValidator;*/

@Controller
@RequestMapping(value = "/test")
public class TestController {
	@Autowired
	private LecturerService lds;
	@Autowired
	private CourseService cs;
	@Autowired
	private StudentService ss;
	@Autowired
	private EnrolmentService scs;
	@Autowired
	private UserService us;
	

	@RequestMapping(value = "/1")
	@ResponseBody
	public String test() {
		
		String s="";
		ArrayList<LecturerDetail> l = lds.findAllLecturers();
		for (LecturerDetail ldetails : l){
			s=s+ldetails.toString();
		}
		return s;

	}
	
	@RequestMapping(value = "/2")
	@ResponseBody
	public String test2() {
		
		return cs.findCourse(1).toString();
	}
	
	@RequestMapping(value = "/3")
	@ResponseBody
	public String test3() {
		
		String s="";
		ArrayList<StudentDetail> l= ss.findAllStudents();
		for (StudentDetail ldetails : l){
			s=s+ldetails.toString();
		}
		return s;
	}

	@RequestMapping(value = "/4")
	@ResponseBody
	public String test4() {
		
		String s="";
		ArrayList<Enrolment> l= scs.findAllCoursesAttending();
		for (Enrolment ldetails : l){
			s=s+ldetails.toString();
		}
		return s;
	}
	
	@RequestMapping(value = "/5")
	@ResponseBody
	public String test5() {
		
		String s="";
		ArrayList<User> l= us.findAllUsers();
		for (User u : l){
			s=s+u.toString();
		}
		return s;
	}
}
