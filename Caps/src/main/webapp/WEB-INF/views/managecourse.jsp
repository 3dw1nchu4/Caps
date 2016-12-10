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
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li id="sidebarStudent"><a href="managestudent">Manage
							Students</a></li>
					<li id="sidebarLecturer"><a href="managelecturer">Manage
							Lecturers</a></li>
					<li id="sidebarCourse" class="active"><a href="managecourse">Manage
							Courses</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>

				<h2 class="sub-header" id="sectiontitle" name="sectiontitle">Manage
					Student Records</h2>

				<div id="mainbody" style="width: 100%">

					<div class="container" style="width: 100%">

						<nav class="navbar navbar-default" role="navigation">
						<div class="container-fluid">


							<form class="navbar-form navbar-left" role="search"
								action="searchcourse" method="get">
								<div class="form-group">
									<label>Course Status: </label> <select name="accountstatus"
										id="accountstatus" class="selectpicker">
										<optgroup label="Account Status">
											<option value="open">Open</option>
											<option value="closed">Closed</option>
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
								<th><h4>Course ID</h4></th>
								<th><h4>Course Name</h4></th>
								<th><h4>Lecturer</h4></th>
								<th><h4>Currently enrolled</h4></th>
								<th><h4>Course Status</h4></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="object" items="${dataList}">
								<tr class="listRecord">
									<td>${object.courseId}</td>
									<td>${object.courseName}</td>
									<td>${object.lecturerDetails.lastName},
										${object.lecturerDetails.firstName}</td>
									<td>${object.currentEnrollment}/ ${object.size}</td>
									<td>${object.status}</td>
									<td><button class="btn btn-primary"
											onclick="EditRecord('${object.courseId}')"
											<c:if test='${object.status.contains("Closed")}'>disabled</c:if>>Edit</button>
										<button class="btn btn-danger"
											onclick="DeleteRecord('${object.courseId}')"
											<c:if test='${object.status.contains("Closed")}'>disabled</c:if>>Close
											Course</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>
				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="POST">
						<h2 class="form-signin-heading">Edit Record</h2>
						<br>
						<div id="formhead" name="formhead">
							<h3>
								This course is taught by <b><u>${data.lecturerDetails.lastName },
										${data.lecturerDetails.firstName }</u></b>
							</h3>
						</div>
						<div>
							<br>
						</div>
						<div style="width: 40%;">
							<label for="id">Course ID: </label> <input type="text" id="id"
								name="id" class="form-control" value="${data.courseId }"
								placeholder="Unique ID, autogenerated" autofocus readonly>
						</div>
						<div style="width: 40%;">
							<div class="form-group">
								<label for="lecturerId" class="control-label">Lecturer
									ID:</label>
								<div>
									<select id="lecturerId" name="lecturerId"
										class="selectpicker show-tick form-control"
										data-live-search="true">
										<c:forEach var="lecturer" items="${lecturerList}">
											<option
												data-subtext="${lecturer.lastName }, ${lecturer.firstName }">${lecturer.lecturerId }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div style="width: 40%">
							<label for="cseName">Course Name: </label> <input type="text"
								id="cseName" name="cseName" class="form-control"
								placeholder="Course Name (Required)" value="${data.courseName }"
								required autofocus>
						</div>
						<div style="width: 40%">
							<label for="startDate">Start Date: </label> <input type="text"
								id="startDate" name="startDate" class="form-control"
								placeholder="Start Date (YYYY-MM-DD)" value="${data.startDate }"
								required autofocus
								pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])">
						</div>
						<div style="width: 40%">
							<label for="endDate">End Date: </label> <input type="text"
								id="endDate" name="endDate" class="form-control"
								placeholder="End Date (YYYY-MM-DD)" value="${data.endDate }"
								required autofocus
								pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])">
						</div>
						<div style="width: 40%">
							<label for="size">Max Size: </label> <input type="text" id="size"
								name="size" class="form-control" placeholder="Maximum class size"
								value="${data.size }" required autofocus>
						</div>
						<div style="width: 40%">
							<label for="currentEnrollment">Enrolled Students: </label> <input
								type="text" id="currentEnrollment" name="currentEnrollment"
								class="form-control" placeholder="New enrolments default to 0"
								value="${data.currentEnrollment }" autofocus
								readonly > <br>
						</div>
						<div style="width: 40%">
							<label for="status">Course Status: </label> <select name="status"
								id="status" class="selectpicker"
								<c:if test='${!data.status.contains("Open")}'> disabled</c:if>>
								<optgroup label="Course Status">
									<option value="Open">Open</option>
									<option value="Closed">Closed</option>
								</optgroup>
							</select>
						</div>


						<br> <br>
						<!-- removed the type="submit" property for testing-->
						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Records</button>
						<button type="button" class="btn btn-danger"
							onclick="BackToPrevious()">Cancel</button>
						<br>
						<br>
					</form>
					<div id="ShowStudentsInCourse" class="jumbotron">
					<h3>A total of ${enrolmentList.size()} student records were found for this course</h3>
						<table class="table table-striped">
							<thead>
								<tr>
									<th><h4>Student ID</h4><br></th>
									<th><h4>Student Name</h4><h6>(Click to view student record)</h6></th>
									<th><h4>Grade Received</h4><br></th>
									<th><h4>Student Status</h4><br></th>
									<th><h4></h4></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="enrolment" items="${enrolmentList}">
									<tr class="listRecord">
										<td>${enrolment.studentDetails.studentId}</td>
										<td><a href="managestudent?id=${enrolment.studentDetails.studentId}">${enrolment.studentDetails.lastName},
											${enrolment.studentDetails.firstName}</a></td>
										<td>${enrolment.grade}</td>
										<td>${enrolment.status}</td>
										<td><button type="button" class="btn btn-info"
												onclick="RemoveEnrolment('${enrolment.enrolmentId}')"
												<c:if test="${enrolment.status.contains('Passed') || enrolment.status.contains('Failed') || enrolment.status.contains('Removed')}"> disabled
													</c:if>>
												Remove from Course</button></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>



			</div>
		</div>

		<br> <br> <br>
	</div>
	<div></div>


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
					<h4 class="modal-title">Confirm Course Closure</h4>
				</div>
				<form action="deletecourse" method="post">
					<div class="modal-body">

						<p>The selected course will be closed.</p>
						<input id="deletethis" name="deletethis" class="form-control"
							placeholder="Last Name" value="" required type="hidden" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button id="deletebtn" name="deletebtn" type="submit"
							class="btn btn-danger">Close this course</button>
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
				<form action="removestudentenrolmentviacourse" method="post">
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
		$("#header").load("${pageContext.request.contextPath}/resources/header.jsp");
		$("#footer").load("${pageContext.request.contextPath}/resources/footer.html");
	});
	
	try
	{
		$(document).ready(function() 
		{
		    $('.selectpicker').selectpicker();
		});
			
		//clears search content when entering search box
		$("#searchcontent").click(function()
		{
		    $("#searchcontent").val('');
		});
	}
	catch (err)
	{
		console.log(err);
	}

	var url = window.location.href;
	console.log(url);

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
	
	
	if (qs['searchcontent'] != null)
	{
		document.getElementById("searchcontent").value = qs['searchcontent'];
		document.getElementById("searchcount").style.display = "block";
	}
	if (qs['accountstatus'] != null)
	{
		document.getElementById("accountstatus").value = qs['accountstatus'];
	}
	
	
	if (url.includes("id="))
	{
		document.getElementById("mainbody").style.display = "none";
		document.getElementById("editcontent").style.display = "block";
		
		if (qs['id'] !="create")
		{
			document.getElementById("id").readOnly = true;
			document.getElementById("formEditRecord").action = "updatecourse";
			document.getElementById("submitbutton").innerHTML = "Update Record";
		} else
		{
			document.getElementById("formEditRecord").action = "createcourse";
			document.getElementById("submitbutton").innerHTML = "Create New Record";
			document.getElementById("startDate").readonly= false;
			document.getElementById("formhead").style.display= "none";
			document.getElementById("ShowStudentsInCourse").style.display= "none"
			
			
		}
	}

	if (qs['actionstatus'] == "success")
	{
		document.getElementById("successModalMessage").innerHTML = "Record successfully updated!";
		$('#successActionModal').modal('toggle');
	}

	if (qs['actionstatus'] == "createsuccess")
	{
		document.getElementById("successModalMessage").innerHTML = "Record successfully created!";
		$('#successActionModal').modal('toggle');
	}
	
	function EditRecord(id) //Edit button
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managecourse?id="+id;
	}
	
	function BackToPrevious()
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managecourse";
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
	
	function RemoveEnrolment(enrolId)
	{
		document.getElementById("removethis").value = enrolId;
		document.getElementById("removethisbyId").value = qs['id'];
		 $('#removeEnrolmentModal').modal('toggle');
	}
	
	

	
</script>
</html>