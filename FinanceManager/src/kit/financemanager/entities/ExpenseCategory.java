package kit.financemanager.entities;

public class ExpenseCategory {

	private int expenseCategoryId;
	private String name;
	private int authorId;
	
	public ExpenseCategory(String name, int id) {
		this.setName(name);
		this.setAuthorId(id);
	}
	
	public int getExpenseCategoryId() {
		return expenseCategoryId;
	}
	
	public void setExpenseCategoryId(int expenseCategoryId) {
		this.expenseCategoryId = expenseCategoryId;
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
