package edu.iss.caps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.iss.caps.model.Course;
import edu.iss.caps.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Resource
	private CourseRepository cr;
	
	@Override
	public ArrayList<Course> findAllCourses() {
		ArrayList<Course> l = (ArrayList<Course>) cr.findAll();
		return l;
	}

	@Override
	public Course findCourse(Integer courseId) {
		// TODO Auto-generated method stub
		return cr.findOne(courseId);
	}

	@Override
	public Course createCourse(Course course) {
		// TODO Auto-generated method stub
		return cr.saveAndFlush(course);
	}

	@Override
	public Course changeCourse(Course course) {
		// TODO Auto-generated method stub
		return cr.saveAndFlush(course);
	}

	@Override
	public void removeCourse(Course course) {
		// TODO Auto-generated method stub
		 cr.delete(course);
	}

}
