package edu.iss.caps.service;

import java.util.ArrayList;

import edu.iss.caps.model.Course;

public interface CourseService {
	
	ArrayList<Course> findAllCourses();

	Course findCourse(Integer courseId);

	Course createCourse(Course course);

	Course changeCourse(Course course);

	void removeCourse(Course course);
	
	ArrayList<Course> findbylecid(String lectureID);
}
