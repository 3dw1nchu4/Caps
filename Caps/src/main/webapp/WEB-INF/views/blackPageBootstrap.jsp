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
<title></title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- For dropdown select -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>


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
<style>
#searchclear {
	position: absolute;
	right: 20px;
	top: 0;
	bottom: 0;
	height: 14px;
	margin: auto;
	font-size: 14px;
	cursor: pointer;
	color: #ccc;
}
</style>


</head>
<body>
	<div id="header"></div>

	<div class="container-fluid">
		<div class="row">
		<!-- For SIDE NAVIGATION -->
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li id="sidebarStudent"><a href="managestudent">Manage
							Students</a></li>
					<li id="sidebarLecturer"><a href="managelecturer">Manage
							Lecturers</a></li>
					<li id="sidebarCourse"><a href="javascript:Manage('course')">Manage
							Courses</a></li>
					<li id="sidebarEnrolment"><a
						href="javascript:Manage('enrolment')">Manage Enrolment</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>
				<!-- SECTION TITLE -->
				<h2 class="sub-header" id="sectiontitle">Section title</h2>
				<!-- Main Body -->
				<div id="mainbody" style="width: 100%">

					<div class="container" style="width: 100%">
				<!-- Search bar -->
						<nav class="navbar navbar-default" role="navigation">
						<div class="container-fluid">


							<form class="navbar-form navbar-left" role="search"
								action="searchlecturer" method="get">
								<div class="form-group">
									<label>Account Status: </label> <select name="accountstatus"
										id="accountstatus" class="selectpicker">
										<optgroup label="Account Status">
											<option value="active">Active</option>
											<option value="disabled">Disabled</option>
											<option value="all">All</option>
										</optgroup>
									</select>
								</div>

								<div class="input-group">
									<input type="text" class="form-control" placeholder="Search"
										name="searchcontent" id="searchcontent">

									<div class="input-group-btn">
										<button class="btn btn-default" type="submit">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div>
								</div>
								<button type="submit" class="btn btn-default">Search</button>
								<button type="button" class="btn btn-success"
									onclick="EditRecord('create')">
									<span class="glyphicon glyphicon-plus"></span><span>
										Create New</span>
								</button>
							</form>
						</div>
						</nav>


						<div class="container"></div>
					</div>
<!-- RESULTS DATA TABLE -->
					<div id="searchcount" name="searchcount" style="display:none"><h5> Your search returned ${dataList.size() } results</h5></div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th id="tableheader1"><h4>#</h4></th>
									<th><h4>First Name</h4></th>
									<th><h4>Last Name</h4></th>
									<th><h4>Status</h4></th>
									<th><h4></h4></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="object" items="${dataList}">
									<tr class="listRecord">
										<td>${object.lecturerId}</td>
										<td>${object.firstName}</td>
										<td>${object.lastName}</td>
										<td>${object.status}</td>
										<td><button class="btn btn-primary"
												onclick="EditRecord('${object.lecturerId}')">Edit</button>
											<button class="btn btn-danger"
												onclick="DeleteRecord('${object.lecturerId}')">Delete</button></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					
				</div>
				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="post">
						<h2 class="form-signin-heading">Edit Record</h2>
						<div style="width: 40%">
							<label for="id">Lecturer ID: </label> <input type="text" id="id"
								name="id" class="form-control" value="${data.lecturerId }"
								placeholder="Unique ID" required autofocus>
						</div>
						<div style="width: 40%">
							<label for="firstName">First Name: </label> <input type="text"
								id="firstName" name="firstName" class="form-control"
								placeholder="First Name" value="${data.firstName }" required
								autofocus>
						</div>
						<div style="width: 40%">
							<label for="lastName">Last Name: </label> <input type="text"
								id="lastName" name="lastName" class="form-control"
								placeholder="Last Name" value="${data.lastName }" required
								autofocus>
						</div>
						<div style="width: 40%">
							<label for="password">Password: </label> <input type="text"
								id="password" name="password" class="form-control"
								placeholder="User password" value="" autofocus>
						</div>
						<br> <br>
						<!-- removed the type="submit" property for testing-->
						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Records</button>
						<button class="btn btn-danger" onclick="BackToPrevious()">Cancel
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	</div>


	<!-- DISPLAY MODALS -->
	<!-- Invalid query string redirect Modal -->
	<div id="redirectModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Oops!</h4>
				</div>
				<div class="modal-body">
					<p>We couldn't find the page you were looking for. You will be
						redirected in 3 seconds.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
		$("#header").load("${pageContext.request.contextPath}/resources/header.jsp");
		$("#footer").load("${pageContext.request.contextPath}/resources/footer.html");
	});

	$(document).ready(function() {
	    $('.selectpicker').selectpicker();
	});
	
	//clears search content when clicking X
	$("#searchclear").click(function(){
	    $("#searchcontent").val('');
	});
	
	//clears search content when entering search box
	$("#searchcontent").click(function(){
	    $("#searchcontent").val('');
	});



	
</script>
</html>