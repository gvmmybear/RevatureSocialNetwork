package zook.model;

public class PasswordResetRequestModel {

	private String password;
	private String token;
	
	public PasswordResetRequestModel(String password, String token) {
		super();
		this.password = password;
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "PasswordResetRequestModel [password=" + password + ", token=" + token + "]";
	}

	
}
