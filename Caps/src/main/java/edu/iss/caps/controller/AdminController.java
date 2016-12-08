package edu.iss.caps.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.UserService;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.service.EnrolmentService;;

@RequestMapping("/admin")
@Controller
public class AdminController
{
	// Edwin

	@Autowired
	CourseService cseService;
	@Autowired
	LecturerService lecturerService;
	@Autowired
	UserService userService;
	@Autowired
	StudentService studentService;
	@Autowired
	EnrolmentService enrolmentService;
/*
	@RequestMapping(value = "/")
	public String testMestod(HttpSession session)
	{
		try
		{
			User u = (User) session.getAttribute("user");
			
			if (u.getRole().equals("Admin"))
			{
				return "redirect:managestudent";
			}
			else
			{
				return "redirect:www.google.com";
			}
		}
		catch (Exception e)
		{
			return "redirect:www.google.com";
		}
		
		
	}*/

	////<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ADMIN-STUDENT >>>>>>>>>>>>>>>>>>>>>>>>>>>/////////
	@RequestMapping(value = "/managestudent", method = RequestMethod.GET)
	public ModelAndView manageStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		try
		{
			String id = requestParams.get("id").toLowerCase();
			ModelAndView mav = new ModelAndView("managestudent");
			StudentDetail student = studentService.findStudentById(id);

			ArrayList<Enrolment> enrolList = enrolmentService.findAllCoursesAttending();
			List<Enrolment> enrolList2 = new ArrayList<Enrolment>();
			for (Enrolment e : enrolList)
			{
				if (e.getStudentDetails().getStudentId().toLowerCase().contains(id))
				{
					enrolList2.add(e);

				}
			}

			mav.addObject("enroldata", enrolList2);
			mav.addObject("data", student);
			return mav;
		} catch (Exception e)
		{
			ModelAndView mav = new ModelAndView("managestudent");
			ArrayList<StudentDetail> lctList = studentService.findAllStudents();
			List<StudentDetail> tempList = new ArrayList<StudentDetail>();
			for (StudentDetail l : lctList)
			{
				if (l.getStatus().toLowerCase().contains("active"))
				{
					tempList.add(l);
				}
			}

			mav.addObject("dataList", tempList);
			return mav;
		}
	}

	// CREATE NEW
	@RequestMapping(value = "/createstudent", method = RequestMethod.POST)
	public String createStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String role = "Student";

		User user = new User(id, password, role);
		userService.createUser(user);

		User userTemp = userService.findUser(id);
		id = userTemp.getUserId();

		// for testing purposes
		@SuppressWarnings("deprecation")
		Date d = new Date(2012, 10, 20);

		StudentDetail student = new StudentDetail(id, firstName, lastName, d);
		student.setStatus("Active");
		studentService.createStduent(student);

		return "redirect:managestudent?create=success";
	}

	// Update Existing student
	@RequestMapping(value = "/updatestudent", method = RequestMethod.POST)
	public String updateStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String role = "Student";

		if (password.length() > 1) // If password not keyed in, no update
		{
			User user = new User(id, password, role);
			userService.changeUser(user);
		}
		User userTemp = userService.findUser(id);
		id = userTemp.getUserId();

		StudentDetail student = studentService.findStudentById(id);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		studentService.changeStudent(student);

		return "redirect:managestudent";
	}

	// delete student
	@RequestMapping(value = "/deletestudent", method = RequestMethod.POST)
	public String deleteStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		StudentDetail student = studentService.findStudentById(id);
		student.setStatus("Disabled");
		studentService.changeStudent(student);
		return "redirect:managestudent";
	}

	// remove student from enrolment
	@RequestMapping(value = "/removestudentenrolment", method = RequestMethod.POST)
	public String removeStudentFromEnrolment(Locale locale, Model model,
			@RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("removethis"));
		Enrolment enrolment = enrolmentService.findbyEnrolmentId(id);
		enrolment.setStatus("Removed");
		enrolment.setGrade("N/A");
		enrolmentService.updateEnrolment(enrolment);
		return "redirect:managestudent";
	}

	// search student
	@RequestMapping(value = "/searchstudent", method = RequestMethod.GET)
	public ModelAndView searchStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String accountstatus = requestParams.get("accountstatus").toLowerCase();
		if (accountstatus.contains("all"))
		{
			accountstatus = "";
		}
		ModelAndView mav = new ModelAndView("managestudent");
		ArrayList<StudentDetail> lctList = studentService.findAllStudents();
		List<StudentDetail> searchList = new ArrayList<StudentDetail>();
		for (StudentDetail l : lctList)
		{
			if (l.getFirstName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLastName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getStudentId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getFirstName().toLowerCase() + " " + l.getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getLastName().toLowerCase() + " " + l.getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			}
		}

		mav.addObject("dataList", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}

	////<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ADMIN-LECTURER >>>>>>>>>>>>>>>>>>>>>>>>>>>/////////
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
			ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
			List<LecturerDetail> tempList = new ArrayList<LecturerDetail>();
			for (LecturerDetail l : lctList)
			{
				if (l.getStatus().toLowerCase().contains("active"))
				{
					tempList.add(l);
				}
			}

			mav.addObject("dataList", tempList);
			return mav;
		}
	}

	// CREATE NEW
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
		lecturer.setStatus("Active");
		lecturerService.createLecturer(lecturer);
		ModelAndView mav = new ModelAndView("managelecturer");

		LecturerDetail lecturer1 = lecturerService.findLecturerById(id);
		mav.addObject("data", lecturer1);
		return mav;
	}

	// Update Existing
	@RequestMapping(value = "/updatelecturer", method = RequestMethod.POST)
	public String updateLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String role = "Lecturer";

		if (password.length() > 1) // If password not keyed in, no update
		{
			User user = new User(id, password, role);
			userService.changeUser(user);
		}
		User userTemp = userService.findUser(id);
		id = userTemp.getUserId();

		LecturerDetail lecturer = lecturerService.findLecturerById(id);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		lecturerService.changeLecturer(lecturer);

		return "redirect:managelecturer";
	}

	// delete lecturer
	@RequestMapping(value = "/deletelecturer", method = RequestMethod.POST)
	public String deleteLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		LecturerDetail lecturer = lecturerService.findLecturerById(id);
		lecturer.setStatus("Disabled");
		lecturerService.changeLecturer(lecturer);
		return "redirect:managelecturer";
	}

	@RequestMapping(value = "/searchlecturer", method = RequestMethod.GET)
	public ModelAndView searchLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String accountstatus = requestParams.get("accountstatus").toLowerCase();
		if (accountstatus.contains("all"))
		{
			accountstatus = "";
		}
		ModelAndView mav = new ModelAndView("managelecturer");
		ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
		List<LecturerDetail> searchList = new ArrayList<LecturerDetail>();
		for (LecturerDetail l : lctList)
		{
			if (l.getFirstName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLastName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLecturerId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getFirstName().toLowerCase() + " " + l.getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getLastName().toLowerCase() + " " + l.getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			}
		}

		mav.addObject("dataList", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}

////<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ADMIN-COURSE >>>>>>>>>>>>>>>>>>>>>>>>>>>/////////
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
