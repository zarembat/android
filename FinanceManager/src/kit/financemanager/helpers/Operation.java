package kit.financemanager.helpers;

public class Operation {

	public String category;
	public String currency;
	public String date;
	public Float amount;
	public String remarks;

	public Operation(String category, String currency, String date, Float amount, String remarks)
	{
		this.category = category;
		this.currency = currency;
		this.date = date;
		this.amount = amount;
		this.remarks = remarks;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Float getAmount() {
		return amount;
	}

	public void setDAmount(Float amount) {
		this.amount = amount;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
		
}
