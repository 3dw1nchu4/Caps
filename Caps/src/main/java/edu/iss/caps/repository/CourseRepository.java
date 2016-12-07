package edu.iss.caps.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import edu.iss.caps.model.Course;

public interface CourseRepository extends JpaRepository <Course, Integer>{

}
