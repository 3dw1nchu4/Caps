package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import edu.iss.caps.model.Enrolment;

public interface EnrolmentService {

	ArrayList<Enrolment> findAllCoursesAttending();

	ArrayList<Enrolment> findbycid(int id, String lecid);

	ArrayList<Enrolment> findungraded();

	List<Enrolment> findstudentbylecturerid(String s);
	
	Enrolment findbyEnrolmentId(int id);
	
	Enrolment updateEnrolment(Enrolment e);

	ArrayList<Enrolment> findcompletedgradesbysid(String studentId);

	List<Enrolment> findungraded(String s, int id);

	ArrayList<Enrolment> findcompletedbyid(int cid);

	ArrayList<Enrolment> findbycid(int courseId);
	
	ArrayList<Enrolment> findCourseBySID(String sid);

}
