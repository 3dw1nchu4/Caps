<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title>Lecturer</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<div class="container" style="width: 100%">
	<div class="row">
		<div class="col-xs-1">
			<label for="search"><h4>Search:</h4> </label>
		</div>
		<div class="col-xs-5">
			<input type="text" id="inputPK" class="form-control"
				placeholder="Search for something here">
		</div>
	</div>
</div>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="table-responsive">
<c:if test="${fn:length(crlist) gt 0}">
	<table class="table table-striped" >
		<thead>
			<tr>
				<th><spring:message code="fieldLabel.courseid" /></th>
				<th><spring:message code="fieldLabel.coursename" /></th>
				<th><spring:message code="fieldLabel.lectureid" /></th>
				<th><spring:message code="fieldLabel.credits" /></th>
				<th><spring:message code="fieldLabel.startdate" /></th>
				<th><spring:message code="fieldLabel.enddate" /></th>
				<th><spring:message code="fieldLabel.size" /></th>
				<th><spring:message code="fieldLabel.cenroll" /></th>
				<th><spring:message code="fieldLabel.status" /></th>


			</tr>
		</thead>
		<tbody>
			<c:forEach var="course" items="${crlist}">
				<tr class="listRecord">
					<td>${course.courseId}</td>
					<td>${course.courseName}</td>
					<td>${course.lecturerDetails.lecturerId}</td>
					<td>${course.credits}</td>
					<td>${course.startDate}</td>
					<td>${course.endDate}</td>
					<td>${course.size}</td>
					<td>${course.currentEnrollment}</td>
					<td>${course.status}</td>
					
			
				</tr>

			</c:forEach>
		</tbody>
	</table>
</c:if>
</div>



