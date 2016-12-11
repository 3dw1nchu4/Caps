<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--  additional custom styles -->
<link rel="stylesheet" href="resources/mystyle.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>

	
	<nav class="navbar navbar-inverse navbar-fixed-top">
<div class="container-fluid">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#myNavbar">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a class="navbar-brand"
			href="${pageContext.request.contextPath}/home/index?#ssh">CAPS</a>

	</div>
	<div class="collapse navbar-collapse" id="myNavbar">

		<ul class="nav navbar-nav">
			<li id="navbarHome"><a
				href="${pageContext.request.contextPath}/home/index?#ssh">Home</a></li>

			<%
				if (session.getAttribute("user") == null || session.getAttribute("user").equals("")) {
				}
				else{
			%>
			<li id="navbarrole"><a
				href="${pageContext.request.contextPath}/home/movein">${role}</a></li>
			<%
				}
			%>


		</ul>
		<ul class="nav navbar-nav navbar-right">
			<!--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>-->
			<li><a
				href="${pageContext.request.contextPath}/home/index?#event">Events</a></li>
			<li><a
				href="${pageContext.request.contextPath}/home/index?#news">News</a></li>
			<li><a
				href="${pageContext.request.contextPath}/home/index?#about">About
					us</a></li>


			<%-- <li id="navbarUser"><a href="#" style="max-width: 100%"><span
						class="glyphicon glyphicon-user"></span> ${role}</a>
						</li>  --%>

			<%
				if (session.getAttribute("user") == null || session.getAttribute("user").equals("")) {
			%>

			<li id="navbarLogin"><a
				href="${pageContext.request.contextPath}/home/login"
				data-toggle="login" data-placement="auto" title="Login"
				style="max-width: 100%"><span class="glyphicon glyphicon-log-in"></span>
					Login</a></li>

			<%
				} else {
			%>
			<li id="navbarLogout"><a
				href="${pageContext.request.contextPath}/home/logout"
				data-toggle="logout" data-placement="auto" title="Logout"
				style="max-width: 100%"><span
					class="glyphicon glyphicon-log-out"></span> Log out</a></li>

			<%
				}
			%></li>

		</ul>
	</div>
</div>
</nav>

	<div class="container" style="width: 50%; min-width: 300px">
	<br><br>
		<h1>Login</h1>
		<div class="jumbotron">

			<form:form modelAttribute="user" method="POST"
				action="${pageContext.request.contextPath}/home/authenticate">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="userid" class="sr-only"></label>
				<form:input class="form-control" placeholder="UserId" id="userid"
					path="userId" required="true"/>
				<br>
				<label for="inputPassword" class="sr-only"></label>
				<form:input type="password" class="form-control" placeholder="Password"
					id="inputPassword" path="password" required="true"/>
				<div class="checkbox">
					<label> <input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div>
				<!-- removed the type="submit" property for testing, redirect function for testing only -->
				<form:button class="btn btn-lg btn-primary btn-block" name="submit"
					type="submit" value="s">
				Sign in</form:button>
				<center>
				<label><font color="red">${errorMsg}</font></label>
				</center>
			</form:form>

		</div>
	</div>
	<footer
		class="t7-container t7-dark-grey t7-padding-32 t7-padding-xlarge footer">
	<div id="footer"></div>
	</footer>

</body>

</html>