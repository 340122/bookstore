package cn.itcast.itcaststore.exception;

public class LoginException extends Exception {

	/**
	 * 登录的用户名和密码不正确时，抛出此异常
	 */
	private static final long serialVersionUID = 1L;

	public LoginException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
