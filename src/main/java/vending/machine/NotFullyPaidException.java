package vending.machine;

public class NotFullyPaidException extends RuntimeException {

	private static final long serialVersionUID = -757736670415550327L;
	
	private String message;
	private long remaining;

	public NotFullyPaidException(String message, long remaining) {
		super();
		this.message = message;
		this.remaining = remaining;
	}

	public long getRemaining() {
		return this.remaining;
	}

	@Override
	public String getMessage() {
		return this.message + this.remaining;
	}

}
