package edu.iss.caps.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.LecturerDetail;

import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.LecturerService;


@RequestMapping("/admin")
@Controller
public class AdminController
{
//Edwin
	
	@Autowired
	CourseService cseService;
	@Autowired
	LecturerService lecturerService;

	@RequestMapping(value = "/manage", params = "manage=student", method = RequestMethod.GET)
	public ModelAndView manage(Locale locale, Model model)
	{
		ModelAndView mav = new ModelAndView("managestudent");
		List<Course> cseList = cseService.findAllCourses();
		mav.addObject("dataList", cseList);
		return mav;
	}
///////////////  ADMIN-LECTURER /////////////////////////
	@RequestMapping(value = "/manage", params = "manage=lecturer", method = RequestMethod.GET)
	public ModelAndView manage2(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		try
		{
			String id = requestParams.get("id");
			ModelAndView mav = new ModelAndView("managelecturer");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			mav.addObject("data", lecturer);
			return mav;
		} catch (Exception e)
		{
			ModelAndView mav = new ModelAndView("managelecturer");
			List<LecturerDetail> lctList = lecturerService.findAllLecturers();
			mav.addObject("dataList", lctList);
			return mav;
		}
	}
	//CREATE NEW
	//Error - need to add User Service
	@RequestMapping(value = "/createLecturer", method = RequestMethod.GET)
	public ModelAndView createLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturerService.createLecturer(lecturer);
			ModelAndView mav = new ModelAndView("managelecturer");
			LecturerDetail lecturer1 = lecturerService.findLecturerById(id);
			mav.addObject("data", lecturer1);
			return mav;
	}
	//Update Existing
	@RequestMapping(value = "/updateLecturer", method = RequestMethod.GET)
	public String updateLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		    
			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			lecturer.setFirstName(firstName);
			lecturer.setLastName(lastName);
			
			lecturerService.changeLecturer(lecturer);
			

			return "redirect:manage?manage=lecturer";
	}
	
///////////////  ADMIN-COURSE /////////////////////////
	@RequestMapping(value = "/manage", params = "manage=course", method = RequestMethod.GET)
	public ModelAndView manage3(Locale locale, Model model)
	{
		ModelAndView mav = new ModelAndView("managecourse");
		List<Course> cseList = cseService.findAllCourses();
		mav.addObject("dataList", cseList);
		return mav;
	}

	@RequestMapping(value = "/manage", params = "manage=enrolment", method = RequestMethod.GET)
	public ModelAndView manage4(Locale locale, Model model)
	{
		ModelAndView mav = new ModelAndView("manageenrolment");
		List<Course> cseList = cseService.findAllCourses();
		mav.addObject("dataList", cseList);
		return mav;
	}
}
