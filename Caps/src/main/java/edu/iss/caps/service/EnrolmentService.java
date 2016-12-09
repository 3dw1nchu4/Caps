package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.StudentDetail;

public interface EnrolmentService {

	ArrayList<Enrolment> findAllCoursesAttending();

	ArrayList<Enrolment> findbycid(int id, String lecid);



	List<Enrolment> findstudentbylecturerid(String s);
	
	Enrolment findbyEnrolmentId(int id);
	
	Enrolment updateEnrolment(Enrolment e);

	List<Enrolment> findungraded(String s, int id);


	ArrayList<Enrolment> findcompletedbyid(int cid);

	ArrayList<Enrolment> findbycid(int courseId);
	
	int countungraded(int cid);

	List<Enrolment> findCourseBySID(String s);


	void createEnrollment(StudentDetail studentDetail, Course c);

	ArrayList<Enrolment> findcompletedgradesbysid(String studentId);

	ArrayList<Enrolment> findcompleted(int id);
	

}
