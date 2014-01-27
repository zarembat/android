package kit.financemanager.entities;

public class User {
	
	private int userId;
	private String login;
	private String password;
	
	public User(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	

}
