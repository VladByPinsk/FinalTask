package by.training.hrsystem.service.exeption.userexception;

import by.training.hrsystem.service.exeption.ServiceException;

public class ValidateException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

}
