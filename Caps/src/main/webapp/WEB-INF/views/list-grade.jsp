<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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

<div class="container" style="width: 100%">
	<div class="row">
		<div class="col-xs-1">
			<label for="search"><h4>Search:</h4> </label>
		</div>
		<div class="col-xs-5">

			<input type='text' class="form-control" id="se"
				placeholder="Search by course Id" name='search' />

		</div>
		<div class="col xs-6">


			<input type="submit" class="btn btn-success" value="OK"
				onclick="search()" class="btn">
		</div>
	</div>
</div>
<!--  <a href="${pageContext.request.contextPath}/gokul/create">Add
	Employee</a>-->


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:choose>
    <c:when test="${fn:length(grlist) gt 0}">
       <div class="table-responsive">
	<table class="table table-striped">
		<thead>
			<tr>
				<th><spring:message code="fieldLabel.courseid" /></th>
				<th><spring:message code="fieldLabel.coursename" /></th>
				<th><spring:message code="fieldLabel.grade" /></th>
				<th><spring:message code="fieldLabel.earncredit" /></th>
				<th><spring:message code="fieldLabel.status" /></th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="role" items="${grlist}">
				<tr class="listgrades">
					<td>${role.courses.courseId}</td>
					<td>${role.courses.courseName}</td>
					<td>${role.grade}</td>
					<td>${role.earnedCredit}</td>
					<td>${role.status}</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>
	</div>
       
    </c:when>
    
    <c:otherwise>
 
   	<spring:message code="error.notfound" />

    </c:otherwise>
  
</c:choose>



<script>
	function search() {
		var x = document.getElementById("se").value;
		window.location = "${pageContext.request.contextPath}/Lec/list-grade/" + x;
	}
</script>