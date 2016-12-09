package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.repository.CourseRepository;
import edu.iss.caps.repository.EnrolmentRepository;

@Service
public class EnrolmentImpl implements EnrolmentService {
	
	@Resource
	private EnrolmentRepository scRepository;
	private CourseRepository courseRepository;


	public ArrayList<Enrolment> findAllCoursesAttending() {
		// TODO Auto-generated method stub
		ArrayList<Enrolment> scList = (ArrayList<Enrolment>)scRepository.findAll();
		return scList;
	}

	@Override
	public ArrayList<Enrolment> findbycid(int courseId) {
		// TODO Auto-generated method stub
		return scRepository.findbycid(courseId);
	}
	
	
	@Override
	public ArrayList<Enrolment> findbycid(int courseId , String lecid) {
		// TODO Auto-generated method stub
		return scRepository.findbycid(courseId,lecid);
	}
	


//	@Override
//	public ArrayList<Enrolment> findcompleted(int id) {
//		// TODO Auto-generated method stub
//		return scRepository.findcompleted(id);
//	}
//	
	@Override
	public List<Enrolment> findstudentbylecturerid(String s) {
		// TODO Auto-generated method stub
		return scRepository.findstudentbylecturerid(s);
	}
	

	@Override
	public Enrolment findbyEnrolmentId(int id)
	{
		return scRepository.getOne(id);
	}

	@Override
	public Enrolment updateEnrolment(Enrolment e)
	{
		return scRepository.saveAndFlush(e);
	}


	@Override
	public ArrayList<Enrolment> findcompletedbyid(int id) {
		// TODO Auto-generated method stub
		return scRepository.findcompletedbyid(id);
	}


	@Override
	public List<Enrolment> findungraded(String s, int id) {
		// TODO Auto-generated method stub
		return scRepository.findungraded(s, id);
	}

	@Override
	public int countungraded(int cid) {
		// TODO Auto-generated method stub
		return scRepository.countungraded(cid);
	}

	
	//joe////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<Enrolment> findCourseBySID(String sid){
		return scRepository.findCourseBySID(sid);
	}

////////////////////////////////////////joe//////////////////////////////////////
	@Override
	public void createEnrollment(StudentDetail studentDetail, Course course) {
	Enrolment e = new Enrolment();
	e.setStudentDetails(studentDetail);
	e.setStatus("Enrolled");
//	int a = course.getCurrentEnrollment();
//	int ce=a+1;
//	course.setCurrentEnrollment(ce);
//	 courseRepository.saveAndFlush(course);

	
	     e.setCourses(course);
	     
		 scRepository.saveAndFlush(e);
//		
		
	}
	@Override
	public ArrayList<Enrolment> findcompletedgradesbysid(String studentId) {
		// TODO Auto-generated method stub
		return scRepository.findcompletedgradesbysid(studentId);
	}


	


}
