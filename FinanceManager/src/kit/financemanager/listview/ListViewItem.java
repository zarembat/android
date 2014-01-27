package kit.financemanager.listview;

public class ListViewItem {

	private int id;
	private String amount;
	private String category;
    private String remarks;
    private String date;
    private String color;
     
    public ListViewItem(){}
 
    public ListViewItem(int id, String amount, String color, String category ,String remarks, String date){
        this.id = id;
    	this.amount = amount;
        this.category = category;
        this.remarks = remarks;
        this.date = date;
        this.color = color;
    }
     
    public int getId(){
        return this.id;
    }
    
    public String getAmount(){
        return this.amount;
    }
    
    public String getCategory(){
        return this.category;
    }
     
    public String getRemarks(){
        return this.remarks;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setAmount(String amount){
        this.amount = amount;
    }
     
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setRemarks(String remarks){
        this.remarks = remarks;
    }
    
    public void setDate(String date){
        this.date = date;
    }
     
    public void setColor(String color){
        this.color = color;
    }
}
