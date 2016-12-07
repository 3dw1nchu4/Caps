<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<c:if test="${fn:length(hlist) gt 0}">
		<table style="cellspacing: 2; cellpadding: 2; border: 1;">
			<thead>
				<tr class="listHeading">
					<th>NAME</th>
					<th>DESCRIPTION</th>
					<th>PLAY</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="hero" items="${hlist}">
					<tr class="listHeading">
						<td>${hero.name}</td>
						<td>${hero.description}</td>
						<td>${hero.play}</td>				
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>