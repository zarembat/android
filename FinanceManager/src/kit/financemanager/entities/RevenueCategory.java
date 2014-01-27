package kit.financemanager.entities;

public class RevenueCategory {

	private int revenueCategoryId;
	private String name;
	private int authorId;
	
	public RevenueCategory(String name, int id) {
		this.setName(name);
		this.setAuthorId(id);
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
	
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	
}
