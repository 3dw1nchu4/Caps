package edu.iss.caps.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	@RequestMapping(value = "/managestudent", method = RequestMethod.GET)
	public ModelAndView manageStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		try
		{
			String id = requestParams.get("id").toLowerCase();
			ModelAndView mav = new ModelAndView("managestudent");
			StudentDetail student = studentService.findStudentById(id);
			
			// retrieves list of courses student is enrolled in
			ArrayList<Enrolment> enrolList = enrolmentService.findAllCoursesAttending();
			List<Enrolment> enrolList2 = new ArrayList<Enrolment>();
			for (Enrolment e : enrolList)
			{
				if (e.getStudentDetails().getStudentId().toLowerCase().contains(id))
				{
					enrolList2.add(e);
				}
			}

			// lists all courses available for student to enrol in
			ArrayList<Course> courseList = cseService.findAllCourses();
			ArrayList<Course> courseListTemp = new ArrayList<Course>();
			for (Course c : courseList)
			{
				if (c.getStatus().equals("Open"))
				{
					courseListTemp.add(c);
				}
			}
			//removes courses student is already enrolled in
			for (Enrolment e : enrolList2)
			{
				courseListTemp.remove(e.getCourses());
			}

			mav.addObject("courseavailable", courseListTemp);
			mav.addObject("enroldata", enrolList2);
			mav.addObject("data", student); //adds specific student detail to response
			return mav;
		} catch (Exception e) // catches requestParams.get("id") null pointer exception, lists all students
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
		String email = requestParams.get("emailinput");
		String dateString = requestParams.get("dateinput");
		String status = "Active";
		String role = "Student";
		
		Date enrolmentDate = ConvertToDate(dateString);

		// create user
		User user = new User(id, password, role);
		userService.createUser(user);
		
		//verifies that user is successfully created
		User userTemp = userService.findUser(id);
		String id2 = userTemp.getUserId();
		
		// create student with reference to user
		StudentDetail student = new StudentDetail(id2, firstName, lastName, enrolmentDate, status, email);
		student.setStatus("Active");
		studentService.createStduent(student);

		return "redirect:managestudent?actionstatus=createsuccess";
	}

	// Update Existing student
	@RequestMapping(value = "/updatestudent", method = RequestMethod.POST)
	public String updateStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String email = requestParams.get("emailinput");
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
		student.setEmail(email);
		studentService.changeStudent(student);

		return "redirect:managestudent?actionstatus=success";
	}

	// delete student
	@RequestMapping(value = "/deletestudent", method = RequestMethod.POST)
	public String deleteStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		StudentDetail student = studentService.findStudentById(id);
		student.setStatus("Disabled");
		studentService.changeStudent(student);
		return "redirect:managestudent?actionstatus=success";
	}

	// remove student from enrolment
	@RequestMapping(value = "/removestudentenrolment", method = RequestMethod.POST)
	public String removeStudentFromEnrolment(Locale locale, Model model,
			@RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("removethis"));
		String studentId = requestParams.get("removethisbyId");
		Enrolment enrolment = enrolmentService.findbyEnrolmentId(id);
		enrolment.setStatus("Removed");
		enrolment.setGrade("N/A");

		enrolmentService.updateEnrolment(enrolment);
		AddCourseEnrolmentCounter(enrolment.getCourses(), false);

		return "redirect:managestudent?actionstatus=success&id=" + studentId;
	}

	// add course for student
	@RequestMapping(value = "/addcoursetostudent", method = RequestMethod.GET)
	public String addCourseToStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String studentId = requestParams.get("studentId");
		String courseTitle = requestParams.get("courseId");
		String[] sArray = courseTitle.split(" -");
		int courseId = Integer.parseInt(sArray[0]);
		Course course = cseService.findCourse(courseId);

		//only allows student to be enrolled for course if course is not full
		if (course.getCurrentEnrollment() < course.getSize())
		{
			StudentDetail studentDetail = studentService.findStudentById(studentId);
			enrolmentService.createEnrollment(studentDetail, course);
			AddCourseEnrolmentCounter(course, true);
			return "redirect:managestudent?actionstatus=success&id=" + studentId;
		} else
		{
			return "redirect:managestudent?actionstatus=coursefull&id=" + studentId;
		}
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

	//// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ADMIN-LECTURER
	//// >>>>>>>>>>>>>>>>>>>>>>>>>>>/////////
	@RequestMapping(value = "/managelecturer", method = RequestMethod.GET)
	public ModelAndView manageLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		try
		{
			// adds list of lecturers to dropdown
			ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
			List<LecturerDetail> tempList = new ArrayList<LecturerDetail>();
			for (LecturerDetail l : lctList)
			{
				if (l.getStatus().toLowerCase().contains("active"))
				{
					tempList.add(l);
				}
			}

			String id = requestParams.get("id");
			ModelAndView mav = new ModelAndView("managelecturer");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);

			//adds list of "Open" courses taught by the lecturer
			ArrayList<Course> enrolList = cseService.findbylecid(id);
			ArrayList<Course> enrolList2 = new ArrayList<Course>();
			for (Course e : enrolList)
			{
				if (e.getStatus().contains("Open"))
				{
					enrolList2.add(e);
				}
			}
			mav.addObject("lecturerList", tempList);
			mav.addObject("enroldata", enrolList2);
			mav.addObject("data", lecturer); // add lecturer details to MAV
			return mav;
		} catch (Exception e) // catches requestParams.get("id") null pointer exception, lists all lecturers
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
	public String createLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
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

		return "redirect:managelecturer?actionstatus=createsuccess";
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

		return "redirect:managelecturer?actionstatus=success";
	}

	// delete lecturer
	@RequestMapping(value = "/deletelecturer", method = RequestMethod.POST)
	public String deleteLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		LecturerDetail lecturer = lecturerService.findLecturerById(id);
		lecturer.setStatus("Disabled");
		lecturerService.changeLecturer(lecturer);
		return "redirect:managelecturer?actionstatus=success";
	}

	// search lecturer
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

	// reassign course to
	@RequestMapping(value = "/reassignto", method = RequestMethod.GET)
	public String reassignCourseTo(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String lecturerId = requestParams.get("lecturerId");
		int courseId = Integer.parseInt(requestParams.get("courseId"));
		String returnTo = requestParams.get("returnTo");

		LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);
		Course course = cseService.findCourse(courseId);

		course.setLecturerDetails(lecturer);
		cseService.changeCourse(course);

		return "redirect:managelecturer?id=" + returnTo + "&actionstatus=success";
	}

	//// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ADMIN-COURSE
	//// >>>>>>>>>>>>>>>>>>>>>>>>>>>/////////
	@RequestMapping(value = "/managecourse", method = RequestMethod.GET)
	public ModelAndView manageCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		// adds list of lecturers to dropdown
		ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
		List<LecturerDetail> tempList = new ArrayList<LecturerDetail>();
		for (LecturerDetail l : lctList)
		{
			if (l.getStatus().toLowerCase().contains("active"))
			{
				tempList.add(l);
			}
		}

		try
		{
			int id = Integer.parseInt(requestParams.get("id"));
			ModelAndView mav = new ModelAndView("managecourse");
			Course course = cseService.findCourse(id);
			try
			{
				ArrayList<Enrolment> enrolmentList = enrolmentService.findbycid(id);
				mav.addObject("enrolmentList", enrolmentList);
			} catch (Exception e)
			{

			}

			mav.addObject("lecturerList", tempList);
			mav.addObject("data", course);
			return mav;
		} catch (Exception e)
		{
			ModelAndView mav = new ModelAndView("managecourse");
			ArrayList<Course> cseList = cseService.findAllCourses();
			List<Course> tempList1 = new ArrayList<Course>();
			try
			{
				for (Course c : cseList)
				{
					if (c.getStatus().toLowerCase().contains("open"))
					{
						tempList1.add(c);
					}
				}
			} catch (Exception e2)
			{

			}

			mav.addObject("lecturerList", tempList);
			mav.addObject("dataList", tempList1);
			return mav;
		}

	}

	// CREATE NEW
	@RequestMapping(value = "/createcourse", method = RequestMethod.POST)
	public String createCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String cseName = requestParams.get("cseName");
		String lecturerId = requestParams.get("lecturerId");

		Date startDate = ConvertToDate(requestParams.get("startDate"));
		Date endDate = ConvertToDate(requestParams.get("endDate"));
		
		if (endDate.compareTo(startDate) > 0)
		{
		int size = Integer.parseInt(requestParams.get("size"));
		String status = "Open";
		int currentEnrollment = 0;
		LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);

		Course course = new Course();
		course.setCourseName(cseName);
		course.setSize(size);
		course.setStatus(status);
		course.setStartDate(startDate);
		course.setEndDate(endDate);
		course.setLecturerDetails(lecturer);
		course.setCurrentEnrollment(currentEnrollment);

		cseService.changeCourse(course);
		return "redirect:managecourse?actionstatus=createsuccess";
		}
		else
		{
			return "redirect:managecourse?actionstatus=failcuzofdate&id=create";
		}
	}

	// Update Existing
	@RequestMapping(value = "/updatecourse", method = RequestMethod.POST)
	public String updateCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("id"));
		String cseName = requestParams.get("cseName");
		int size = Integer.parseInt(requestParams.get("size"));
		String status = requestParams.get("status");
		String lecturerId = requestParams.get("lecturerId");
		LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);
		Date startDate = ConvertToDate(requestParams.get("startDate"));
		Date endDate = ConvertToDate(requestParams.get("endDate"));
		
		if (endDate.compareTo(startDate) > 0)
		{
			
		Course course = cseService.findCourse(id);
		course.setCourseName(cseName);
		course.setSize(size);
		course.setStatus(status);
		course.setLecturerDetails(lecturer);
		course.setStartDate(startDate);
		course.setEndDate(endDate);

		cseService.changeCourse(course);
		return "redirect:managecourse?actionstatus=success";
		}
		else
		{
			return "redirect:managecourse?actionstatus=failcuzofdate&id="+id;
		}
	}

	// delete course
	@RequestMapping(value = "/deletecourse", method = RequestMethod.POST)
	public String deleteCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("deletethis"));
		Course course = cseService.findCourse(id);
		course.setStatus("Closed");
		cseService.changeCourse(course);
		return "redirect:managecourse?actionstatus=success";
	}

	// search course
	@RequestMapping(value = "/searchcourse", method = RequestMethod.GET)
	public ModelAndView searchCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String coursestatus = requestParams.get("accountstatus").toLowerCase();
		if (coursestatus.contains("all"))
		{
			coursestatus = "";
		}
		ModelAndView mav = new ModelAndView("managecourse");
		ArrayList<Course> lctList = cseService.findAllCourses();
		List<Course> searchList = new ArrayList<Course>();
		for (Course l : lctList)
		{
			if (Integer.toString(l.getCourseId()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getCourseName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getLecturerDetails().getLecturerId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if ((l.getLecturerDetails().getFirstName().toLowerCase() + " "
					+ l.getLecturerDetails().getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if ((l.getLecturerDetails().getLastName().toLowerCase() + " "
					+ l.getLecturerDetails().getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			}
		}

		mav.addObject("dataList", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}

	// remove student from enrolment via course
	@RequestMapping(value = "/removestudentenrolmentviacourse", method = RequestMethod.POST)
	public String removeStudentFromEnrolmentViaCourse(Locale locale, Model model,
			@RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("removethis"));
		String courseId = requestParams.get("removethisbyId");
		Enrolment enrolment = enrolmentService.findbyEnrolmentId(id);
		enrolment.setStatus("Removed");
		enrolment.setGrade("N/A");

		enrolmentService.updateEnrolment(enrolment);
		AddCourseEnrolmentCounter(enrolment.getCourses(), false);

		return "redirect:managecourse?actionstatus=success&id=" + courseId;
	}

	// To get date formatted from string (year -1900, & for some reason, month-1 is required as well
	@SuppressWarnings("deprecation")
	private static Date ConvertToDate(String dateString)
	{
		List<Integer> dateArray = new ArrayList<Integer>();

		for (String s : dateString.split("-"))
		{
			dateArray.add(Integer.parseInt(s));
		}
		Date date = new Date(dateArray.get(0) - 1900, dateArray.get(1)-1, dateArray.get(2));
		return date;
	}

	private void AddCourseEnrolmentCounter(Course c, Boolean b)
	{
		if (b)
		{
			c.setCurrentEnrollment(c.getCurrentEnrollment() + 1);
			cseService.changeCourse(c);
		} else
		{
			c.setCurrentEnrollment(c.getCurrentEnrollment() - 1);
			cseService.changeCourse(c);
		}
	}

}
