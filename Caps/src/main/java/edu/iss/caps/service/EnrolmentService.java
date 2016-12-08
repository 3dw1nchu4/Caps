package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import edu.iss.caps.model.Enrolment;

public interface EnrolmentService {

	ArrayList<Enrolment> findAllCoursesAttending();

	ArrayList<Enrolment> findbycid(int id);

	

	List<Enrolment> findstudentbylecturerid(String s);
	
	ArrayList<Enrolment> findcompleted();

	List<Enrolment> findcompletedbyid(int id);

	List<Enrolment> findungraded(String s, int id);

}
