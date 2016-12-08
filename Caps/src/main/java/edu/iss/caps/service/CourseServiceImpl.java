package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public ArrayList<Course> findbylecid(String lectureID) {
		// TODO Auto-generated method stub
		return cr.findbylecid(lectureID);
	}

	@Override
	public List<Course> findbycid(int id) {
		// TODO Auto-generated method stub
		return cr.findbycid(id);
	}

	
	
	@Override
	public int calcredit(String g, int cid) {
		// TODO Auto-generated method stub
		
		int actualc= 8;
		if(g.equals("F"))
			return 0;
			else
				return actualc;
	}

	@Override
	public String makestatus(String g) {
		// TODO Auto-generated method stub
		
		String sta="Passed";
		if(g.equals("F"))
			sta="Failed";
		
		return sta;
	}






}
