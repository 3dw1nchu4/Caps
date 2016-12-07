package edu.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.iss.caps.model.StudentDetail;

public interface StudentDetailRepository extends JpaRepository<StudentDetail, String>{

}
