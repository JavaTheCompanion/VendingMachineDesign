package vending.machine;

public class NotSufficientChangeException extends RuntimeException {

	private static final long serialVersionUID = 8375545289471896581L;

	private String message;

	public NotSufficientChangeException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
