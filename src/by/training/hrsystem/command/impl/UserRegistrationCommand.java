package by.training.hrsystem.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import by.training.hrsystem.command.Command;
import by.training.hrsystem.command.constant.Attribute;
import by.training.hrsystem.command.constant.CommandField;
import by.training.hrsystem.command.constant.PageName;
import by.training.hrsystem.command.exception.CommandException;
import by.training.hrsystem.domain.role.Role;
import by.training.hrsystem.service.UserService;
import by.training.hrsystem.service.exeption.ServiceException;
import by.training.hrsystem.service.exeption.userexception.PasswordNotEqualsServiceException;
import by.training.hrsystem.service.exeption.userexception.UserWithThisEmailExistServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongBirthDateServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongEmailServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongNameServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongPasswordServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongPhoneServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongSecondnameServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongSkypeServiceException;
import by.training.hrsystem.service.exeption.userexception.WrongSurnameServiceException;
import by.training.hrsystem.service.factory.ServiceFactory;

public class UserRegistrationCommand implements Command {
	private static final Logger logger = Logger.getLogger(UserRegistrationCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommandException {
		logger.debug("UserRegistrationCommand:execute() start");

		String email = request.getParameter(CommandField.EMAIL);
		String password = request.getParameter(CommandField.PASSWORD);
		String copyPassword = request.getParameter(CommandField.COPY_PASSWORD);
		String surname = request.getParameter(CommandField.SURNAME);
		String name = request.getParameter(CommandField.NAME);
		String secondName = request.getParameter(CommandField.SECOND_NAME);
		String skype = request.getParameter(CommandField.SKYPE);
		String contactPhone = request.getParameter(CommandField.CONTACT_PHONE);
		String birthDate = request.getParameter(CommandField.BIRHT_DATE);
		try {
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			UserService userService = serviceFactory.getUserService();
			userService.registration(email, password, copyPassword, surname, name, secondName, skype, contactPhone,
					birthDate, Role.APPLICNAT);
			request.setAttribute(Attribute.REGISTRATION_SUCCESS, true);
			request.getRequestDispatcher(PageName.INDEX_PAGE).forward(request, response);
		} catch (WrongEmailServiceException e) {
			request.setAttribute(Attribute.ERROR_EMAIL, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("Wrong email");
		} catch (UserWithThisEmailExistServiceException e) {
			request.setAttribute(Attribute.ERROR_ALREDI_EXIST, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("User with this email is aredy exist");
		} catch (WrongPasswordServiceException e) {
			request.setAttribute(Attribute.ERROR_PASSWORD, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("Wrong password");
		} catch (PasswordNotEqualsServiceException e) {
			request.setAttribute(Attribute.ERROR_PASSWORD_NOT_EQUALS, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("Passwod must be equals");
		} catch (WrongSurnameServiceException e) {
			request.setAttribute(Attribute.ERROR_SURNAME, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("Wrong surname");
		} catch (WrongNameServiceException e) {
			request.setAttribute(Attribute.ERROR_NAME, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("wrong name");
		} catch (WrongSecondnameServiceException e) {
			request.setAttribute(Attribute.ERROR_SECONDNAME, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("wrong secondname");
		} catch (WrongSkypeServiceException e) {
			request.setAttribute(Attribute.ERROR_SKYPE, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("wrong skype");
		} catch (WrongPhoneServiceException e) {
			request.setAttribute(Attribute.ERROR_PHONE, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("wrong contcat phone");
		} catch (WrongBirthDateServiceException e) {
			request.setAttribute(Attribute.ERROR_DATE, true);
			request.getRequestDispatcher(PageName.REGISTRATION_PAGE).forward(request, response);
			logger.error("wrong date");
		} catch (ServiceException e) {
			throw new CommandException("command layer");
		}
		logger.debug("UserRegistationCommand:execute() end");
	}
}
