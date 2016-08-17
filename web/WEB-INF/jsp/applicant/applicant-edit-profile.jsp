<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="EN" />
<c:if test="${sessionScope.locale!=null}">
	<fmt:setLocale value="${sessionScope.locale}" />
</c:if>
<fmt:setBundle basename="resource.locale" var="locale" />
<fmt:message bundle="${locale}"
	key="locale.applicant.office.header.profile" var="profile" />
<fmt:message bundle="${locale}"
	key="locale.applicant.office.header.resume" var="resume" />
<fmt:message bundle="${locale}" key="locale.update.applicant"
	var="update" />
<fmt:message bundle="${locale}" key="locale.update.save" var="save" />
<fmt:message bundle="${locale}" key="locale.update.cancel" var="cancel" />
<fmt:message bundle="${locale}" key="locale.reg.password" var="password" />
<fmt:message bundle="${locale}" key="locale.reg.enterPassword"
	var="enterPassword" />
<fmt:message bundle="${locale}" key="locale.reg.wrongPassword"
	var="wrongPassword" />
<fmt:message bundle="${locale}" key="locale.reg.copypassword"
	var="copypassword" />
<fmt:message bundle="${locale}" key="locale.reg.enterCopypassword"
	var="enterCopypassword" />
<fmt:message bundle="${locale}" key="locale.reg.passwordNotEquals"
	var="passwordNotEquals" />
<fmt:message bundle="${locale}" key="locale.reg.surname" var="surname" />
<fmt:message bundle="${locale}" key="locale.reg.enterSurname"
	var="enterSurname" />
<fmt:message bundle="${locale}" key="locale.reg.wrongSurname"
	var="wrongSurname" />
<fmt:message bundle="${locale}" key="locale.reg.name" var="name" />
<fmt:message bundle="${locale}" key="locale.reg.enterName"
	var="enterName" />
<fmt:message bundle="${locale}" key="locale.reg.wrongName"
	var="wrongName" />
<fmt:message bundle="${locale}" key="locale.reg.secondname"
	var="secondname" />
<fmt:message bundle="${locale}" key="locale.reg.enterSecondname"
	var="enterSecondname" />
<fmt:message bundle="${locale}" key="locale.reg.wrongSecondname"
	var="wrongSecondname" />
<fmt:message bundle="${locale}" key="locale.reg.skype" var="skype" />
<fmt:message bundle="${locale}" key="locale.reg.enterSkype"
	var="enterSkype" />
<fmt:message bundle="${locale}" key="locale.reg.wrongSkype"
	var="wrongSkype" />
<fmt:message bundle="${locale}" key="locale.reg.birthDate"
	var="birthDate" />
<fmt:message bundle="${locale}" key="locale.reg.enterBirthDate"
	var="enterBirthDate" />
<fmt:message bundle="${locale}" key="locale.reg.wrongBirthDate"
	var="wrongBirthDate" />
<fmt:message bundle="${locale}" key="locale.reg.phone" var="phone" />
<fmt:message bundle="${locale}" key="locale.reg.enterPhone"
	var="enterPhone" />
<fmt:message bundle="${locale}" key="locale.reg.wrongPhone"
	var="wrongPhone" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ru">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${update}</title>
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
	<br><br><br><br><br>
	<div class="container">
		<div class="top-nav clearfix">
			<ul class="nav nav-tabs nav-justified">
				<li class="active"><a href="Controller?command=to-applicant-profile">${profile}</a></li>
				<li><a href="Controller?command=to-applicant-list-resume">${resume}</a></li>
			</ul>
		</div>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${update}</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="Controller" method="post">
					<input type="hidden" name="command" value="applicant-edit-profile">
					<input type="hidden" name="email" value="${user.email}">
					<div class="form-group">
						<img src="../images/photo.jpg" alt="..."
							class="img-circle col-xs-4">
						<div class="col-xs-6">
							<button type="button" class="btn btn-success">Добавить</button>
							<button type="button" class="btn btn-danger">Удалить</button>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="password">${password}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="password"
								placeholder="${enterPassword}" required>
						</div>
						<c:if test="${requestScope.errorPassword}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongPassword}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="copypassword">${password}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="copypassword"
								placeholder="${enterCopypassword}" required>
						</div>
						<c:if test="${requestScope.errorPasswordNotEquals}">
							<div class="col-xs-2">
								<p class="text-danger">${passwordNotEquals}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="surname">${surname}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="surname"
								value="${user.surname}" required>
						</div>
						<c:if test="${requestScope.errorSurname}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongSurname}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="name">${name}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="name"
								value="${user.name}" required>
						</div>
						<c:if test="${requestScope.errorName}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongName}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="secondname">${secondname}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="secondname"
								value="${user.secondName }" required>
						</div>
						<c:if test="${requestScope.errorSecondname}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongSecondName}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="skype">${skype}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="skype"
								value="${user.skype}" required>
						</div>
						<c:if test="${requestScope.errorSkype}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongSkype}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="phone">${phone}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="phone"
								value="${user.contactPhone}" required>
						</div>
						<c:if test="${requestScope.errorPhone}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongPhone}</p>
							</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 col-md-1" for="birthDate">${birthDate}</label>
						<div class="col-xs-6">
							<input type="text" class="form-control" name="birthDate"
								value="${user.birthDate}" required>
						</div>
						<c:if test="${requestScope.errorDate}">
							<div class="col-xs-2">
								<p class="text-danger">${wrongBirthDate}</p>
							</div>
						</c:if>
					</div>
					<div class="left-menu clearfix">
						<input type="submit" class="btn btn-success btn-lg"
							value="${save}">
						<button type="button" class="btn btn-danger btn-lg">${cancel}</button>
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