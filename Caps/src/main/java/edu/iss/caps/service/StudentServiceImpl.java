package edu.iss.caps.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.repository.EnrolmentRepository;
import edu.iss.caps.repository.StudentDetailRepository;


@Service
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private StudentDetailRepository sdRepository;
	@Resource
	private EnrolmentRepository eRepository;

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
	
	@Override
	public float calcStudentGPA(String studentId) {
		
		ArrayList<Enrolment> courseList =eRepository.findcompletedgradesbysid(studentId);
		//Todo:correct logic
		String grade;
		int gradeNumeric=0;
		int credit=0;
		int totalCredits=0;
		float gpa = 0;
		
		for(Enrolment e: courseList){
			credit=e.getCourses().getCredits();
			totalCredits+=credit;
			grade=e.getGrade();
			gradeNumeric=getGradePt(grade);
		}
		gpa = (credit/totalCredits)*gradeNumeric;		
		return gpa;
	}

	public int getGradePt(String grade) {
		int gradePt = 0;
		if (grade.equals("A")) {
			gradePt = 5;
		}
		if (grade.equals("B") ) {
			gradePt = 4;
		}
		if (grade.equals("C")) {
			gradePt = 3;
		}
		if (grade.equals("D")) {
			gradePt = 2;
		}
		if (grade.equals("E")) {
			gradePt = 1;
		}
		if (grade.equals("B")) {
			gradePt = 0;
		}

		return gradePt;
	}


}
