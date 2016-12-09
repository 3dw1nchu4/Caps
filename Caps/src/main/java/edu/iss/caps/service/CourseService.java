package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import edu.iss.caps.model.Course;

public interface CourseService {
	
	ArrayList<Course> findAllCourses();

	Course findCourse(Integer courseId);

	Course createCourse(Course course);

	Course changeCourse(Course course);

	void removeCourse(Course course);
	
	ArrayList<Course> findbylecid(String lectureID);

	ArrayList<Course> findbynotcop(String studentId);

	List<Course> findbycid(int id);



	int calcredit(String g, int cid);

	String makestatus(String g);

	void increasecourseEnrolment(Course c);
	

//	String makestatus(int cred);


}
