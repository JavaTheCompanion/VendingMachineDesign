package vending.machine;

public class SoldOutException extends RuntimeException {

	private static final long serialVersionUID = -8983059760204331880L;

	private String message;

	public SoldOutException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
