package edu.iss.caps.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.LecturerDetail;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.User;
import edu.iss.caps.repository.CourseRepository;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.LecturerService;
import edu.iss.caps.service.StudentService;

@Controller
@RequestMapping("/Lec")

public class Lecturercontroller {

	@Autowired
	private EnrolmentService ens;

	@Autowired
	private CourseService cs;

	@Autowired
	private StudentService sds;
	
	

	// @RequestMapping(value = "/all", method = RequestMethod.GET)
	// public ModelAndView viewtaught() {
	// ModelAndView mav = new ModelAndView("courselist");
	// List<Course> course = cs.findAllCourses();
	// mav.addObject("list", course);
	// return mav;
	// }

	// @RequestMapping(value = "/view", method = RequestMethod.GET)
	// public String home(Locale locale, Model model) {
	// return "NewFile";
	// }

//	// 1.first page to load. lec id view..
//	@RequestMapping(value = "/byid", method = RequestMethod.GET)
//	public ModelAndView viewtaughtbyid(HttpServletRequest request) {
//		User u = (User) request.getSession().getAttribute("user");
//		String s = u.getUserId();
//		ModelAndView mav = new ModelAndView("courselist");
//		List<Course> course = cs.findbylecid(s);
//		mav.addObject("list", course);
//		return mav;
//	}



	// 2.view all course to select.
	@RequestMapping(value = "/viewallenrole", method = RequestMethod.GET)
	public ModelAndView viewallcourseenrole() {
		ModelAndView mav = new ModelAndView("enroleview");
		List<Course> course = cs.findAllCourses();
		mav.addObject("Enlist", course);
		return mav;
	}

	// search courseid & course name(viewalleenrole) [2]
	@RequestMapping(value = "/2searchbyname", method = RequestMethod.GET)
	public ModelAndView searchStudent2(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {
		String searchContent = requestParams.get("searchcontent").toLowerCase();
//		User u = (User) request.getSession().getAttribute("user");
//		String s = u.getUserId();
		ModelAndView mav = new ModelAndView("enroleview");
		ArrayList<Course> lctList = cs.findAllCourses();
		List<Course> searchList = new ArrayList<Course>();
		int bn=0;String s2="";
		for (Course l : lctList) {
			bn=(l.getCourseId());
			s2=Integer.toString(bn);
			if (l.getCourseName().toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
			
			else if (s2.toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
		}

		mav.addObject("Enlist", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}
	//2.2 view all student enroled
	@RequestMapping(value = "/enrole", method = RequestMethod.GET)
	public ModelAndView employeeListPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("enrolelist");
		int id =Integer.parseInt(request.getParameter("id"));
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		List<Enrolment> enrole = ens.findbycid(id);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	//2.2 view all student enroled 
	@RequestMapping(value = "/enrole/{id}", method = RequestMethod.GET)
	public ModelAndView findonlyen(@PathVariable int id, HttpServletRequest request, @RequestParam Map<String, String> requestParams) {
		ModelAndView mav = new ModelAndView("enrolelist");
//		User u = (User) request.getSession().getAttribute("user");
//		String s = u.getUserId();
	//	List<Enrolment> enrole = ens.findbycid(id);
		ArrayList<Enrolment> lctList =  ens.findbycid(id);
	
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		if(requestParams.get("searchcontent")!= null && !requestParams.get("searchcontent").isEmpty()){
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			for (Enrolment l : lctList) {
			
			if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);}
					else			if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
					else			if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
			
			}
			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());
			return mav;
			
		
		}
		mav.addObject("Enlist", lctList);
		return mav;
	}
	
//	//2.2.1.search  by student id and name
//	@RequestMapping(value = "/22searchbyname", method = RequestMethod.GET)
//	public ModelAndView searchStudent22(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
//			HttpServletRequest request) {
//		String searchContent = requestParams.get("searchcontent").toLowerCase();
//		int id =Integer.parseInt(request.getParameter("id"));
//		ModelAndView mav = new ModelAndView("enrolelist");
//		ArrayList<Enrolment> lctList =  ens.findbycid(2);
//		List<Enrolment> searchList = new ArrayList<Enrolment>();
//	//	int bn=0;String s2="";
//		for (Enrolment l : lctList) {
////			bn=(l.getCourseId());
////			s2=Integer.toString(bn);
//		if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
//				searchList.add(l);
//		}
//		
//		else 	if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
//			searchList.add(l);
//	}
//		}
//
//		mav.addObject("Enlist", searchList);
//		mav.addObject("datacount", searchList.size());
//		return mav;
//	}
	
	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public ModelAndView searchgradeastudent(@PathVariable int id, HttpServletRequest request, @RequestParam Map<String, String> requestParams) {
		ModelAndView mav = new ModelAndView("gradinglist");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
	
//		ArrayList<Enrolment> lctList =  ens.findungraded(s, id);
		List<Enrolment> lctList = ens.findungraded(s, id);
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		if(requestParams.get("searchcontent")!= null && !requestParams.get("searchcontent").isEmpty()){
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			for (Enrolment l : lctList) {
			
			if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);}
					else			if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
					else			if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
			
			}
			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());
			return mav;
			
		
		}
		mav.addObject("Enlist", lctList);
		return mav;
	}
	
	
//
//	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
//	public ModelAndView gradeastudent(@PathVariable int id, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("gradinglist");
//		User u = (User) request.getSession().getAttribute("user");
//		String s = u.getUserId();
//		List<Enrolment> employeeList = ens.findungraded(s, id);
//		mav.addObject("Enlist", employeeList);
//		return mav;
//	}
//
//	@RequestMapping(value = "/viewsp", method = RequestMethod.GET)
//	public ModelAndView viewstudentperformance() {
//		ModelAndView mav = new ModelAndView("studentperformance");
//		List<Enrolment> course = ens.findcompleted();
//		mav.addObject("Enlist", course);
//		return mav;
//	}

	
	//4.2
	@RequestMapping(value = "/viewsp/{id}", method = RequestMethod.GET)
	public ModelAndView viewstudentbycid(@PathVariable int id, HttpServletRequest request,  @RequestParam Map<String, String> requestParams) {
		ModelAndView mav = new ModelAndView("studentperformance");
		
		List<Enrolment> lctList = ens.findcompletedbyid(id);
		
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		if(requestParams.get("searchcontent")!= null && !requestParams.get("searchcontent").isEmpty()){
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			for (Enrolment l : lctList) {
			
			if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);}
					else			if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
					else			if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
						searchList.add(l);
			}
			
			}
			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());
			return mav;
			
		
		}
		mav.addObject("Enlist", lctList);
		return mav;
	}
	
	
	//4.1 to grade
	@RequestMapping(value = "/viewallcr", method = RequestMethod.GET)
	public ModelAndView viewallcourse() {
		ModelAndView mav = new ModelAndView("courseavi");
		List<Course> course = cs.findAllCourses();
		mav.addObject("Enlist", course);
		ArrayList<Integer> ar = new ArrayList<Integer>();
	      for (Course num : course) { 		      
	        ar.add(ens.countungraded(num.getCourseId()));
	      }
		
		mav.addObject("cou", ar);
		
		return mav;
	}
	
	// search courseid & course name(viewallcr) [4]
		@RequestMapping(value = "/searchviewallcr", method = RequestMethod.GET)
		public ModelAndView searchviewallcr(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
				HttpServletRequest request) {
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			ModelAndView mav = new ModelAndView("courseavi");
			ArrayList<Course> lctList = cs.findAllCourses();
			List<Course> searchList = new ArrayList<Course>();
			int bn=0;String s2="";
			for (Course l : lctList) {
				bn=(l.getCourseId());
				s2=Integer.toString(bn);
				if (l.getCourseName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}
				
				else if (s2.toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}
			}

			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());
			return mav;
		}

	@RequestMapping(value = "/mycourse", method = RequestMethod.GET)
	public ModelAndView mycourse(HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		ModelAndView mav = new ModelAndView("courseavi");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("Enlist", course);
		ArrayList<Integer> ar = new ArrayList<Integer>();
	      for (Course num : course) { 		      
	        ar.add(ens.countungraded(num.getCourseId()));
	      }
		
		mav.addObject("cou", ar);
		
		return mav;
	}

	@RequestMapping(value = "/viewallcr/{id}", method = RequestMethod.GET)
	public ModelAndView searchin4(@PathVariable int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseavi");

		List<Course> enrole = cs.findbycid(id);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	
	//grade course show
	@RequestMapping(value = "/viewalltograde", method = RequestMethod.GET)
	public ModelAndView viewalltograde(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseforgrading");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		List<Course> course = cs.findbylecid(s);
		List<Course> courseTemp = new ArrayList<Course>();
		//List<Enrolment> enrolmentList = new ArrayList<Enrolment>();

		for(Course c : course)
		{	
			if (ens.findungraded(s, c.getCourseId()).size() !=0)
			{
				courseTemp.add(c);
			}
		}
		
		//mav.addObject("Enlist2", courseTemp);
	
		mav.addObject("Enlist", courseTemp);
		return mav;
	}
	
	
	@RequestMapping(value = "/1searchbyname", method = RequestMethod.GET)
	public ModelAndView searchStudentforgrd(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		ModelAndView mav = new ModelAndView("courseforgrading");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
//		ArrayList<Course> lctList = cs.findbylecid(s);
		List<Course> searchList = new ArrayList<Course>();
		
		List<Course> course = cs.findbylecid(s);
		List<Course> courseTemp = new ArrayList<Course>();
		

		for(Course c : course)
		{	
			if (ens.findungraded(s, c.getCourseId()).size() !=0)
			{
				courseTemp.add(c);
			}
		}
		
		
		
		
		
		int bn=0;String s2="";
		for (Course l : courseTemp) {
			bn=(l.getCourseId());
			s2=Integer.toString(bn);
			if (l.getCourseName().toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
			
			else if (s2.toLowerCase().contains(searchContent)) {
				searchList.add(l);
			}
		}

		mav.addObject("Enlist", searchList);
		mav.addObject("datacount", searchList.size());
		return mav;
	}
	
	
//	// search by course name (grade) page
//	@RequestMapping(value = "/1searchbyname", method = RequestMethod.GET)
//	public ModelAndView searchStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
//			HttpServletRequest request) {
//		String searchContent = requestParams.get("searchcontent").toLowerCase();
//		User u = (User) request.getSession().getAttribute("user");
//		String s = u.getUserId();
//		ModelAndView mav = new ModelAndView("courseforgrading");
//		ArrayList<Course> lctList = cs.findbylecid(s);
//		List<Course> searchList = new ArrayList<Course>();
//		int bn=0;String s2="";
//		for (Course l : lctList) {
//			bn=(l.getCourseId());
//			s2=Integer.toString(bn);
//			if (l.getCourseName().toLowerCase().contains(searchContent)) {
//				searchList.add(l);
//			}
//			else if (s2.toLowerCase().contains(searchContent)) {
//				searchList.add(l);
//			}
//		}
//
//		mav.addObject("Enlist", searchList);
//		mav.addObject("datacount", searchList.size());
//		return mav;
//	}

	@RequestMapping(value = "/grade/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request) {
		// ModelAndView mav = new ModelAndView("courseforgrading");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		String g = request.getParameter("glist");
		String id = request.getParameter("sd");
		int cid = Integer.parseInt(request.getParameter("couid"));
		int cred = cs.calcredit(g, cid);
		String newstatus = cs.makestatus(g);
		int enroleid = Integer.parseInt(request.getParameter("enid"));
		Enrolment en = ens.findbyEnrolmentId(enroleid);
		en.setGrade(g);
		en.setEarnedCredit(cred);
		en.setStatus(newstatus);
		ens.updateEnrolment(en);
		List<Course> course = cs.findbylecid(s);
		// mav.addObject("Enlist", course);
		return "redirect:" + cid+"?actionstatus=0";
	}

	@RequestMapping(value = "/mycourseenrole", method = RequestMethod.GET)
	public ModelAndView mycourseenrole(HttpServletRequest request) {
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		ModelAndView mav = new ModelAndView("enroleview");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("Enlist", course);
		return mav;
	}

}
