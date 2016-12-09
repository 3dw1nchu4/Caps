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
		float totalCredits=getTotalCredits(courseList);
		float gpa = 0;
		
		for(Enrolment e: courseList){
			float credit=e.getCourses().getCredits();
			String grade=e.getGrade();
			float gradeNumeric=getGradePt(grade);
			gpa= gpa+credit/totalCredits*gradeNumeric;
		}	
		return gpa;
	}
	
	private float getTotalCredits(ArrayList<Enrolment> el){
		float total=0;
		for(Enrolment e:el){
			total=total+e.getCourses().getCredits();
		}
		return total;
	}

	private int getGradePt(String grade) {
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
		if (grade.equals("F")) {
			gradePt = 0;
		}

		return gradePt;
	}


}
