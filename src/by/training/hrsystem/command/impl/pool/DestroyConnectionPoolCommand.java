package by.training.hrsystem.command.impl.pool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.hrsystem.command.Command;
import by.training.hrsystem.command.constant.CommandField;
import by.training.hrsystem.service.InitConnectionService;
import by.training.hrsystem.service.exeption.ServiceException;
import by.training.hrsystem.service.factory.ServiceFactory;

public class DestroyConnectionPoolCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		InitConnectionService poolService = serviceFactory.getInitPoolService();
		try {
			poolService.destroyConnection();
			response.sendRedirect(CommandField.WELCOME_PAGE);
		} catch (ServiceException e) {
			response.sendError(CommandField.ERROR_CODE_500);
		}
	}

}
