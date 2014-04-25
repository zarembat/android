package kit.financemanager.entities;

public class ExpenseCategory {

	private int expenseCategoryId;
	private String name;
	
	public ExpenseCategory(String name) {
		this.setName(name);
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
	
}
