<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title>Student</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>mycourses</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!--  additional custom styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/mystyle.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="${pageContext.request.contextPath}/resources/dashboard.css"
	rel="stylesheet">

</head>
<body>
	<div id="header"></div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Course/listall">View All Courses
							</a></li>
					<li id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Course/grade">Course Grades
							</a></li><li class="active" id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Course/a">Enrollment
							</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>
			
				<h2 class="sub-header" id="sectiontitle">Hello your Student ID is "${sessionScope.user.userId}"</h2>


				
<div class="container" style="width: 100%">
	<div class="row">
		<div class="col-xs-1">
			<label for="search"><h4>Search:</h4> </label>
		</div>
		<div class="col-xs-5">
			<input type="text" id="inputPK" class="form-control"
				placeholder="Search by course name or id here">
		</div>
		<div class="col xs-6">
		<a class="btn btn-success"
						href="${pageContext.request.contextPath}/Course/listall"><spring:message
								code="Search"  /></a>
		</div>
	</div>
</div>



<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="table-responsive">
	<table class="table table-striped">
		<thead>
				<tr class="listHeading">
				<th>Course Id</th>
				<th>Lecture Name</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Credit</th>
				<th>Size</th>
				<th>Current Enrollment</th>
				
				<th>Enrolling For</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="c" items="${courseavailable}">
				<tr class="listRecord">
					<td>${c.courseId}</td>
					<td>${c.lecturerDetails.firstName} ${ c.lecturerDetails.lastName}</td>
					<td>${c.startDate}</td>
					<td>${c.endDate}</td>
					<td>${c.credits}</td>
					<td>${c.size}</td>
					<td>${c.currentEnrollment}</td>
					
					
				    <td><div class="col xs-6">
		            <a class="btn btn-Danger"
						href="${pageContext.request.contextPath}/Course/enrol/${c.courseId}"><spring:message
								code="enrolling"  /></a></div></td>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

</div>
</div>
</div>

	<footer
		class="t7-container t7-dark-grey t7-padding-32 t7-padding-xlarge footer">
	<div id="footer"></div>
	</footer>

</body>
<script>
	$(function()
	{
		$("#header").load("${pageContext.request.contextPath}/resources/header.html");
		$("#footer").load("${pageContext.request.contextPath}/resources/footer.html");
	});



</script>
</html>










