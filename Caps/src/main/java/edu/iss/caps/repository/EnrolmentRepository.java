package edu.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.iss.caps.model.Enrolment;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer>{

	/*@Query("SELECT sc from enrolment sc WHERE sc.studentId = :sid AND sc.courseid = :cid")
	ArrayList<Courses> findCoursesBySID(@Param("sid") String StudentID, @Param("cid") int cid);
	
	@Query("SELECT sc.grade from enrolment sc WHERE sc.studentId = :sid AND sc.courseid = :cid")
	String getGrades(@Param("sid") String StudentID, @Param("cid") int cid);
	
	@Query("insert into enrolment (studentid, courseid) VALUES (?1, ?2)")
	void enrollCourse(String studentid, int courseid);
	
	@Query("SELECT sc.credits from enrolment sc WHERE sc.studentId = :sid AND sc.courseid = :cid")
	int getEarnedCredit(@Param("sid") String sid, @Param("cid") int cid);
	
	@Modifying
	@Transactional
	@Query(value="delete from enrolment c where c.studentid = ?1 AND c.courseid = ?2")
	void deleteCoursebyStudentID(String StudentID, int courseid);*/
	
	
}
