package by.training.hrsystem.command.impl.hr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.hrsystem.command.Command;
import by.training.hrsystem.command.constant.Attribute;
import by.training.hrsystem.command.constant.PageName;
import by.training.hrsystem.command.exception.CommandException;
import by.training.hrsystem.domain.User;
import by.training.hrsystem.service.VacancyService;
import by.training.hrsystem.service.exeption.ServiceException;
import by.training.hrsystem.service.exeption.vacancy.WrongConditionsServiceException;
import by.training.hrsystem.service.exeption.vacancy.WrongDescriptionServiceException;
import by.training.hrsystem.service.exeption.vacancy.WrongSalaryServiceException;
import by.training.hrsystem.service.exeption.vacancy.WrongVacancyNameServiceException;
import by.training.hrsystem.service.factory.ServiceFactory;

public class AddVacancyCommand implements Command {

	private static final Logger logger = LogManager.getRootLogger();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException, ServletException, IOException {
		logger.debug("addVacancyCommand:execute() start");

		User hrEmail = (User) request.getSession().getAttribute(Attribute.USER);

		String vacancyName = request.getParameter(Attribute.VACANCY_NAME);
		String salary = request.getParameter(Attribute.SALARY);
		String currency = request.getParameter(Attribute.CURRENCY);
		String description = request.getParameter(Attribute.DESCRIPTION);
		String conditions = request.getParameter(Attribute.CONDITIONS);
		String employmentType = request.getParameter(Attribute.EMPLOYMENT_TYPE);

		try {
			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			VacancyService vacancyService = serviceFactory.getVacancyService();
			vacancyService.addVacancy(vacancyName, salary, currency, description, conditions, employmentType,
					hrEmail.getEmail());
			request.setAttribute(Attribute.ADD_VACANCY_SUCCESS, true);
			request.getRequestDispatcher(PageName.HR_ADD_VACANCY_PAGE).forward(request, response);
		} catch (WrongVacancyNameServiceException e) {
			request.setAttribute(Attribute.ERROR_VACANCY_NAME, true);
			request.getRequestDispatcher(PageName.HR_ADD_VACANCY_PAGE).forward(request, response);
			logger.error("wrong vacancy name");
		} catch (WrongSalaryServiceException e) {
			request.setAttribute(Attribute.ERROR_SALARY, true);
			request.getRequestDispatcher(PageName.HR_ADD_VACANCY_PAGE).forward(request, response);
			logger.error("wrong salary");
		} catch (WrongDescriptionServiceException e) {
			request.setAttribute(Attribute.ERROR_DESCRIPTION, true);
			request.getRequestDispatcher(PageName.HR_ADD_VACANCY_PAGE).forward(request, response);
			logger.error("wrong description");
		} catch (WrongConditionsServiceException e) {
			request.setAttribute(Attribute.ERROR_CONDITIONS, true);
			request.getRequestDispatcher(PageName.HR_ADD_VACANCY_PAGE).forward(request, response);
			logger.error("wrong condotions");
		} catch (ServiceException e) {
			throw new CommandException("Command layer");
		}

		logger.debug("addVacancyCommand:execute() end");
	}

}
