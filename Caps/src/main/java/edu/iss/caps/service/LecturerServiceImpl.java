package edu.iss.caps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.iss.caps.model.LecturerDetail;
import edu.iss.caps.repository.LecturerDetailRepository;

@Service
public class LecturerServiceImpl implements LecturerService{

	@Resource
	private LecturerDetailRepository ldRepository;
	
	@Override
	public LecturerDetail findLecturerById(String lecturerId) {
		// TODO Auto-generated method stub
		return ldRepository.findOne(lecturerId);
	}

	@Override
	@Transactional
	public ArrayList<LecturerDetail> findAllLecturers() {
		// TODO Auto-generated method stub
		ArrayList<LecturerDetail> lecList=(ArrayList<LecturerDetail>) ldRepository.findAll();
		return lecList;
	}

	@Override
	public LecturerDetail findLecturerByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return ldRepository.findOne(firstName);
	}

	@Override
	public LecturerDetail createLecturer(LecturerDetail lecturer) {
		// TODO Auto-generated method stub
		return ldRepository.saveAndFlush(lecturer);
	}

	@Override
	@Transactional
	public LecturerDetail changeLecturer(LecturerDetail lecturer) {
		// TODO Auto-generated method stub
		return ldRepository.saveAndFlush(lecturer);
	}

	@Override
	public void removeLecturer(LecturerDetail lecturer) {
		// TODO Auto-generated method stub
		ldRepository.delete(lecturer);
	}

}
