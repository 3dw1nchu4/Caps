

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

					<li id="sidebarStudent"><a
						href="${pageContext.request.contextPath}/Lec/viewallenrole">View
							Course Enrolment </a></li>
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



				<h2 class="sub-header" id="sectiontitle">View Student performance</h2>



				<div class="container" style="width: 100%">
					<nav class="navbar navbar-default" role="navigation">
					<div class="container-fluid">
						<!--  div class="navbar-header">
								<a class="navbar-brand" href="#">Search</a>
							</div>-->

						<form class="navbar-form navbar-left" role="search"
							action="${requestScope['javax.servlet.forward.request_uri']}"
							method="get">
							<div class="form-group">
								<label>Search by : </label>
							</div>
							<div class="input-group">

								<input type="text" class="form-control"
									placeholder="Student name or ID" name="searchcontent"
									id="searchcontent">

								<!-- <div class="input-group-btn">
										<button class="btn btn-default" type="submit">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div> -->
							</div>
							<button type="submit" class="btn btn-default">Search</button>

							<a class="btn btn-success"
								href="${pageContext.request.contextPath}/Lec/viewallcr"> <span
								class="glyphicon glyphicon-arrow-left"></span> back to All
								courses
							</a>

						</form>

					</div>
					</nav>
				</div>
				<!--  <a href="${pageContext.request.contextPath}/gokul/create">Add
	Employee</a>-->
				<%@ taglib prefix="form"
					uri="http://www.springframework.org/tags/form"%>
	<!------------------------------------------------------------------------------------------------------------------------->
	              
				  <spring:url value="/Lec/viewsp" var="pageurl" />
				<div id="searchcount" name="searchcount" style="display: none">
					<h5>Your search returned ${studList.getNrOfElements() } results</h5>
				</div>
				
		
				<div class="table-responsive">
						<c:set var="pageListHolder" value="${studList}" scope="session" />
						<table class="table table-striped">
							<thead>
								<tr>
										<th><h4><spring:message code="fieldLabel.Studentid" /></h4></th>
										<th><h4><spring:message code="fieldLabel.name" /></h4></th>


										<th><h4><spring:message code="fieldLabel.grade" /></h4></th>
										<th><h4><spring:message code="fieldLabel.earncredit" /></h4></th>
										<th><h4><spring:message code="fieldLabel.gpa" /></h4></th>

										<th><h4><spring:message code="fieldLabel.status" /></h4></th>

									</tr>
							</thead>



							<tbody>
									<c:forEach var="role" items="${pageListHolder.pageList}" varStatus="loop">
										<tr class="listRecord">
											<td>${role.studentDetails.studentId}</td>
											<td>${role.studentDetails.firstName}
												${role.studentDetails.lastName}</td>



											<td>${role.grade}</td>
											<td>${role.earnedCredit}</td>
											<td>${Stgpa[loop.index]}</td>
											<td><c:set var="sta" scope="session"
													value="${role.status}" /> <c:choose>
													<c:when test="${sta=='Failed'}">
														<p class="btn btn-danger">
															<spring:message code="${role.status}" />
														</p>
													</c:when>
													<c:when test="${sta=='Passed'}">
														<p class="btn btn-success">
															<spring:message code="${role.status}" />
														</p>
													</c:when>
													<c:otherwise>
													<p class="btn btn-warning">
															<spring:message code="${role.status}" />
														</p>
													</c:otherwise>

												</c:choose></td>
										</tr>

									</c:forEach>
								</tbody>
						</table>
						<div>
							<span style="float: left;"> <c:choose>
									<c:when test="${pageListHolder.firstPage}">Prev</c:when>
									<c:otherwise>
										<a href="${pageurl}/${ID}/prev">Prev</a>
									</c:otherwise>
								</c:choose>
							</span> <span> <c:forEach begin="0"
									end="${pageListHolder.pageCount-1}" varStatus="loop">
    &nbsp;&nbsp;
    <c:choose>
										<c:when test="${loop.index == pageListHolder.page}">${loop.index+1}</c:when>
										<c:otherwise>

											

											<a href="${pageurl}/${ID}/${loop.index}">${loop.index+1}</a>

										</c:otherwise>
									</c:choose>
    &nbsp;&nbsp;
    </c:forEach>
							</span> <span> <c:choose>
									<c:when test="${pageListHolder.lastPage}">Next</c:when>
									<c:otherwise>
										<a href="${pageurl}/${ID}/next">Next</a>
									</c:otherwise>
								</c:choose>
							</span>
						</div>
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


