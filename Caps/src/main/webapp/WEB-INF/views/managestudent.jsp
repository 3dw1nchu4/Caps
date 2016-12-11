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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<!-- For dropdown select -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>



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
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li id="sidebarStudent" class="active"><a href="managestudent">Manage
							Students</a></li>
					<li id="sidebarLecturer"><a href="managelecturer">Manage
							Lecturers</a></li>
					<li id="sidebarCourse"><a href="managecourse">Manage
							Courses</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>

				<h2 class="sub-header" id="sectiontitle">Manage Student Records</h2>

				<div id="mainbody" style="width: 100%">

					<div class="container" style="width: 100%">

						<nav class="navbar navbar-default" role="navigation">
						<div class="container-fluid">
							<!--  div class="navbar-header">
								<a class="navbar-brand" href="#">Search</a>
							</div>-->

							<form class="navbar-form navbar-left" role="search"
								action="searchstudent" method="get">
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

					<div id="searchcount" name="searchcount" style="display: none">
						<h5>Your search returned ${dataList.size() } results</h5>
					</div>
					<table class="table table-striped">
						<thead>
							<tr>
								<th><h4>Student ID</h4></th>
								<th><h4>Name</h4></th>
								<th><h4>Enrolment Date</h4></th>
								<th><h4>Status</h4></th>
								<th><h4></h4></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="object" items="${dataList}">
								<tr class="listRecord">
									<td>${object.studentId}</td>
									<td>${object.lastName}, ${object.firstName}</td>
									<td>${object.enrolmentDate}</td>
									<td>${object.status}</td>
									<td><button class="btn btn-primary"
											onclick="EditRecord('${object.studentId}')">Edit</button>
										<button class="btn btn-danger"
											onclick="DeleteRecord('${object.studentId}')">Disable</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>

				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="POST">
						<h3 class="form-signin-heading">Edit Record</h3>
						<div class="row">
							<div class = "col-lg-4 col-xs-12">
								<label for="id"><h4>Student ID: </h4></label> <input type="text" id="id"
								name="id" class="form-control" value="${data.studentId }"
								placeholder="Unique ID (eg. S1234)" required autofocus>
							</div>
							<div class = "col-lg-4 col-xs-12">
								<label for="emailinput"><h4>Email: </h4></label> <input type="email"
								id="emailinput" name="emailinput" class="form-control"
								placeholder="Email" value="${data.email }" required autofocus>
							</div>
						</div>
						<div class="row"><br></div>
						<div class="row">
							<div class = "col-lg-4 col-xs-12">
							<label for="firstName"><h4>First Name: </h4></label> <input type="text"
								id="firstName" name="firstName" class="form-control"
								placeholder="First Name" value="${data.firstName }" required
								autofocus pattern="[A-Za-z ]{3,}" title="Only uppercase and lowercase alphabets">
							</div>
							<div class = "col-lg-3 col-xs-8">
							<label for="dateinput"><h4>Enrolment Date: </h4></label> <input
								type="text" id="dateinput" name="dateinput" class="form-control"
								placeholder="Enrolment Date (YYYY-MM-DD)"
								value="${data.enrolmentDate }" required
								pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
								title="Date format (YYYY-MM-DD)" autofocus>
							</div>
							<div class = "col-lg-1 col-xs-4">
							<label ><br><br> </label>
								<button type="button" class="btn btn-default" onclick="DateToday()">Today</button>
							</div>
						</div>
						<div class="row"><br></div>
						<div class="row">
						<div class = "col-lg-4 col-xs-12">
							<label for="lastName"><h4>Last Name: </h4></label> <input type="text"
								id="lastName" name="lastName" class="form-control"
								placeholder="Last Name" value="${data.lastName }" required
								autofocus pattern="[A-Za-z ]{3,}" title="Only uppercase and lowercase alphabets">
						</div>
						</div>
						<div class="row"><br></div>
						<div class="row">
							<div class = "col-lg-4 col-xs-12">
								<label for="password"><h4>Password: </h4></label> <input type="password"
									id="password" name="password" class="form-control"
									placeholder="User password (Required for new accounts/ Optional when updating)" value="" autofocus> <br>
							</div>
						</div>
						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Details</button>
						<button type="button" class="btn btn-danger"
							onclick="BackToPrevious()">Cancel</button>
					</form>

					<div class="row" id="studentcourses" name="studentcourses">
						<br>

						<div class="jumbotron col-sm-7 col-xs-12">
							<h3>
								<b><u>${data.lastName }, ${data.firstName }</u></b> is enrolled
								in ${enroldata.size() } courses
							</h3>
							<table class="table table-striped">

								<thead>
									<tr>
										<th><h4>Course Name</h4><h6>(Click to view course details)</h6></th>
										<th><h4>Status</h4><br></th>
										<th><h4>Grade</h4><br></th>
										<th><h4>Earned Credits</h4><br></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="enrol" items="${enroldata}">
										<tr class="listRecord">
											<td><a href="managecourse?id=${enrol.courses.courseId}">${enrol.courses.courseName}</a></td>
											<td>${enrol.status}</td>
											<td>${enrol.grade}</td>
											<td>${enrol.earnedCredit}</td>
											<td><button type="button" class="btn btn-info"
													onclick="RemoveEnrolment('${enrol.enrolmentId}')"
													<c:if test="${enrol.status.contains('Passed') || enrol.status.contains('Failed') || enrol.status.contains('Removed')}"> disabled
													</c:if>>
													Remove from Course</button>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col-sm-1"></div>
						<div class="jumbotron col-sm-4 col-xs-12" style="background-color:darkgrey">
							<h3>Add a course:</h3>
							<div>
								<select id="addstudentpicker" name="addstudentpicker"
									class="selectpicker show-tick form-control"
									data-live-search="true">
									<option></option>
									<c:forEach var="course" items="${courseavailable}">
										<option>${course.courseId } - ${course.courseName }</option>
									</c:forEach>
								</select>
							</div>
							<div>
								<br> <br>
								<button class="btn btn-warning" type="button"
									onclick="AddCourse('${data.studentId}')">Add Course</button>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>



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

	<!-- Successful transaction Modal -->
	<div id="successActionModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Success</h4>
				</div>
				<div class="modal-body" id="successModalMessage"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

	<!-- Not logged in Modal -->

	<div id="redirectLoginModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Login required</h4>
				</div>
				<div class="modal-body">
					<p>The page you tried to access requires you to be logged in.
						You will be redirected to the login page.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	<!-- Confirm deletion Modal -->
	<div id="deleteModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Confirm Inactivation of Student
						Account</h4>
				</div>
				<form action="deletestudent" method="post">
					<div class="modal-body">

						<p>The selected student account will be inactivated.</p>
						<input id="deletethis" name="deletethis" class="form-control"
							placeholder="Last Name" value="" required type="hidden" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button id="deletebtn" name="deletebtn" type="submit"
							class="btn btn-danger">Confirm</button>
					</div>
				</form>

			</div>

		</div>
	</div>

	<!-- Confirm Remove from Enrolment Modal -->
	<div id="removeEnrolmentModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Confirm Deletion</h4>
				</div>
				<form action="removestudentenrolment" method="post">
					<div class="modal-body">

						<p>The selected entry will be permanently deleted.</p>
						<input id="removethis" name="removethis" class="form-control"
							value="" required type="hidden" /> <input id="removethisbyId"
							name="removethisbyId" class="form-control" value="" required
							type="hidden" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button id="deletebtn" name="deletebtn" type="submit"
							class="btn btn-danger">Delete</button>
					</div>
				</form>
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
			$("#header").load("${pageContext.request.contextPath}/resources/headerforadmin.jsp");
			$("#footer").load("${pageContext.request.contextPath}/resources/footer.html");
		});




	$(document).ready(function() {
	    $('.selectpicker').selectpicker();
	    
	});
	
	//clears search content when entering search box
	$("#searchcontent").click(function(){
	    $("#searchcontent").val('');
	});
	
	

	var url = window.location.href;

	try
	{
		var qs = (function(a)
		{
			if (a == "")
				return
				{};
			var b =
			{};
			for (var i = 0; i < a.length; ++i)
			{
				var p = a[i].split('=', 2);
				if (p.length == 1)
					b[p[0]] = "";
				else
					b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
			}
			return b;
		})(window.location.search.substr(1).split('&'));
	}
	catch (err)
	{
		console.log("no qs");
	}
	
	try
	{
		if (qs['searchcontent'] != null)
		{
			document.getElementById("searchcontent").value = qs['searchcontent'];
			document.getElementById("searchcount").style.display = "block";
		}
	}
	catch (err)
	{
		console.log("no search content");
	}
	
	try
	{
		if (qs['accountstatus'] != null)
		{
			document.getElementById("accountstatus").value = qs['accountstatus'];
		}
	}
	catch (err)
	{
		console.log("no search content");
	}
	
	
	if (url.includes("id="))
	{
		document.getElementById("mainbody").style.display = "none";
		document.getElementById("editcontent").style.display = "block";
		
		if (qs['id'] !="create")
		{
			document.getElementById("id").readOnly = true;
			document.getElementById("formEditRecord").action = "updatestudent";
			document.getElementById("submitbutton").innerHTML = "Update Record";			
		} else
		{
			document.getElementById("formEditRecord").action = "createstudent";
			document.getElementById("submitbutton").innerHTML = "Create New Record";
			document.getElementById("studentcourses").style.display = "none";
			document.getElementById("password").required = true;
		}
	}

	function EditRecord(id) //Edit button
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managestudent?id="+id;
	}
	
	function BackToPrevious()
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managestudent";
	}
	
	function RedirectToLogin()
	{
		$('#redirectLoginModal').modal('toggle');
		window.setTimeout(function()
		{
			window.location = "login.jsp";
		}, 3000);
	}
	
	function DeleteRecord(id)
	{
		//Attaches correct delete event to button
		console.log("event attached");
		document.getElementById("deletethis").value = id;
		$('#deleteModal').modal('toggle');
	}
	

			if (qs['actionstatus'].includes("success"))
			{
					document.getElementById("successModalMessage").innerHTML = "Record successfully updated!";
					$('#successActionModal').modal('toggle');
			}
		
			if (qs['actionstatus'] == "createsuccess")
			{
					document.getElementById("successModalMessage").innerHTML = "Record successfully created!";
					$('#successActionModal').modal('toggle');
			}

	
	function AddCourse(studentId)
	{
		console.log(studentId);
		console.log(document.getElementById("addstudentpicker").value);
		var course = document.getElementById("addstudentpicker").value;
		window.location.href = "${pageContext.request.contextPath}/admin/addcoursetostudent?studentId="+studentId+"&courseId="+course;
	}
	
	function RemoveEnrolment(enrolId)
	{
		document.getElementById("removethis").value = enrolId;
		document.getElementById("removethisbyId").value = qs['id'];
		 $('#removeEnrolmentModal').modal('toggle');
	}
	
	function DateToday()
	{
		var utc = new Date().toJSON().slice(0,10);
		document.getElementById("dateinput").value = utc;
		console.log(utc);
		
	}
	
</script>
</html>