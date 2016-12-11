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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.exception.FailedAuthentication;
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

	// 1.view all course to select enrole details
		@SuppressWarnings("unchecked")
		@RequestMapping(value = { "/viewallenrole/{type}", "/viewallenrole" }, method = RequestMethod.GET)
		public ModelAndView all(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {

			PagedListHolder<Course> courseList = null;

			String type = pathVariablesMap.get("type");

			if (null == type) {
				// First Request, Return first set of list
				List<Course> phonesList = cs.findAllCourses();

				courseList = new PagedListHolder<Course>();
				courseList.setSource(phonesList);
				courseList.setPageSize(2);

				req.getSession().setAttribute("courseList", courseList);

				printPageDetails(courseList);

			} else if ("next".equals(type)) {
				// Return next set of list
				courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				courseList.nextPage();

				printPageDetails(courseList);

			} else if ("prev".equals(type)) {
				// Return previous set of list
				courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				courseList.previousPage();

				printPageDetails(courseList);

			} else {
				// Return specific index set of list
				System.out.println("type:" + type);

				courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				int pageNum = Integer.parseInt(type);

				courseList.setPage(pageNum);

				printPageDetails(courseList);
			}

			ModelAndView mv = new ModelAndView("enroleview");
			String s = "all";
			mv.addObject("Error", s);
			return mv;
		}

		public void printPageDetails(PagedListHolder LList) {

			System.out.println("curent page - productList.getPage() :" + LList.getPage());

			System.out.println("Total Num of pages - productList.getPageCount :" + LList.getPageCount());

			System.out.println("is First page - productList.isFirstPage :" + LList.isFirstPage());

			System.out.println("is Last page - productList.isLastPage :" + LList.isLastPage());
		}

	// 1.[search] search courseid & course name(viewalleenrole)
		@RequestMapping(value = { "/2searchbyname/{type}", "/2searchbyname" }, method = RequestMethod.GET)
		public ModelAndView searchStudent2(Locale locale, Model model, @PathVariable Map<String, String> pathVariablesMap,
				@RequestParam Map<String, String> requestParams, HttpServletRequest req) {

			List<Course> searchList = new ArrayList<Course>();

			String searchContent = requestParams.get("searchcontent").toLowerCase();
			System.out.println("I am for form:" + searchContent);
			ArrayList<Course> lctList = cs.findAllCourses();

			String s = null;
			int bn = 0;
			String s2 = "";
			for (Course l : lctList) {
				bn = (l.getCourseId());
				s2 = Integer.toString(bn);
				if (l.getCourseName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
					System.out.println(l);
				} else if (s2.toLowerCase().contains(searchContent)) {
					System.out.println(l);
					searchList.add(l);
				}
			}
			if (searchList.size() <= 0) {
				s = "No SRhearch Result ";

			} else {
				PagedListHolder<Course> courseList = null;

				String type = pathVariablesMap.get("type");

				if (null == type) {
					// First Request, Return first set of list
					List<Course> phonesList = searchList;

					courseList = new PagedListHolder<Course>();
					courseList.setSource(phonesList);
					courseList.setPageSize(3);

					req.getSession().setAttribute("courseList", courseList);

					printPageDetails(courseList);

				} else if ("next".equals(type)) {
					// Return next set of list
					courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

					courseList.nextPage();

					printPageDetails(courseList);

				} else if ("prev".equals(type)) {
					// Return previous set of list
					courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

					courseList.previousPage();

					printPageDetails(courseList);

				} else {
					// Return specific index set of list
					System.out.println("type:" + type);

					courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

					int pageNum = Integer.parseInt(type);

					courseList.setPage(pageNum);

					printPageDetails(courseList);
				}

			}

			ModelAndView mv = new ModelAndView("enroleview");
			mv.addObject("Error", s);
			return mv;

		}
		// 1.1 view my course only.
		@SuppressWarnings("unchecked")
		@RequestMapping(value = { "/mycourseenrole/{type}", "/mycourseenrole" }, method = RequestMethod.GET)
		public ModelAndView mycourseenrole(@PathVariable Map<String, String> pathVariablesMap, HttpServletRequest req) {

			User u = (User) req.getSession().getAttribute("user");
			String s = u.getUserId();

			PagedListHolder<Course> couList = null;

			String type = pathVariablesMap.get("type");

			if (null == type) {
				// First Request, Return first set of list

				List<Course> cList = cs.findbylecid(s);
				couList = new PagedListHolder<Course>();
				couList.setSource(cList);
				couList.setPageSize(3);

				req.getSession().setAttribute("courseList", couList);

				printPageDetails(couList);

			} else if ("next".equals(type)) {
				// Return next set of list
				couList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				couList.nextPage();

				printPageDetails(couList);

			} else if ("prev".equals(type)) {
				// Return previous set of list
				couList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				couList.previousPage();

				printPageDetails(couList);

			} else {
				// Return specific index set of list
				System.out.println("type:" + type);

				couList = (PagedListHolder<Course>) req.getSession().getAttribute("courseList");

				int pageNum = Integer.parseInt(type);

				couList.setPage(pageNum);

				printPageDetails(couList);
			}

			ModelAndView mv = new ModelAndView("enroleview");
			
			return mv;
		}

	// 1.2 view all student enroled / with search
	@RequestMapping(value = "/enrole/{id}", method = RequestMethod.GET)
	public ModelAndView findonlyen(@PathVariable int id, HttpServletRequest request,
			@RequestParam Map<String, String> requestParams) {
		ModelAndView mav = new ModelAndView("enrolelist");
		ArrayList<Enrolment> lctList = ens.findbycid(id);
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		if (requestParams.get("searchcontent") != null && !requestParams.get("searchcontent").isEmpty()) {
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			for (Enrolment l : lctList) {

				if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
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

	// 2.1 grade course - show course to select
	@RequestMapping(value = "/viewalltograde", method = RequestMethod.GET)
	public ModelAndView viewalltograde(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseforgrading");
		try {
			User u = (User) request.getSession().getAttribute("user");
			String s = u.getUserId();
			List<Course> course = cs.findbylecid(s);
			List<Course> courseTemp = new ArrayList<Course>();
			// List<Enrolment> enrolmentList = new ArrayList<Enrolment>();

			for (Course c : course) {
				if (ens.findungraded(s, c.getCourseId()).size() != 0) {
					courseTemp.add(c);
				}
			}
			mav.addObject("Enlist", courseTemp);
		} catch (Exception e) {

		}
		return mav;

	}

	// search in 2.1 [for course name and id]
	@RequestMapping(value = "/1searchbyname", method = RequestMethod.GET)
	public ModelAndView searchStudentforgrd(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("courseforgrading");
		try {
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			User u = (User) request.getSession().getAttribute("user");

			String s = u.getUserId();
			// ArrayList<Course> lctList = cs.findbylecid(s);
			List<Course> searchList = new ArrayList<Course>();

			List<Course> course = cs.findbylecid(s);
			List<Course> courseTemp = new ArrayList<Course>();

			for (Course c : course) {
				if (ens.findungraded(s, c.getCourseId()).size() != 0) {
					courseTemp.add(c);
				}
			}

			int bn = 0;
			String s2 = "";
			for (Course l : courseTemp) {
				bn = (l.getCourseId());
				s2 = Integer.toString(bn);
				if (l.getCourseName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}

				else if (s2.toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}
			}

			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());

		} catch (Exception e) {

		}
		return mav;
	}

	// 2.2 grade a student
	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public ModelAndView searchgradeastudent(@PathVariable int id, HttpServletRequest request,
			@RequestParam Map<String, String> requestParams) {
		ModelAndView mav = new ModelAndView("gradinglist");
		String searchContent = null;
		String s = null;
		try {
			User u = (User) request.getSession().getAttribute("user");
			s = u.getUserId();
			searchContent = requestParams.get("searchcontent").toLowerCase();
		} catch (Exception e) {

		}
		List<Enrolment> lctList = ens.findungraded(s, id);
		List<Enrolment> searchList = new ArrayList<Enrolment>();
		if (requestParams.get("searchcontent") != null && !requestParams.get("searchcontent").isEmpty()) {

			for (Enrolment l : lctList) {

				if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
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

	// 3.2 view all student performance / search for all students.
	//-----------------------------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = { "/viewsp/{id}/{type}", "/viewsp/{id}" }, method = RequestMethod.GET)
	public ModelAndView viewstudentbycid(@PathVariable int id, HttpServletRequest req,
			@RequestParam Map<String, String> requestParams,@PathVariable Map<String, String> pathVariablesMap) {
		

		PagedListHolder<Enrolment> studList = null;
		String type = pathVariablesMap.get("type");	

		if (requestParams.get("searchcontent") != null && !requestParams.get("searchcontent").isEmpty()) {
			ModelAndView mav = new ModelAndView("studentperformancebyid");
			List<Enrolment> lctList = ens.findcompletedbyid(id);
			List<Enrolment> searchList = new ArrayList<Enrolment>();
			String searchContent = requestParams.get("searchcontent").toLowerCase();
			for (Enrolment l : lctList) {

				if (l.getStudentDetails().getFirstName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getLastName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				} else if (l.getStudentDetails().getStudentId().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}

			}
			mav.addObject("Enlist", searchList);
			mav.addObject("datacount", searchList.size());
			ArrayList<String> gpa = new ArrayList<String>();
			System.out.println(lctList.size());
			for (Enrolment en : lctList) {
				float f = sds.calcStudentGPA(en.getStudentDetails().getStudentId());
				String ss = String.format("%.2f%n", f);
				gpa.add(ss);
			}
			mav.addObject("Stgpa", gpa);

			return mav;

		}
		else {
			if (null == type) {
										
				List<Enrolment>sList =ens.findcompletedbyid(id);
				studList = new PagedListHolder<Enrolment>();
				studList.setSource(sList);
				studList.setPageSize(3);

				req.getSession().setAttribute("studList", studList);

				printPageDetails(studList);

			} else if ("next".equals(type)) {
				// Return next set of list
				studList = (PagedListHolder<Enrolment>) req.getSession().getAttribute("studList");

				studList.nextPage();

				printPageDetails(studList);

			} else if ("prev".equals(type)) {
				// Return previous set of list
				studList = (PagedListHolder<Enrolment>) req.getSession().getAttribute("studList");

				studList.previousPage();

				printPageDetails(studList);

			} else {
				// Return specific index set of list
				System.out.println("type:" + type);

				studList = (PagedListHolder<Enrolment>) req.getSession().getAttribute("studList");

				int pageNum = Integer.parseInt(type);

				studList.setPage(pageNum);

				printPageDetails(studList);
			}
			
			ArrayList<String> gpa = new ArrayList<String>();
			List<Enrolment> lctList = ens.findcompletedbyid(id);
			for (Enrolment en : lctList) {
				float f = sds.calcStudentGPA(en.getStudentDetails().getStudentId());
				String ss = String.format("%.2f%n", f);
				gpa.add(ss);
			}
			 
			ModelAndView mv = new ModelAndView("studentperformance");
			mv.addObject("ID",id);
			mv.addObject("Stgpa", gpa);
			return mv;
			
			}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------
	// 3.1 to view performance
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

	// search courseid & course name(viewallcr) [3]
	@RequestMapping(value = "/searchviewallcr", method = RequestMethod.GET)
	public ModelAndView searchviewallcr(Locale locale, Model model, @RequestParam Map<String, String> requestParams,
			HttpServletRequest request) {
		String searchContent = null;
		List<Course> searchList = null;
		ModelAndView mav = null;
		try {
			searchContent = requestParams.get("searchcontent").toLowerCase();
			mav = new ModelAndView("courseavi");
			ArrayList<Course> lctList = cs.findAllCourses();
			searchList = new ArrayList<Course>();
			int bn = 0;
			String s2 = "";
			for (Course l : lctList) {
				bn = (l.getCourseId());
				s2 = Integer.toString(bn);
				if (l.getCourseName().toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}

				else if (s2.toLowerCase().contains(searchContent)) {
					searchList.add(l);
				}
			}

			mav.addObject("Enlist", searchList);
			ArrayList<Integer> ar = new ArrayList<Integer>();
			for (Course num : searchList) {
				ar.add(ens.countungraded(num.getCourseId()));
			}
			mav.addObject("cou", ar);

			mav.addObject("datacount", searchList.size());
		} catch (Exception e) {
		}
		
		return mav;
	}

	// 3.1 view my course for viewing performance
	@RequestMapping(value = "/mycourse", method = RequestMethod.GET)
	public ModelAndView mycourse(HttpServletRequest request) {
		ModelAndView mav =null;
		try {
			User u = (User) request.getSession().getAttribute("user");
			String s = u.getUserId();
			mav = new ModelAndView("courseavi");
			List<Course> course = cs.findbylecid(s);
			mav.addObject("Enlist", course);
			ArrayList<Integer> ar = new ArrayList<Integer>();
			for (Course num : course) {
				ar.add(ens.countungraded(num.getCourseId()));
			}

			mav.addObject("cou", ar);

			
		} catch (Exception e) {
			
		}
		return mav;
	}

	// update the grade.. (2.2)
	@RequestMapping(value = "/grade/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request) {
		int cid =0;
		try {
			User u = (User) request.getSession().getAttribute("user");
			String s = u.getUserId();
			String g = request.getParameter("glist");
			String id = request.getParameter("sd");
			 cid = Integer.parseInt(request.getParameter("couid"));
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
			
		} catch (Exception e) {
			
		}
		return "redirect:" + cid + "?actionstatus=0";
	}

}
