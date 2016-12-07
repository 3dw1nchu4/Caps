package edu.iss.caps.service;

import java.util.ArrayList;

import edu.iss.caps.model.LecturerDetail;

public interface LecturerService {


	LecturerDetail findLecturerById(String lecturerId);

	ArrayList<LecturerDetail> findAllLecturers();

	LecturerDetail findLecturerByFirstName(String firstName);

	LecturerDetail createLecturer(LecturerDetail lecturer);

	LecturerDetail changeLecturer(LecturerDetail lecturer);

	void removeLecturer(LecturerDetail lecturer);
	

}
