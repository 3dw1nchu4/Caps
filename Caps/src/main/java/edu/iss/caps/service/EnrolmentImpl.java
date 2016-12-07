package edu.iss.caps.service;

import java.util.ArrayList;

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

}
