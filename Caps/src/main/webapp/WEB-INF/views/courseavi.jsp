

<%@page import="edu.iss.caps.model.Course"%>
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
<title>allcourse</title>
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
						href="${pageContext.request.contextPath}/Lec/viewallenrole">View
							Course Enrolement </a></li>
					<li id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Lec/viewalltograde">Grade
							a course </a></li>
					<li class="active" id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Lec/viewallcr">View a
							Student Preformance </a></li>
				</ul>

			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Dashboard</h1>


				<h2 class="sub-header" id="sectiontitle">Section title</h2>



				<div class="container" style="width: 100%">
					<nav class="navbar navbar-default" role="navigation">
					<div class="container-fluid">
						<!--  div class="navbar-header">
								<a class="navbar-brand" href="#">Search</a>
							</div>-->

						<form class="navbar-form navbar-left" role="search"
							action="searchviewallcr" method="get">
							<div class="form-group">
								<label>Search by : </label>
							</div>
							<div class="input-group">

								<input type="text" class="form-control"
									placeholder="course name or ID" name="searchcontent"
									id="searchcontent">

								<!-- <div class="input-group-btn">
										<button class="btn btn-default" type="submit">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div> -->
							</div>
							<button type="submit" class="btn btn-default">Search</button>
							<a class="btn btn-success"
								href="${pageContext.request.contextPath}/Lec/mycourse"><spring:message
									code="My course" /></a> <a class="btn btn-success"
								href="${pageContext.request.contextPath}/Lec/viewallcr"><spring:message
									code="All course" /></a>
						</form>

					</div>
					</nav>
				</div>
				<!--  <a href="${pageContext.request.contextPath}/gokul/create">Add
	Employee</a>-->


				<%@ taglib prefix="form"
					uri="http://www.springframework.org/tags/form"%>
				<%@ page import="java.io.*,java.util.*"%>


				<c:choose>
					<c:when test="${fn:length(Enlist) gt 0}">
						<div class="table-responsive">
							<table class="table table-striped">
								<thead>
									<tr>
										<th><spring:message code="fieldLabel.courseid" /></th>

										<th><spring:message code="fieldLabel.coursename" /></th>
										<th><spring:message code="fieldLabel.credits" /></th>
										<th><spring:message code="fieldLabel.cenroll" /></th>
										<th><spring:message code="fieldLabel.stgr" /></th>


									</tr>
								</thead>
								<tbody>
									<c:set var="count" value="0" scope="page" />
									<c:forEach var="role" items="${Enlist}" varStatus="loop">
										<tr class="listRecord">
											<td>${role.courseId}</td>
											<td>${role.courseName}</td>
											<td>${role.credits}</td>
											<td>${role.currentEnrollment}</td>

										
											<td align="center"><c:set var="sta" scope="session"
													value="${cou[loop.index]}" /> <c:choose>
													<c:when test="${sta==0}">
														<p class="btn btn-primary disabled">
															<spring:message code="${cou[loop.index]}" />
															<br>( view all )
														</p>

													</c:when>
													<c:otherwise>
														<a class="btn btn-primary"
															href="${pageContext.request.contextPath}/Lec/viewsp/${role.courseId}"><spring:message
																code="${cou[loop.index]}" /><br>( view all )</a>

													</c:otherwise>

												</c:choose></td>
											<%-- 	<%
											count =0;
											${cou};
											${count};
											%> --%>
										</tr>



										</tr>

									</c:forEach>




								</tbody>
							</table>
						</div>

					</c:when>

					<c:otherwise>


						<spring:message code="error.notfoundall" />

					</c:otherwise>

				</c:choose>
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
	$(function() {
		$("#header").load(
				"${pageContext.request.contextPath}/resources/header.html");
		$("#footer").load(
				"${pageContext.request.contextPath}/resources/footer.html");
	});

	function search() {
		var x = document.getElementById("inputPK").value;
		window.location = "${pageContext.request.contextPath}/Lec/viewallcr/"
				+ x;
	}
	

	//clears search content when entering search box
	$("#searchcontent").click(function(){
	    $("#searchcontent").val('');
	});
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

</script>
</html>


