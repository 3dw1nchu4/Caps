package edu.iss.caps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseService;
//import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.StudentService;



@RequestMapping(value="/Course")
@Controller
public class StudentController {

	@Autowired
	private CourseService cService;
	@Autowired
	private StudentService sService;
	@Autowired
	private EnrolmentService eService;
// 	@Autowired
//	private DepartmentValidator dValidator;
	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView newEmployeePage() {
//		ModelAndView mav = new ModelAndView("employee-new", "employee", new ());
//		mav.addObject("eidlist", cService.findAllEmployeeIDs());
//		return mav;
//	}
	@RequestMapping(value="/sec",method = RequestMethod.GET)
	public String testMestod(HttpServletRequest request){
	    User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
	    
	    return "sec";
	  }
//	
//	@SuppressWarnings("null")

	@RequestMapping(value = "/a",method = RequestMethod.GET)
	public ModelAndView AvailablePage1(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("course-available");
		try {

		List<Course> courselist = cService.findAllCourses();
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();

		StudentDetail studentDetail =sService.findStudentById(s);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
		List<Enrolment> grades = eService.findCourseBySID(s);
		
		
			for (Enrolment enrolment : grades) {
				courselist.remove(enrolment.getCourses());
			}
		
		
		mav.addObject("courseavailable", courselist);
		} catch (Exception e) {

		}
		return mav;
	}
	
///////////////////////////listall courses
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/listall/{type}", "/listall" }, method = RequestMethod.GET)
	public ModelAndView all(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest request) {
		try {
		PagedListHolder<Course> courseList = null;

		String type = pathVariablesMap.get("type");
		
		User u = (User) request.getSession().getAttribute("user");
		String ss = u.getUserId();
		StudentDetail studentDetail =sService.findStudentById(ss);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
	
/////////////////////////
		if (null == type) {
			List<Course> clist = cService.findAllCourses();
			courseList = new PagedListHolder<Course>();
			courseList.setSource(clist);
			courseList.setPageSize(3);
			// First Request, Return first set of list
			
			request.getSession().setAttribute("courseList", courseList);

			printPageDetails(courseList);

		} else if ("next".equals(type)) {
			// Return next set of list
			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			courseList.nextPage();

			printPageDetails(courseList);

		} else if ("prev".equals(type)) {
			// Return previous set of list
			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			courseList.previousPage();

			printPageDetails(courseList);

		} else {
			// Return specific index set of list
			System.out.println("type:" + type);

			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			int pageNum = Integer.parseInt(type);

			courseList.setPage(pageNum);

			printPageDetails(courseList);
		}	

		} catch (Exception e) {

		}

		ModelAndView mav = new ModelAndView("list-all");
		return mav;
	}
	
	@RequestMapping(value = "/grade",method = RequestMethod.GET)
	public ModelAndView studentviewgrade(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("list-grade");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		List<Enrolment> grades = eService.findCourseBySID(s);/////////joe changed in eservice
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		StudentDetail studentDetail =sService.findStudentById(s);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
		
		float gpa = sService.calcStudentGPA(s);
		
		String k= String.format("%.2f", gpa) ;


		
		
		
		request.getSession().setAttribute("GPA",k);
		
		for (Enrolment enrolment : grades) {
			if (enrolment.getCourses().getStatus()=="Closed") {
				grades.remove(enrolment);
			}
		}
		
		mav.addObject("grlist", grades);
		return mav;
	}
	
	
	
	@RequestMapping(value = "/enrol/{courseId}", method = RequestMethod.GET)
	public ModelAndView enrolling(@ModelAttribute Course course, BindingResult result,HttpServletRequest request,
			@PathVariable int courseId, final RedirectAttributes redirectAttributes,HttpServletResponse response) throws ServletException, IOException {

		
		   Course c = cService.findCourse(courseId);
		   String message ="";
		
		   

		if (c.getSize()>c.getCurrentEnrollment()) {
			User u = (User) request.getSession().getAttribute("user");
			String s = u.getUserId();
		    StudentDetail studentDetail =sService.findStudentById(s);
  		    eService.createEnrollment(studentDetail, c);/////////need to check in eservice
  		    cService.increasecourseEnrolment(c);/////////need to check in cservice
  		    Course cs = cService.findCourse(courseId);
  		    
		    message = "You have sucessfully enrolled with " + cs.getCourseName() + "+. We have sent you an email, please check it. ";
			redirectAttributes.addFlashAttribute("message", message);
			
			request.setAttribute("course", c);
			request.setAttribute("student", studentDetail);
			String redir=request.getContextPath();
			RequestDispatcher rd = request.getRequestDispatcher("/email");
			rd.include(request, response);

			
			
		}else {
			 Course cs = cService.findCourse(courseId);
		    message =  cs.getCourseName() + " course is already full.";
			redirectAttributes.addFlashAttribute("message", message);		
			
		}
		request.getSession().setAttribute("message", message);
		
//		Session session = SetAttribute("message", );
		ModelAndView mav = new ModelAndView("redirect:/Course/bar");

		return mav;
	}
	@RequestMapping(value = "/bar",method = RequestMethod.GET)
	public ModelAndView bar(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("bar");
		String m=(String)request.getSession().getAttribute("message");
	
		
		mav.addObject("message", m);
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/listallsearchbyname/{type}", "/listallsearchbyname" }, method = RequestMethod.GET)
	public ModelAndView searchStudentforgrd(Locale locale, Model model,@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest request, @RequestParam Map<String, String> requestParams) {

	
//	@RequestMapping(value = "/listallsearchbyname", method = RequestMethod.GET)
//	public ModelAndView searchStudentforgrd(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
//			HttpServletRequest request) {
		String searchContent = requestParams.get("searchcontent").toLowerCase();
	
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		// ArrayList<Course> lctList = cs.findbylecid(s);
		List<Course> searchList = new ArrayList<Course>();

		String type = pathVariablesMap.get("type");
		PagedListHolder<Course> courseList = null;
		StudentDetail studentDetail =sService.findStudentById(s);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
		
/////////////////////////
		
		if (null == type) {
			List<Course> clist = cService.findAllCourses();
			
			/////////////////
			
			
			int bn = 0;
			String s2 = "";
			for (Course l : clist) {
				bn = (l.getCourseId());
				s2 = Integer.toString(bn);
				if (l.getCourseName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}

				else if (s2.toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}
			}
			///////////////////
			
			
			courseList = new PagedListHolder<Course>();
			courseList.setSource(searchList);
			courseList.setPageSize(3);
			// First Request, Return first set of list
			
			request.getSession().setAttribute("courseList", courseList);

			printPageDetails(courseList);

		} else if ("next".equals(type)) {
			// Return next set of list
			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			courseList.nextPage();

			printPageDetails(courseList);

		} else if ("prev".equals(type)) {
			// Return previous set of list
			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			courseList.previousPage();

			printPageDetails(courseList);

		} else {
			// Return specific index set of list
			System.out.println("type:" + type);

			courseList = (PagedListHolder<Course>) request.getSession().getAttribute("courseList");

			int pageNum = Integer.parseInt(type);

			courseList.setPage(pageNum);

			printPageDetails(courseList);
		}
		
		
	//////////////////////////////////	
		

//		int bn = 0;
//		String s2 = "";
//		for (Course l : course) {
//			bn = (l.getCourseId());
//			s2 = Integer.toString(bn);
//			if (l.getCourseName().toLowerCase().contains(searchContent)) {
//				searchList.add(l);
//			}
//
//			else if (s2.toLowerCase().contains(searchContent)) {
//				searchList.add(l);
//			}
//		}

		ModelAndView mav = new ModelAndView("list-all");
		mav.addObject("courselist", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}
	
	@RequestMapping(value = "/listgradesearchbyname", method = RequestMethod.GET)
	public ModelAndView searchStudentforgr(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		ModelAndView mav = new ModelAndView("list-grade");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		// ArrayList<Course> lctList = cs.findbylecid(s);
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		List<Enrolment> grades = eService.findCourseBySID(s);/////////joe changed in eservice
		

		StudentDetail studentDetail =sService.findStudentById(s);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
		
		for (Enrolment enrolment : grades) {
			if (enrolment.getCourses().getStatus()=="Closed") {
				grades.remove(enrolment);
			}
		}
//		List<Course> courseTemp = new ArrayList<Course>();
//		
//
//		for (Course c : course) {
//			if (eService.findungraded(s, c.getCourseId()).size() != 0) {
//				courseTemp.add(c);
//			}
//		}

		int bn = 0;
		String s2 = "";
		for (Enrolment l : grades) {
			bn = (l.getCourses().getCourseId());
			s2 = Integer.toString(bn);
			if (l.getCourses().getCourseName().toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}

			else if (s2.toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
		}

		mav.addObject("grlist", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}
	
	@RequestMapping(value = "/asearch", method = RequestMethod.GET)
	public ModelAndView searchStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		ModelAndView mav = new ModelAndView("course-available");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		
		StudentDetail studentDetail =sService.findStudentById(s);
		String sname = studentDetail.getFirstName();
		request.setAttribute("student", sname);
		
		List<Course> searchList = new ArrayList<Course>();
		List<Course> courselist = cService.findAllCourses();

//		List<Course> courseTemp = new ArrayList<Course>();
//		
//
//		for (Course c : course) {
//			if (eService.findungraded(s, c.getCourseId()).size() != 0) {
//				courseTemp.add(c);
//			}
//		}

//		ModelAndView mav = new ModelAndView("course-available");
//		List<Course> courselist = cService.findAllCourses();

		List<Enrolment> grades = eService.findCourseBySID(s);
		
		
			for (Enrolment enrolment : grades) {
				courselist.remove(enrolment.getCourses());
			}
		
//		
//		mav.addObject("courseavailable", courselist);
//		return mav;
		
		int bn = 0;
		String s2 = "";
		for (Course l : courselist) {
			bn = (l.getCourseId());
			s2 = Integer.toString(bn);
			if (l.getCourseName().toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}

			else if (s2.toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
		}

 		mav.addObject("courseavailable", searchList);
	    mav.addObject("datacount", searchList.size());
		return mav;
	}
	public void printPageDetails(PagedListHolder LList) {

		System.out.println("curent page - productList.getPage() :" + LList.getPage());

		System.out.println("Total Num of pages - productList.getPageCount :" + LList.getPageCount());

		System.out.println("is First page - productList.isFirstPage :" + LList.isFirstPage());

		System.out.println("is Last page - productList.isLastPage :" + LList.isLastPage());
	}
	
		
}
