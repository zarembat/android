package kit.financemanager.helpers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.listview.ListViewAdapter;
import kit.financemanager.listview.ListViewItem;
import android.content.Context;
import android.widget.Toast;


public class WriteExcel {

	  private WritableCellFormat timesBoldUnderline;
	  private WritableCellFormat times;
	  private String inputFile;
	  private Context context;
	  private int current_user;
	  private String date;
	  private String expenses;
	  private String revenues;
	  private String balance;
	  
	public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
	  }
	
	public void setContext(Context context) {
		  this.context = context;
		  }
	
	public void setUser(int current_user) {
		  this.current_user = current_user;
		  }
	
	public void setDate(String date) {
		  this.date = date;
		  }
	
	public void setExpenses(String expenses) {
		  this.expenses = expenses;
		  }
	public void setRevenues(String revenues) {
		  this.revenues = revenues;
		  }
	public void setBalance(String balance) {
		  this.balance = balance;
		  }
	  public void write() throws IOException, WriteException, Exception {
		
		  if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
				throw new Exception();
		    }
		  
		  String name = "Report" + date + ".xls";
	      	File dir = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Finance Manager");
		    if (!dir.exists())
		    	dir.mkdirs();
		    File file = new File(dir, name);
	      	WorkbookSettings wbSettings = new WorkbookSettings();
		
		    wbSettings.setLocale(new Locale("en", "EN"));
		    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		    workbook.createSheet("Report", 0);
		    WritableSheet excelSheet = workbook.getSheet(0);
		    createLabel(excelSheet);
		    createContent(excelSheet);
		
		    workbook.write();
		    workbook.close();
	  }
	
	  private void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);
	
	    // create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);
	
	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);
	
	    // Write a few headers
	    addCaption(sheet, 0, 0, "Date");
	    addCaption(sheet, 1, 0, "Operation");
	    addCaption(sheet, 2, 0, "Ammount");
	    addCaption(sheet, 3, 0, "Currency");
	    addCaption(sheet, 4, 0, "Category");
	    addCaption(sheet, 5, 0, "Remarks");
	
	  }
	
	  private void createContent(WritableSheet sheet) throws WriteException,
	      RowsExceededException {
	    
		DatabaseHandler db = new DatabaseHandler(context);  
		List<Expense> expenseList = db.getRaportExpenses(current_user, date, -1);
        List<Revenue> revenueList = db.getRaportRevenues(current_user,date, -1);
        List<Currency> currencies = db.getAllCurrencies();
        List<Operation> operations = new ArrayList<Operation>();
        int counter = 0;

        if(expenseList != null){
        			
			for (Expense ex : expenseList) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = ex.getDate();
				
				ExpenseCategory expenseCategory = db.getExpenseCategory((String)null, ex.getCategoryId(), current_user);
				String currency = currencies.get(ex.getCurrencyId()-1).getName();
				
				Float examount = ex.getAmmount();
				Float examount2 = ex.getAmmount()*2;
				BigDecimal examountbg = new BigDecimal(examount.toString());
				BigDecimal examountbg2 = new BigDecimal(examount2.toString());
				BigDecimal expensebgsum = examountbg.subtract(examountbg2);
				Float exfloat = Float.parseFloat(expensebgsum.toString());
				
				if (counter == 0)
					operations.add(new Operation(expenseCategory.getName(), currency, dateFormat.format(date), exfloat, ex.getRemarks()));
					
				else{
					boolean added = false;
					for (int i = 0; i<operations.size(); i++){
						if (dateFormat.format(date).compareTo(operations.get(i).getDate()) >= 0){
							operations.add(i,new Operation(expenseCategory.getName(), currency, dateFormat.format(date), exfloat, ex.getRemarks()));
							added = true;
							break;
						}
					}
					if (!added)
						operations.add(new Operation(expenseCategory.getName(), currency, dateFormat.format(date), exfloat, ex.getRemarks()));
						
				}
				counter++;
				
			}
			
		}
		
		if(revenueList != null){
			for (Revenue rv : revenueList) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = rv.getDate();
				Float a = rv.getAmmount();
				BigDecimal revenue = new BigDecimal(a.toString());
				Float revfloat = Float.parseFloat(revenue.toString());
				
				RevenueCategory revenueCategory = db.getRevenueCategory((String)null, rv.getCategoryId(), current_user);
				String currency = currencies.get(rv.getCurrencyId()-1).getName();
				
				if (counter == 0)
					operations.add(new Operation(revenueCategory.getName(), currency, dateFormat.format(date), revfloat, rv.getRemarks()));
				else{
					boolean added = false;
					for (int i = 0; i<operations.size(); i++){
						if (dateFormat.format(date).compareTo(operations.get(i).getDate()) >= 0){
							operations.add(i,new Operation(revenueCategory.getName(), currency, dateFormat.format(date), revfloat, rv.getRemarks()));
							added = true;
							break;
						}
					}
					if (!added)
						operations.add(new Operation(revenueCategory.getName(), currency, dateFormat.format(date), revfloat, rv.getRemarks()));
						
				}
				counter++;
				
			}
		}
		
		if (counter != 0){
			for (int i = 0; i < operations.size(); i++) {
				
				addLabel(sheet, 0, i+1, operations.get(i).getDate());
				if (operations.get(i).getAmount()<0)
					addLabel(sheet, 1, i+1, "Expense");
				else
					addLabel(sheet, 1, i+1, "Revenue");
				
				addNumber(sheet, 2, i+1, operations.get(i).getAmount());
				addLabel(sheet, 3, i+1, operations.get(i).getCurrency());
				addLabel(sheet, 4, i+1, operations.get(i).getCategory());
				addLabel(sheet, 5, i+1, operations.get(i).getRemarks());
		    }
		} 

		addLabel(sheet, 0, operations.size()+3, "Expenses:");
		addLabel(sheet, 1, operations.size()+3, expenses);
		addLabel(sheet, 0, operations.size()+4, "Revenues:");
		addLabel(sheet, 1, operations.size()+4, revenues);
		addLabel(sheet, 0, operations.size()+5, "Balance:");
		addLabel(sheet, 1, operations.size()+5, balance);
	  }
	
	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }
	
	  private void addNumber(WritableSheet sheet, int column, int row,
	      Float amount) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, amount, times);
	    sheet.addCell(number);
	  }
	
	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }
}
