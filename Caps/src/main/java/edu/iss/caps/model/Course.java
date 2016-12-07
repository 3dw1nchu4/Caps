package edu.iss.caps.model;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Courses")

public class Course {
	@Id
	@Column(name = "CourseId")
	private int courseId;
	@Basic(optional = false)
	@Column(name = "CourseName")
	private String courseName;
	@Column(name = "Credits")
	private int credits;

	@Column(name = "StartDate")
	private Date startDate;
	@Column(name="EndDate")
	private Date endDate;
	@Column(name = "CurrentEnrollment")
	private int currentEnrollment;
	@Column(name="Size")
	private int size;
	//sandi

	@OneToOne
    @JoinColumn(name="LecturerId") 
	private LecturerDetail lecturerDetails;

	public Course() {

	}

	

	public Course(int courseId, String courseName, int credits, Date startDate, Date endDate, int currentEnrollment,
			int size, LecturerDetail lecturerDetails) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.credits = credits;
		this.startDate = startDate;
		this.endDate = endDate;
		this.currentEnrollment = currentEnrollment;
		this.size = size;
		this.lecturerDetails = lecturerDetails;
	}



	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getCurrentEnrollment() {
		return currentEnrollment;
	}

	public void setCurrentEnrollment(int currentEnrollment) {
		this.currentEnrollment = currentEnrollment;
	}

	public LecturerDetail getLecturerDetails() {
		return lecturerDetails;
	}

	public void setLecturerDetails(LecturerDetail lecturerDetails) {
		this.lecturerDetails = lecturerDetails;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
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
		Course other = (Course) obj;
		if (courseId != other.courseId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", credits=" + credits + ", startDate="
				+ startDate + ", endDate=" + endDate + ", currentEnrollment=" + currentEnrollment + ", size=" + size
				+ ", lecturerDetails=" + lecturerDetails.toString() + "]";
	}
	
}
