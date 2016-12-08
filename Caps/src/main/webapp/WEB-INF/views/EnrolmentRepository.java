package edu.iss.caps.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer>{

	@Query("SELECT e from Enrolment e WHERE e.studentDetails.studentId = :sid")
	ArrayList<Enrolment> findCourseBySID(@Param("sid") String StudentID);
	
	
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
	
	@Query("SELECT sc from Enrolment sc WHERE sc.courses.lecturerDetails.lecturerId = :lid " )
	ArrayList<Enrolment> findstudentbylecturerid(@Param("lid") String lid);
	
	@Query("SELECT sc from Enrolment sc WHERE sc.courses.courseId = :sid AND sc.courses.lecturerDetails.lecturerId = :lid")
	ArrayList<Enrolment> findbycid(@Param("sid") int courseId,@Param("lid") String LecId );
	
	@Query("SELECT w FROM Enrolment w WHERE w.grade is null")
	ArrayList<Enrolment> findungraded();
	
	
}
