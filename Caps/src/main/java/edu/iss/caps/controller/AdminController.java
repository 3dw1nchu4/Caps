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
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.LecturerService;
import edu.iss.caps.service.UserService;


@RequestMapping("/admin")
@Controller
public class AdminController
{
//Edwin
	
	@Autowired
	CourseService cseService;
	@Autowired
	LecturerService lecturerService;
	@Autowired
	UserService userService;

	
///////////////  ADMIN-STUDENT /////////////////////////
	@RequestMapping(value = "/managestudent", method = RequestMethod.GET)
	public ModelAndView manageStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
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
	@RequestMapping(value = "/createstudent", method = RequestMethod.POST)
	public ModelAndView createStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String role = "Lecturer";
			
			User user = new User(id, password, role);
			userService.createUser(user);
			
			User userTemp = userService.findUser(id);
			id = userTemp.getUserId();
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturerService.createLecturer(lecturer);
			ModelAndView mav = new ModelAndView("managelecturer");
			
			LecturerDetail lecturer1 = lecturerService.findLecturerById(id);
			mav.addObject("data", lecturer1);
			return mav;
	}
	//Update Existing
	@RequestMapping(value = "/updatestudent", method = RequestMethod.POST)
	public String updateStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String role = "Lecturer";
			
			if(password.length() > 1) //If password not keyed in, no update
			{
			User user = new User(id, password, role);
			userService.changeUser(user);
			}
			User userTemp = userService.findUser(id);
			id = userTemp.getUserId();
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturerService.changeLecturer(lecturer);

			return "redirect:managelecturer";
	}
	//delete lecturer
	//problem with foreign key constraints - KIV
	@RequestMapping(value = "/deletestudent", method = RequestMethod.POST)
	public String deleteStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
			String id = requestParams.get("deletethis");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			lecturerService.removeLecturer(lecturer);
			
			User user = userService.findUser(id);
			userService.removeUser(user);
			
			return "redirect:managelecturer";
	}
	
	@RequestMapping(value = "/searchstudent", method = RequestMethod.POST)
	public String searchStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
			String id = requestParams.get("deletethis");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			lecturerService.removeLecturer(lecturer);
			
			User user = userService.findUser(id);
			userService.removeUser(user);
			
			return "redirect:managelecturer";
	}
///////////////  ADMIN-LECTURER /////////////////////////
	@RequestMapping(value = "/managelecturer", method = RequestMethod.GET)
	public ModelAndView manageLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
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
	@RequestMapping(value = "/createlecturer", method = RequestMethod.POST)
	public ModelAndView createLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String role = "Lecturer";
			
			User user = new User(id, password, role);
			userService.createUser(user);
			
			User userTemp = userService.findUser(id);
			id = userTemp.getUserId();
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturerService.createLecturer(lecturer);
			ModelAndView mav = new ModelAndView("managelecturer");
			
			LecturerDetail lecturer1 = lecturerService.findLecturerById(id);
			mav.addObject("data", lecturer1);
			return mav;
	}
	//Update Existing
	@RequestMapping(value = "/updatelecturer", method = RequestMethod.POST)
	public String updateLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
			String id = requestParams.get("id");
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String role = "Lecturer";
			
			if(password.length() > 1) //If password not keyed in, no update
			{
			User user = new User(id, password, role);
			userService.changeUser(user);
			}
			User userTemp = userService.findUser(id);
			id = userTemp.getUserId();
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturerService.changeLecturer(lecturer);

			return "redirect:managelecturer";
	}
	//delete lecturer
	//problem with foreign key constraints - KIV
	@RequestMapping(value = "/deletelecturer", method = RequestMethod.POST)
	public String deleteLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
			String id = requestParams.get("deletethis");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			lecturerService.removeLecturer(lecturer);
			
			User user = userService.findUser(id);
			userService.removeUser(user);
			
			return "redirect:managelecturer";
	}
	@RequestMapping(value = "/searchlecturer", method = RequestMethod.GET)
	public ModelAndView searchLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		ModelAndView mav = new ModelAndView("managelecturer");
		ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
		List<LecturerDetail> searchList = new ArrayList<LecturerDetail>();
		for(LecturerDetail l : lctList)
		{
			if (l.getFirstName().toLowerCase().contains(searchContent))
			{
				searchList.add(l);
			}
			else if (l.getLastName().toLowerCase().contains(searchContent))
			{
				searchList.add(l);
			}
			else if (l.getLecturerId().toLowerCase().contains(searchContent))
			{
				searchList.add(l);
			}
			else if ((l.getFirstName().toLowerCase() + " " + l.getLastName().toLowerCase()).contains(searchContent))
			{
				searchList.add(l);
			}
			else if ((l.getLastName().toLowerCase() + " " + l.getFirstName().toLowerCase()).contains(searchContent))
			{
				searchList.add(l);
			}
			
		}
		
		mav.addObject("dataList", searchList);
		return mav;
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
