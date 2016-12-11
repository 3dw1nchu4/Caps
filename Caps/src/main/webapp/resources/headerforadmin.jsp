<!--Navigation bar mobile responsive-->

<%@page import="edu.iss.caps.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>


</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home/index">CAPS</a>
			</li>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">

			<ul class="nav navbar-nav">
				
				<li id="navbarAdmin" class="dropdown" style="display:none"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Admin
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/admin/managestudent">Manage Students</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/managelecturer">Manage
								Lecturers</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/managecourse">Manage Courses</a></li>
					</ul></li>
				<li id="navbarStudent" class="dropdown" style="display:none"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Student
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/Course/listall">View All Courses</a></li>
						<li><a href="${pageContext.request.contextPath}/Course/grade">Course Grades</a></li>
						<li><a href="${pageContext.request.contextPath}/Course/a">Enrollment</a></li>
					</ul></li>
				<li id="navbarLecturer" class="dropdown" style="display:none"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Lecturer
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/Lec/viewallenrole">View Course
								Enrolment</a></li>
						<li><a href="${pageContext.request.contextPath}/Lec/viewalltograde">Grade a Course</a></li>
						<li><a href="${pageContext.request.contextPath}/Lec/viewallcr">View a Student
								Performance</a></li>
					</ul></li>
			</ul> 
			<ul class="nav navbar-nav navbar-right">
				<!--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>-->
				 <li id="logedinuser"><a href="#" style="max-width: 100%"><span
						class="glyphicon glyphicon-user"></span>
				<% User u = (User) request.getSession().getAttribute("user") ; %>
				<%= u.getUserId() %>
					
						
						</a></li>
				<!--<li id="navbarLogin"><a href="login.html" data-toggle="login"
					data-placement="auto" title="Login" style="max-width: 100%"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li> -->
				<li id="navbarLogout"><a href="${pageContext.request.contextPath}/home/logout"
					data-toggle="logout" data-placement="auto" title="Logout"
					style="max-width: 100%"><span
						class="glyphicon glyphicon-log-out"></span> Log out</a></li>
			</ul>
		</div>
	</div>
</nav>
<br>
</body>


<script>
	var url = window.location.pathname
	console.log(url);
	
	 if (url.includes("/caps/admin/"))
	{
		document.getElementById("navbarAdmin").style.display = "block";
	} else if (url.includes("/caps/Course/"))
	{
		document.getElementById("navbarStudent").style.display = "block";

	} else if (url.includes("/caps/Lec/"))
	{
		document.getElementById("navbarLecturer").style.display = "block";
	} 



</script> 

</html>