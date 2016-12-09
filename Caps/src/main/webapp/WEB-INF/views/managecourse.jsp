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
					<li id="sidebarCourse"><a href="managecourse">Manage
							Courses</a></li>
					<li id="sidebarEnrolment"><a href="manageenrolment">Manage
							Enrolment</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>

				<h2 class="sub-header" id="sectiontitle" name="sectiontitle">Section title</h2>

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
								<th><h4>Max Size</h4></th>
								<th><h4>Currently enrolled</h4></th>
								<th><h4>Course Status</h4></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="object" items="${dataList}">
								<tr class="listRecord">
									<td>${object.courseId}</td>
									<td>${object.courseName}</td>
									<td>${object.size}</td>
									<td>${object.currentEnrollment}</td>
									<td>${object.status}</td>
									<td><button class="btn btn-primary"
											onclick="EditRecord('${object.courseId}')">Edit</button>
										<button class="btn btn-danger"
											onclick="DeleteRecord('${object.courseId}')">Delete</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

				</div>
				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="post">
						<h2 class="form-signin-heading">Edit Record</h2>
						<br>
						<div id="formhead" name="formhead">
						<h3>This course is taught by ${data.lecturerDetails.lastName },
							${data.lecturerDetails.firstName } </h3>
						</div>
						<div style="width: 40%;" >
						<label for="id">Course ID: </label>
							<input type="text" id="id" name="id" class="form-control"
								value="${data.courseId }" placeholder="Unique ID, autogenerated"
								autofocus readonly> 
						</div>
						<div style="width: 40%;">	
						<label for="lecturerId">Lecturer ID: </label>	
								<input type="text" id="lecturerId"
								name="lecturerId" class="form-control" placeholder="lecturerId"
								value="${data.lecturerDetails.lecturerId }" required autofocus >
						</div>

						<div style="width: 40%">
							<label for="cseName">Course Name: </label> <input type="text"
								id="cseName" name="cseName" class="form-control"
								placeholder="First Name" value="${data.courseName }" required
								autofocus>
						</div>
						<div style="width: 40%">
							<label for="startDate">Start Date: </label> <input type="text"
								id="startDate" name="startDate" class="form-control"
								placeholder="Start Date (YYYY-MM-DD)" value="${data.startDate }" required
								autofocus <c:if test='${data.size > 1 }'>readonly </c:if>>
						</div>
						<div style="width: 40%">
							<label for="endDate">End Date: </label> <input type="text"
								id="endDate" name="endDate" class="form-control"
								placeholder="First Name" value="${data.endDate }" required
								autofocus  <c:if test='${data.size > 1 }'>readonly </c:if>>
						</div>
						<div style="width: 40%">
							<label for="size">Max Size: </label> <input type="text" id="size"
								name="size" class="form-control" placeholder="First Name"
								value="${data.size }" required autofocus>
						</div>
						<div style="width: 40%">
							<label for="currentEnrollment">Enrolled Students: </label> <input
								type="text" id="currentEnrollment" name="currentEnrollment"
								class="form-control" placeholder="First Name"
								value="${data.currentEnrollment }" required autofocus  <c:if test='${data.size > 1 }'>readonly </c:if>>
						</div>
						<div style="width: 40%">
							<label for="status">Course Status: </label> <input type="text"
								id="status" name="status" class="form-control"
								placeholder="First Name" value="${data.status }" required
								autofocus>
						</div>



						<br> <br>
						<!-- removed the type="submit" property for testing-->
						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Records</button>
						<button type="button" class="btn btn-danger"
							onclick="BackToPrevious()">Cancel</button>
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
				<form action="deletecourse" method="post">
					<div class="modal-body">

						<p>The selected entry will be permanently deleted.</p>
						<input id="deletethis" name="deletethis" class="form-control"
							placeholder="Last Name" value="" required />
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
		$("#header").load("${pageContext.request.contextPath}/resources/header.html");
		$("#footer").load("${pageContext.request.contextPath}/resources/footer.html");
	});
try{
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
}
catch (err)
{
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
	

	try
	{
		if (url.includes("student"))
		{
			document.getElementById("sidebarStudent").className = "active";
			document.getElementById("sectiontitle").innerHTML = "Manage Student Records";
	
		} else if (url.includes("lecturer"))
		{
			document.getElementById("sidebarLecturer").className = "active";
			document.getElementById("sectiontitle").innerHTML = "Manage Lecturer Records";
	
		} else if (url.includes("course"))
		{
			document.getElementById("sidebarCourse").className = "active";
			document.getElementById("sectiontitle").innerHTML = "Manage Course Records";
	
		} else if (url.includes("enrolment"))
		{
			document.getElementById("sidebarEnrolment").className = "active";
			document.getElementById("sectiontitle").innerHTML = "Manage Enrolment Records";
	
		} 

	}
	catch (err)
	{
		console.log(err);
		
	}
	
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
			
		}
	}

	function EditRecord(id) //Edit button
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managecourse?id="+id;
	}

	function Manage(recordtype)
	{
		window.location.href = url + "?userrole=" + qs['userrole'] + "&manage="
				+ recordtype;
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
	

	
</script>
</html>