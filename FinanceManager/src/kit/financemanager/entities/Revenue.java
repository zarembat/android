package kit.financemanager.entities;

import java.util.Date;

public class Revenue {

	private int revenueId;
	private int categoryId;
	private int currencyId;
	private Date date;
	private float ammount;
	private String remarks;
	private int authorId;
	
	public int getRevenueId() {
		return revenueId;
	}
	
	public void setRevenueId(int revenueId) {
		this.revenueId = revenueId;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public int getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public float getAmmount() {
		return ammount;
	}
	
	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	
}
