package by.training.hrsystem.service.exeption.userexception;

public class WrongSurnameServiceException extends UserServiceException{
	private static final long serialVersionUID = 1L;

	public WrongSurnameServiceException(String message) {
		super(message);
	}

	public WrongSurnameServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
