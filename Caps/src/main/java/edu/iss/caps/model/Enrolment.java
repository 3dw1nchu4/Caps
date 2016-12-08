package edu.iss.caps.model;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import edu.iss.caps.model.*;
import edu.iss.caps.service.StudentService;

@Entity
@Table(name = "Enrolments")

public class Enrolment {

	@Id
	@Column(name = "EnrolmentId")
	private int enrolmentId;
	@Basic(optional = false)
	@Column(name = "Grade")
	private String grade;
	@Column(name = "EarnedCredit")
	private Integer earnedCredit;
	@Column(name="Status")
	private String status;

	@OneToOne
    @JoinColumn(name="CourseId") 
	Course courses;
	
	@OneToOne 
	@JoinColumn(name="StudentId") 
	StudentDetail studentDetails;
	
	public Enrolment(){
		
	}

	


	public Enrolment(int enrolmentId, String grade, Integer earnedCredit, String status, Course courses,
			StudentDetail studentDetails) {
		super();
		this.enrolmentId = enrolmentId;
		this.grade = grade;
		this.earnedCredit = earnedCredit;
		this.status = status;
		this.courses = courses;
		this.studentDetails = studentDetails;
	}




	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getEarnedCredit() {
		return earnedCredit;
	}

	public void setEarnedCredit(Integer earnedCredit) {
		this.earnedCredit = earnedCredit;
	}

	public Course getCourses() {
		return courses;
	}

	public void setCourses(Course courses) {
		this.courses = courses;
	}

	public StudentDetail getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetail studentDetails) {
		this.studentDetails = studentDetails;
	}

	
	
	public int getEnrolmentId() {
		return enrolmentId;
	}




	public void setEnrolmentId(int enrolmentId) {
		this.enrolmentId = enrolmentId;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}





	




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + enrolmentId;
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrolment other = (Enrolment) obj;
		if (enrolmentId != other.enrolmentId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Enrolment [enrolmentId=" + enrolmentId + ", grade=" + grade + ", earnedCredit=" + earnedCredit
				+ ", status=" + status + ", courses=" + courses + ", studentDetails=" + studentDetails.toString() + "]";
	}
		

	
}
