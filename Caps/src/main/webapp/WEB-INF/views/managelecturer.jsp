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
					<li id="sidebarStudent"><a href="javascript:Manage('student')">Manage
							Students</a></li>
					<li id="sidebarLecturer"><a
						href="javascript:Manage('lecturer')">Manage Lecturers</a></li>
					<li id="sidebarCourse"><a href="javascript:Manage('course')">Manage
							Courses</a></li>
					<li id="sidebarEnrolment"><a
						href="javascript:Manage('enrolment')">Manage Enrolment</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>

				<h2 class="sub-header" id="sectiontitle">Section title</h2>

				<div id="mainbody" style="width: 100%">

					<div class="container" style="width: 100%">
						<div class="row">
							<div class="col-xs-1">
								<label for="search"><h4>Search:</h4> </label>
							</div>
							<div class="col-xs-5">
								<input type="text" id="inputPK" class="form-control"
									placeholder="Search for something here">
							</div>
							<div class="container">
								<div class="col xs-6">
									<button class="btn btn-default" style="float: left">Search</button>
								</div>
								<div class="col xs-6"></div>
							</div>
							<div class="container">
								<button class="btn btn-success" style="float: left"
									onclick="EditRecord('create')">
									<span class="glyphicon glyphicon-plus"></span><span>
										Creates</span>
								</button>
							</div>
						</div>
					</div>

					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th id="tableheader1">#</th>
									<th>Header</th>
									<th>Header</th>
									<th>Header</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="object" items="${dataList}">
									<tr class="listRecord">
										<td>${object.lecturerId}</td>
										<td>${object.firstName}</td>
										<td>${object.lastName}</td>

										<td><button class="btn btn-primary"
												onclick="EditRecord('${object.lecturerId}')">Edit</button>
											<button class="btn btn-danger"
												onclick="DeleteRecord('${object.lecturerId}')">Delete</button></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="get">
						<h2 class="form-signin-heading">Edit Record</h2>
						<div style="width: 40%">
							<label for="id">Lecturer ID: </label> 
							<input type="text" id="id" name="id"
								class="form-control" value="${data.lecturerId }"
								placeholder="Unique ID" required autofocus>
						</div>
						<div style="width: 40%">
							<label for="firstName">First Name: </label> 
							<input type="text"
								id="firstName" name="firstName" class="form-control" placeholder="First Name"
								value="${data.firstName }" required autofocus>
						</div>
						<div style="width: 40%">
							<label for="lastName">Last Name: </label> 
							<input type="text"
								id="lastName" name="lastname" class="form-control" placeholder="Last Name"
								value="${data.lastName }" required autofocus>
						</div>
						<br> <br>
						<!-- removed the type="submit" property for testing-->
						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Records</button>
						<button class="btn btn-danger" onclick="BackToPrevious()">Cancel
							and Return to Previous</button>
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
					<h4 class="modal-title">Confirm Deletion</h4>
				</div>
				<div class="modal-body">
					<p>The selected entry will be permanently deleted.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-danger">Delete</button>

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


	var url = window.location.search;

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
	

	try
	{
		if (qs['manage'] == "student")
		{
			document.getElementById("sidebarStudent").className = "active";
			document.getElementById("tableheader1").innerHTML = "Student ID";
			document.getElementById("sectiontitle").innerHTML = "Manage Student Records";
	
		} else if (qs['manage'] == "lecturer")
		{
			document.getElementById("sidebarLecturer").className = "active";
			document.getElementById("tableheader1").innerHTML = "Lecturer ID";
			document.getElementById("sectiontitle").innerHTML = "Manage Lecturer Records";
	
		} else if (qs['manage'] == "course")
		{
			document.getElementById("sidebarCourse").className = "active";
			document.getElementById("tableheader1").innerHTML = "Course ID";
			document.getElementById("sectiontitle").innerHTML = "Manage Course Records";
	
		} else if (qs['manage'] == "enrolment")
		{
			document.getElementById("sidebarEnrolment").className = "active";
			document.getElementById("tableheader1").innerHTML = "Enrolment ID";
			document.getElementById("sectiontitle").innerHTML = "Manage Enrolment Records";
	
		} else
		{
			$('#redirectModal').modal('toggle');
			window.setTimeout(function()
			{
				window.location = "adminmgt.jsp?manage=student&userrole="
						+ qs['userrole'];
			}, 3000);
		}
	}
	catch (err)
	{
		console.log("no query string");
		RedirectToLogin();
	}
	
	
	if (url.includes("id="))
	{
		document.getElementById("mainbody").style.display = "none";
		document.getElementById("editcontent").style.display = "block";
		
		if (qs['id'] !="create")
		{
			document.getElementById("id").readOnly = true;
			document.getElementById("formEditRecord").action = "updateLecturer";
			document.getElementById("submitbutton").innerHTML = "Update Record";
		} else
		{
			document.getElementById("formEditRecord").action = "createLecturer";
			document.getElementById("submitbutton").innerHTML = "Create New Record";
		}
	}

	function EditRecord(id) //Edit button
	{
		window.location.href = window.location.href + '&id='+id;
	}

	function Manage(recordtype)
	{
		window.location.href = url + "?userrole=" + qs['userrole'] + "&manage="
				+ recordtype;
	}
	
	function BackToPrevious()
	{
		var previous = qs['manage'];
		Manage(previous);
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
		$('#deleteModal').modal('toggle');
	}
	
</script>
</html>