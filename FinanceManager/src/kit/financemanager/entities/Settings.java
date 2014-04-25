package kit.financemanager.entities;

public class Settings {

	private String password;
	private int currency_id;
	
	public String getPasswrod() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getCurrency() {
		return currency_id;
	}
	
	public void setCurrency(int currency_id) {
		this.currency_id = currency_id;
	}
}
