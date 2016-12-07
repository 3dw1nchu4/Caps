package edu.iss.caps.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.iss.caps.model.Course;

public interface CourseRepository extends JpaRepository <Course, Integer>{

	@Query("SELECT e FROM Course e where e.lecturerDetails.lecturerId = :id")
	ArrayList<Course> findbylecid(@Param("id") String id);


}
