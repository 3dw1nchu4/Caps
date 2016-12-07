package edu.iss.caps.service;

import java.util.ArrayList;

import edu.iss.caps.model.StudentDetail;

public interface StudentService {
	StudentDetail findStudentById(String studentId);

	ArrayList<StudentDetail> findAllStudents();

	StudentDetail createStduent(StudentDetail student);

	StudentDetail changeStudent(StudentDetail student);

	void removeStudent(StudentDetail student);


}
