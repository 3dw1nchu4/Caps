package edu.iss.caps.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	@Query("SELECT sc from Enrolment sc WHERE sc.courses.lecturerDetails.lecturerId = :lid " )
	ArrayList<Enrolment> findstudentbylecturerid(@Param("lid") String lid);
	
	@Query("SELECT sc from Enrolment sc WHERE sc.courses.courseId = :sid ")
	ArrayList<Enrolment> findbycid(@Param("sid") int courseId );
	
	@Query("SELECT w FROM Enrolment w WHERE w.grade is null AND( w.courses.lecturerDetails.lecturerId = :lid AND w.courses.courseId = :cid)")
	ArrayList<Enrolment> findungraded(@Param("lid") String LecId,@Param("cid") int id);
	
	@Query("SELECT w FROM Enrolment w WHERE w.grade is not null")
	ArrayList<Enrolment> findcompleted();

	@Query("SELECT w FROM Enrolment w WHERE w.grade is not null AND w.courses.courseId = :lid ")
	List<Enrolment> findcompletedbyid(@Param("lid") int cid);


	@Query("SELECT sc from Enrolment sc WHERE sc.courses.courseId = :sid AND sc.courses.lecturerDetails.lecturerId = :lid")
	ArrayList<Enrolment> findbycid(@Param("sid") int courseId,@Param("lid") String LecId );
	
	
	
	@Query("select count(courseId) from Enrolment w where w.grade is not null AND w.courses.courseId = :sid ")
	int countungraded (@Param("sid") int courseId);
	
	
	
	
	
}
