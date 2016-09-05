<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/WEB-INF/tld/paging.tlg" prefix="pt"%>
<c:if test="${sessionScope.locale==null}">
	<c:set var="locale" value="EN" scope="session" />
</c:if>
<c:if test="${sessionScope.locale!=null}">
	<fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="resource.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.hr.office.header.vacancy"
	var="vacancy" />
<fmt:message bundle="${locale}"
	key="locale.hr.office.header.vacancyVerify" var="vacancyVerify" />
<fmt:message bundle="${locale}" key="locale.hr.office.header.interview"
	var="interview" />
<fmt:message bundle="${locale}" key="locale.vacancy.add" var="add" />
<fmt:message bundle="${locale}" key="locale.open" var="open" />
<fmt:message bundle="${locale}" key="locale.edit" var="edit" />
<fmt:message bundle="${locale}" key="locale.remove" var="remove" />
<fmt:message bundle="${locale}" key="locale.edit" var="edit" />
<fmt:message bundle="${locale}" key="locale.active" var="active" />
<fmt:message bundle="${locale}" key="locale.deactive" var="deactive" />
<fmt:message bundle="${locale}" key="locale.hot" var="hot" />
<fmt:message bundle="${locale}"
	key="locale.applicant.office.header.profile" var="profile" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${privateOffice}</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/styleForProfile.css" rel="stylesheet">
<link href="css/footerStyleForProfile.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<%@include file="/WEB-INF/jspf/navigation.jspf"%>
	<div class="container">
		<br> <br> <br> <br> <br>
		<div class="container">
			<div class="container">
				<div class="top-nav clearfix">
					<ul class="nav nav-tabs nav-justified">
						<li><a href="Controller?command=to-private-office">${profile}</a></li>
						<li class="active"><a
							href="Controller?command=to-hr-list-vacancy">${vacancy}</a></li>
						<li><a href="Controller?command=to-verify-list">${vacancyVerify}</a></li>
						<li><a href="#">${interview}</a></li>
					</ul>
				</div>
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="to-hr-add-vacancy-page">
					<div class="left-menu clearfix">
						<input type="submit" class="btn btn-success btn-lg" value="${add}">
					</div>
				</form>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">${listVacancy}</h3>
					</div>
					<div class="panel-body">
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="delete-vacancy">
							<c:forEach items="${requestScope.vacancies}" var="vacancy">
								<div class="panel-body">
									<div class="thumbnail">
										<div class="caption">
											<h3>${vacancy.name}
												<c:choose>
													<c:when test="${vacancy.hotType == 'HOT'}">
														<span class="glyphicon glyphicon-ok"></span>
														<span class="glyphicon glyphicon-fire"></span>
													</c:when>
													<c:when test="${vacancy.active == 'ACTIVE'}">
														<span class="glyphicon glyphicon-ok"></span>
													</c:when>
												</c:choose>
											</h3>
											<p>${publishDate}${vacancy.publishDate}</p>
											<p>
												<a
													href="Controller?command=hr-private-office-show-vacancy&vacancy-id=${vacancy.idVacancy}"
													class="btn btn-info" role="button">${open}</a> <a href="#"
													class="btn btn-default" role="button">${edit}</a> <input
													type="hidden" value="${vacancy.idVacancy}"
													name="vacancy-id"> <input type="submit"
													class="btn btn-default" value="${remove}">
												<c:choose>
													<c:when test="${vacancy.active == 'ACTIVE'}">
														<a
															href="Controller?command=deactivate-vacancy&vacancy-id=${vacancy.idVacancy}"
															class="btn btn-danger" role="button">${deactive}</a>
														<c:if test="${vacancy.hotType == 'NON_HOT'}">
															<a
																href="Controller?command=hot-vacancy&vacancy-id=${vacancy.idVacancy}"
																class="btn btn-warning" role="button">${hot}</a>
														</c:if>
													</c:when>
													<c:when test="${vacancy.active == 'NON_ACTIVE'}">
														<a
															href="Controller?command=activate-vacancy&vacancy-id=${vacancy.idVacancy}"
															class="btn btn-primary" role="button">${active}</a>
													</c:when>

												</c:choose>


											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</form>
					</div>
					<pt:paging-links page="${requestScope.page}"
						pageAmount="${requestScope.pageAmount}"
						href="Controller?command=to-hr-list-vacancy" />
				</div>
			</div>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>