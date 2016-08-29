<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="EN" />
<c:if test="${sessionScope.locale!=null}">
	<fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="resource.locale" var="locale" />
<fmt:message bundle="${locale}" key="locale.vacancy.add" var="add" />
<fmt:message bundle="${locale}"
	key="locale.applicant.office.header.profile" var="profile" />
<fmt:message bundle="${locale}" key="locale.hr.office.header.vacancy"
	var="vacancy" />
<fmt:message bundle="${locale}"
	key="locale.hr.office.header.vacancyVerify" var="vacancyVerify" />
<fmt:message bundle="${locale}" key="locale.hr.office.header.interview"
	var="interview" />
<fmt:message bundle="${locale}" key="locale.addVacancy.vacancyName"
	var="vacancyName" />
<fmt:message bundle="${locale}" key="locale.addVacancy.enterVacancyName"
	var="enterVacancyName" />
<fmt:message bundle="${locale}" key="locale.addVacancy.wrongVacancyName"
	var="wrongVacancyName" />
<fmt:message bundle="${locale}" key="locale.addVacancy.salary" var="salary" />
<fmt:message bundle="${locale}" key="locale.addVacancy.enterSalary"
	var="enterSalary" />
<fmt:message bundle="${locale}" key="locale.addVacancy.wrongSalary"
	var="wrongSalary" />
<fmt:message bundle="${locale}" key="locale.addVacancy.currency"
	var="currency" />
<fmt:message bundle="${locale}" key="locale.addVacancy.rub" var="rub" />
<fmt:message bundle="${locale}" key="locale.addVacancy.dolar" var="dolar" />
<fmt:message bundle="${locale}" key="locale.addVacancy.description"
	var="description" />
<fmt:message bundle="${locale}" key="locale.addVacancy.wrongDescription"
	var="wrongDescription" />
<fmt:message bundle="${locale}" key="locale.addVacancy.conditions"
	var="conditions" />
<fmt:message bundle="${locale}" key="locale.addVacancy.wrongConditions"
	var="wrongConditions" />
<fmt:message bundle="${locale}" key="locale.addVacancy.employmentType"
	var="employmentType"/>
<fmt:message bundle="${locale}" key="locale.addVacancy.fullTime"
	var="fullTime"/>
<fmt:message bundle="${locale}" key="locale.addVacancy.partTime"
	var="partTime"/>
<fmt:message bundle="${locale}" key="locale.addVacancy.contractual"
	var="contractual"/>
<fmt:message bundle="${locale}" key="locale.addVacancy.addVacancy"
	var="add"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${add}</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/styleForProfile.css" rel="stylesheet">
<link href="css/footerStyle.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<%@include file="/WEB-INF/jspf/navigation.jspf"%>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="container">
		<div class="top-nav clearfix">
			<ul class="nav nav-tabs nav-justified">
				<li><a href="#">${profile}</a></li>
				<li class="active"><a
					href="Controller?command=to-hr-list-vacancy">${vacancy}</a></li>
				<li><a href="#">${vacancyVerify}</a></li>
				<li><a href="#">${interview}</a></li>
			</ul>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${add}</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="Controller" method="post">
					<input type="hidden" name="command" value="add-vacancy">
					<div class="form-group">
						<label class="control-label col-xs-3 col-md-1" for="vacancyName">${vacancyName}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="vacancyName"
								placeholder="${enterVacancyName}" required>
						</div>
						<c:if test="${requestScope.errorVacancyName}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongVacancyName}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="salary">${salary}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="salary"
								placeholder="${enterSalary}" required>
						</div>
						<c:if test="${requestScope.errorSalary}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongSalary}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="military">${currency}</label>
						<div class="col-xs-6">
							<select class="form-control" name="currency">
								<option value="rub">${rub}</option>
								<option value="dolar">${dolar}</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="description">${description}</label>
						<div class="col-xs-6">
							<textarea class="form-control" rows="9" name="description"></textarea>
						</div>
						<c:if test="${requestScope.errorDescription}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongDescription}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="conditions">${conditions}</label>
						<div class="col-xs-6">
							<textarea class="form-control" rows="9" name="conditions"></textarea>
						</div>
						<c:if test="${requestScope.errorConditions}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongConditions}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1"
							for="employmentType">${employmentType}</label>
						<div class="col-xs-6">
							<select class="form-control" name="employmentType">
								<option value="full time">${fullTime}</option>
								<option value="part time">${partTime}</option>
								<option value="contractual">${contractual}</option>
							</select>
						</div>
					</div>
					<div class="left-menu clearfix">
						<input type="submit" class="btn btn-success btn-lg"
							value="${add}">
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>

</body>
</html>