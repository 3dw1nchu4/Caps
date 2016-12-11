<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<spring:url value="/admin/managelecturer" var="pageurl" />
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
					<li id="sidebarStudent"><a href="managestudent">Manage
							Students</a></li>
					<li id="sidebarLecturer" class="active"><a
						href="managelecturer">Manage Lecturers</a></li>
					<li id="sidebarCourse"><a href="managecourse">Manage
							Courses</a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>

				<h2 class="sub-header" id="sectiontitle">Manage Lecturer
					Records</h2>

				<div id="mainbody" style="width: 100%">

					<div class="container" style="width: 100%">

						<nav class="navbar navbar-default" role="navigation">
						<div class="container-fluid">
							<!--  div class="navbar-header">
								<a class="navbar-brand" href="#">Search</a>
							</div>-->

							<form class="navbar-form navbar-left" role="search"
								action="${pageContext.request.contextPath}/admin/searchlecturer" method="get">
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
						<h5>Your search returned ${lecturerListPage.getNrOfElements() } results</h5>
					</div>
					<c:set var="pageListHolder" value="${lecturerListPage}" scope="session" />
					<table class="table table-striped">
						<thead>
							<tr>
								<th><h4>Lecturer ID</h4></th>
								<th><h4>Lecturer Name</h4></th>
								<th><h4>Status</h4></th>
								<th><h4>Manage</h4></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="object" items="${pageListHolder.pageList}">
								<tr class="listRecord">
									<td>${object.lecturerId}</td>
									<td>${object.lastName}, ${object.firstName}</td>
									
									<td>${object.status}</td>
									<td><button class="btn btn-primary"
											onclick="EditRecord('${object.lecturerId}')">Edit</button>
										<button class="btn btn-danger"
											onclick="DeleteRecord('${object.lecturerId}')" <c:if test="${object.status == 'Disabled'}">disabled</c:if> >Disable</button></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
					<center>
						<div>
							 <span> 
							<ul class="pagination">
							<c:forEach begin="0"
									end="${pageListHolder.pageCount-1}" varStatus="loop">
								    &nbsp;&nbsp;
								    <c:choose>
										<c:when test="${loop.index == pageListHolder.page}"><li class="active"><a href="#" >${loop.index+1}</a></li></c:when>
										<c:otherwise>
											<li><a href="${pageurl}/${loop.index}">${loop.index+1}</a></li>
										</c:otherwise>
									</c:choose>
					    &nbsp;&nbsp;
					    </c:forEach>
					    </ul>
							</span> 
						</div>
						</center>

				</div>
				<div id="editcontent" style="display: none">
					<form id="formEditRecord" method="post">
						<h3 id="EditCreateHeader" class="form-signin-heading">Edit Record</h3>
						<div class="row">
							<div class = "col-lg-4 col-xs-12">
							<label for="id"><h4>Lecturer ID: </h4></label> <input type="text" id="id"
								name="id" class="form-control" value="${data.lecturerId }"
								placeholder="Unique ID" required autofocus>
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
								placeholder="User password (Required for new accounts)" value="" autofocus>
							</div>
						</div>
						<br> <br>

						<button id="submitbutton" class="btn btn-success" type="submit">Update
							Records</button>
						<button type="button" class="btn btn-danger"
							onclick="BackToPrevious()">Cancel</button>
					</form>
					<div class="row"><br> <br></div>
					<div class="jumbotron" id="lecturercourses" name="lecturercourses">
						
						<h3>
							<b><u>${data.lastName }, ${data.firstName }</u></b> is teaching
							the following ${enroldata.size() } courses
						</h3>
						<table class="table table-striped">
							<thead>
								<tr>
									<th><h4>Course Id</h4><br></th>
									<th><h4>Course Name</h4><h6>(Click to view course details)</h6></th>
									<th><h4>From / To</h4><br></th>
									<th><h4>Course Size</h4><br></th>
									<th><h4>Reassign To Another Lecturer</h4><br></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="enrol" items="${enroldata}">
									<tr class="listRecord">
										<td>${enrol.courseId}</td>
										<td><a href="managecourse?id=${enrol.courseId}">${enrol.courseName}</a></td>
										<td>From: ${enrol.startDate} <br> To:
											${enrol.endDate}
										</td>
										<td>${enrol.currentEnrollment}/${enrol.size}</td>
										<td>
											<div>
												<select id="changelecturerpicker${enrol.courseId}"
													name="changelecturerpicker"
													class="selectpicker show-tick form-control"
													data-live-search="true" style="width: 500px">
													<c:forEach var="lecturer" items="${lecturerList}">
														<option
															data-subtext="${lecturer.lastName }, ${lecturer.firstName }">${lecturer.lecturerId }</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td>
											<button type="button" class="btn btn-info"
												onclick="ChangeLecturer('${enrol.courseId}')">
												Reassign</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
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
	
			<!-- Successful transaction Modal -->
	<div id="errorActionModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">We encountered a problem</h4>
				</div>
				<div class="modal-body" id="errorModalMessage"></div>
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
					<h4 class="modal-title">Confirm Inactivation of Lecturer
						Account</h4>
				</div>
				<form action="${pageContext.request.contextPath}/admin/deletelecturer" method="post">
					<div class="modal-body">

						<p>The selected account will be inactivated.</p>
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
			document.getElementById("formEditRecord").action = "${pageContext.request.contextPath}/admin/updatelecturer";
			document.getElementById("submitbutton").innerHTML = "Update Record";
			document.getElementById("EditCreateHeader").innerHTML = "Update Existing Lecturer Record";
		} else
		{
			document.getElementById("formEditRecord").action = "${pageContext.request.contextPath}/admin/createlecturer";
			document.getElementById("submitbutton").innerHTML = "Create New Record";
			document.getElementById("lecturercourses").style.display = "none";
			document.getElementById("EditCreateHeader").innerHTML = "Create New Lecturer Record";
			document.getElementById("password").required = true;
		}
	}

	function EditRecord(id) //Edit button
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managelecturer?id="+id;
	}
	
	function BackToPrevious()
	{
		window.location.href = "${pageContext.request.contextPath}/admin/managelecturer";
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
	
	function ChangeLecturer(courseId)
	{
		var lecturerId = document.getElementById("changelecturerpicker"+courseId).value;
		console.log("lecturerId = "+ lecturerId);
		console.log("CourseId = "+courseId);
		var queryString = "lecturerId="+lecturerId+"&courseId="+courseId+"&returnTo="+qs['id'];
		
		window.location.href = "${pageContext.request.contextPath}/admin/reassignto?"+queryString;
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
	
	if (qs['actionstatus'].includes("userexisterror"))
	{
			document.getElementById("errorModalMessage").innerHTML = "Duplicate User ID. Please try again";
			$('#errorActionModal').modal('show');
	}
	

	
</script>
</html>