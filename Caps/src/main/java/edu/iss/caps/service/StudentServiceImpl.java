package edu.iss.caps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.repository.StudentDetailRepository;


@Service
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private StudentDetailRepository sdRepository;

	@Override
	public StudentDetail findStudentById(String studentId) {
		// TODO Auto-generated method stub
		return sdRepository.findOne(studentId);
	}

	@Override
	public ArrayList<StudentDetail> findAllStudents() {
		// TODO Auto-generated method stub
	    ArrayList<StudentDetail> studentList=(ArrayList<StudentDetail>)sdRepository.findAll();
		return studentList;
	}

	@Override
	public StudentDetail createStduent(StudentDetail student) {
		// TODO Auto-generated method stub
		return sdRepository.saveAndFlush(student);
	}

	@Override
	public StudentDetail changeStudent(StudentDetail student) {
		// TODO Auto-generated method stub
		return sdRepository.saveAndFlush(student);
	}

	@Override
	public void removeStudent(StudentDetail student) {
		// TODO Auto-generated method stub
		sdRepository.delete(student);
	}

}
