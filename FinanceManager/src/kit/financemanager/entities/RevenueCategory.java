package kit.financemanager.entities;

public class RevenueCategory {

	private int revenueCategoryId;
	private String name;
	
	public RevenueCategory(String name) {
		this.setName(name);
	}
	
	public int getRevenueCategoryId() {
		return revenueCategoryId;
	}
	
	public void setRevenueCategoryId(int revenueCategoryId) {
		this.revenueCategoryId = revenueCategoryId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
