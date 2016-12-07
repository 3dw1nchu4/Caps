package edu.iss.caps.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.iss.caps.model.Enrolment;
import edu.iss.caps.repository.EnrolmentRepository;

@Service
public class EnrolmentImpl implements EnrolmentService {
	
	@Resource
	private EnrolmentRepository scRepository;


	public ArrayList<Enrolment> findAllCoursesAttending() {
		// TODO Auto-generated method stub
		ArrayList<Enrolment> scList = (ArrayList<Enrolment>)scRepository.findAll();
		return scList;
	}

	@Override
	public ArrayList<Enrolment> findbycid(int courseId , String lecid) {
		// TODO Auto-generated method stub
		return scRepository.findbycid(courseId,lecid);
	}
	
	@Override
	public ArrayList<Enrolment> findungraded() {
		// TODO Auto-generated method stub
		return scRepository.findungraded();
	}


	@Override
	public List<Enrolment> findstudentbylecturerid(String s) {
		// TODO Auto-generated method stub
		return scRepository.findstudentbylecturerid(s);
	}

	
	


}
