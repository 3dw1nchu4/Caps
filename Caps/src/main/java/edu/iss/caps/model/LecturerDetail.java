	package edu.iss.caps.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LecturerDetails")
public class LecturerDetail {

	@Id
	@Column(name = "LecturerId")
	private String lecturerId;
	@Basic(optional = false)
	@Column(name = "FirstName")
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	@Column(name="Status")
	private String status;

//	@OneToMany
//	List<Courses> course = new ArrayList<Courses>();


	public LecturerDetail() {

	}

	public LecturerDetail(String lecturerId, String firstName, String lastName) {
		super();
		this.lecturerId = lecturerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		result = prime * result + ((lecturerId == null) ? 0 : lecturerId.hashCode());
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
		LecturerDetail other = (LecturerDetail) obj;
		if (lecturerId == null) {
			if (other.lecturerId != null)
				return false;
		} else if (!lecturerId.equals(other.lecturerId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LecturerDetail [lecturerId=" + lecturerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", status=" + status + "]";
	}

}
