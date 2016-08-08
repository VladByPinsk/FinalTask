package by.training.hrsystem.service.exeption.userexception;

public class WrongPhoneServiceException extends UserServiceException {

	private static final long serialVersionUID = 1L;

	public WrongPhoneServiceException(String message) {
		super(message);
	}

	public WrongPhoneServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
