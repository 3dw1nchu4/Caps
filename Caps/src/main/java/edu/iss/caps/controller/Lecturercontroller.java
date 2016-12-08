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
import edu.iss.caps.model.StudentDetail;
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

	@RequestMapping(value = "/sec", method = RequestMethod.GET)
	public String testMestod(HttpServletRequest request) {
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
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		ModelAndView mav = new ModelAndView("courselist");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("list", course);
		return mav;
	}

	@RequestMapping(value = "/enrole", method = RequestMethod.GET)
	public ModelAndView employeeListPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("enrolelist");
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		List<Enrolment> enrole = ens.findstudentbylecturerid(s);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	@RequestMapping(value = "/enrole/{id}", method = RequestMethod.GET)
	public ModelAndView findonlyen(@PathVariable int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("enrolelist");
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		List<Enrolment> enrole = ens.findbycid(id);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public ModelAndView gradeastudent(@PathVariable int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("gradinglist");
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		List<Enrolment> employeeList = ens.findungraded(s, id);
		mav.addObject("Enlist", employeeList);
		return mav;
	}

	@RequestMapping(value = "/viewsp", method = RequestMethod.GET)
	public ModelAndView viewstudentperformance() {
		ModelAndView mav = new ModelAndView("studentperformance");
		List<Enrolment> course = ens.findcompleted();
		mav.addObject("Enlist", course);
		return mav;
	}

	@RequestMapping(value = "/viewsp/{id}", method = RequestMethod.GET)
	public ModelAndView viewstudentbycid(@PathVariable int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("studentperformance");
		List<Enrolment> enrole = ens.findcompletedbyid(id);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	@RequestMapping(value = "/viewallcr", method = RequestMethod.GET)
	public ModelAndView viewallcourse() {
		ModelAndView mav = new ModelAndView("courseavi");
		List<Course> course = cs.findAllCourses();
		mav.addObject("Enlist", course);
		return mav;
	}

	@RequestMapping(value = "/mycourse", method = RequestMethod.GET)
	public ModelAndView mycourse(HttpServletRequest request) {
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		ModelAndView mav = new ModelAndView("courseavi");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("Enlist", course);
		return mav;
	}

	@RequestMapping(value = "/viewallcr/{id}", method = RequestMethod.GET)
	public ModelAndView searchin4(@PathVariable int id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseavi");

		List<Course> enrole = cs.findbycid(id);
		mav.addObject("Enlist", enrole);
		return mav;
	}

	@RequestMapping(value = "/viewalltograde", method = RequestMethod.GET)
	public ModelAndView viewalltograde(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseforgrading");
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		List<Course> course = cs.findbylecid(s);
		mav.addObject("Enlist", course);
		return mav;
	}

	@RequestMapping(value = "/grade/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseforgrading");
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		String g = request.getParameter("glist");
		String id = request.getParameter("sd");
		int cid = Integer.parseInt(request.getParameter("couid"));
		System.out.println(
				"dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
						+ request.getParameter("glist"));
		int cred = cs.calcredit(g, cid);
		String newstatus = cs.makestatus(g);
		String enroleid = request.getParameter("enid");
		System.out.println(id + "-" + g + "-" + s + "--" + cid + "*****" + cred + "---------" + newstatus+"8888"+enroleid);
//		Enrolment en = ens.
		// List<Course> course = cs.findbylecid(s);
		// mav.addObject("Enlist", course);
		return null;
	}

	@RequestMapping(value = "/viewallenrole", method = RequestMethod.GET)
	public ModelAndView viewallcourseenrole() {
		ModelAndView mav = new ModelAndView("enroleview");
		List<Course> course = cs.findAllCourses();
		mav.addObject("Enlist", course);
		return mav;
	}

	@RequestMapping(value = "/mycourseenrole", method = RequestMethod.GET)
	public ModelAndView mycourseenrole(HttpServletRequest request) {
		Object lecid = request.getSession().getAttribute("name");
		String s = (String) lecid;
		ModelAndView mav = new ModelAndView("enroleview");
		List<Course> course = cs.findbylecid(s);
		mav.addObject("Enlist", course);
		return mav;
	}

}
