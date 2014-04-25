package kit.financemanager.db;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "FinanceManager.db";
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {		
		
		String CREATE_EXPENSES_TABLE = "CREATE TABLE expenses (" +
						"expense_id INTEGER PRIMARY KEY AUTOINCREMENT," +
						"category_id INTEGER NOT NULL," +
						"currency_id INTEGER NOT NULL," +
						"date DATETIME NOT NULL," +
						"ammount FLOAT NOT NULL," +
						"remarks VARCHAR(50)," +
						"FOREIGN KEY(category_id) REFERENCES expense_categories(expense_category_id)" +
						"FOREIGN KEY(currency_id) REFERENCES currency(currency_id)" +
					");";
		String CREATE_EXPENSE_CATEGORIES_TABLE = "CREATE TABLE expense_categories (" +
				"expense_category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name VARCHAR(100) NOT NULL" +
			");";
		String CREATE_REVENUES_TABLE = "CREATE TABLE revenues (" +
				"revenue_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"category_id INTEGER NOT NULL," +
				"currency_id INTEGER NOT NULL," +
				"date DATETIME NOT NULL," +
				"ammount FLOAT NOT NULL," +
				"remarks VARCHAR(50)," +
				"FOREIGN KEY(category_id) REFERENCES revenue_categories(revenue_category_id)" +
				"FOREIGN KEY(currency_id) REFERENCES currency(currency_id)" +
			");";
		String CREATE_REVENUE_CATEGORIES_TABLE = "CREATE TABLE revenue_categories (" +
					"revenue_category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name VARCHAR(100) NOT NULL" +
				");";
		String CREATE_CURRENCY_TABLE = "CREATE TABLE currency (" +
				"currency_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name VARCHAR(100) NOT NULL" +
			");";
		
		String CREATE_SETTINGS_TABLE = "CREATE TABLE settings (" +
				"settings_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"currency_id INTEGER NOT NULL," +
				"password VARCHAR(100) NOT NULL," +
				"FOREIGN KEY(currency_id) REFERENCES currency(currency_id)" +
			");";
		
		String 	INSERT_EXPENSE_CATEGORY1 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Food')";
		String 	INSERT_EXPENSE_CATEGORY2 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Car - fuel')";
		String 	INSERT_EXPENSE_CATEGORY3 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Car - other')";
		String 	INSERT_EXPENSE_CATEGORY4 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Hobby')";
		String 	INSERT_EXPENSE_CATEGORY5 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Entertienment')";
		String 	INSERT_EXPENSE_CATEGORY6 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Computer and RTV')";
		String 	INSERT_EXPENSE_CATEGORY7 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Home')";
		String 	INSERT_EXPENSE_CATEGORY8 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Cloths and footwear')";
		String 	INSERT_EXPENSE_CATEGORY9 = "INSERT INTO expense_categories (name)"+
				"VALUES ('Computer and RTV')";
		
		
		String 	INSERT_REVENUE_CATEGORY1 = "INSERT INTO revenue_categories (name)"+
				"VALUES ('Job')";
		String 	INSERT_REVENUE_CATEGORY2 = "INSERT INTO revenue_categories (name)"+
				"VALUES ('Scholarship')";
		String 	INSERT_REVENUE_CATEGORY3 = "INSERT INTO revenue_categories (name)"+
				"VALUES ('Pension')";
		String 	INSERT_REVENUE_CATEGORY4 = "INSERT INTO revenue_categories (name)"+
				"VALUES ('Gift')";
		
		String 	INSERT_CURRENCY1 = "INSERT INTO currency (name)"+
				"VALUES ('EUR')";
		String 	INSERT_CURRENCY2 = "INSERT INTO currency (name)"+
				"VALUES ('USD')";
		String 	INSERT_CURRENCY3 = "INSERT INTO currency (name)"+
				"VALUES ('GBP')";
		String 	INSERT_CURRENCY4 = "INSERT INTO currency (name)"+
				"VALUES ('PLN')";
		
		db.execSQL(CREATE_EXPENSES_TABLE);
		db.execSQL(CREATE_EXPENSE_CATEGORIES_TABLE);
		db.execSQL(CREATE_REVENUE_CATEGORIES_TABLE);
		db.execSQL(CREATE_REVENUES_TABLE);
		db.execSQL(CREATE_CURRENCY_TABLE);
		db.execSQL(CREATE_SETTINGS_TABLE);
		
		db.execSQL(INSERT_EXPENSE_CATEGORY1);
		db.execSQL(INSERT_EXPENSE_CATEGORY2);
		db.execSQL(INSERT_EXPENSE_CATEGORY3);
		db.execSQL(INSERT_EXPENSE_CATEGORY4);
		db.execSQL(INSERT_EXPENSE_CATEGORY5);
		db.execSQL(INSERT_EXPENSE_CATEGORY6);
		db.execSQL(INSERT_EXPENSE_CATEGORY7);
		db.execSQL(INSERT_EXPENSE_CATEGORY8);
		db.execSQL(INSERT_EXPENSE_CATEGORY9);
		
		db.execSQL(INSERT_REVENUE_CATEGORY1);
		db.execSQL(INSERT_REVENUE_CATEGORY2);
		db.execSQL(INSERT_REVENUE_CATEGORY3);
		db.execSQL(INSERT_REVENUE_CATEGORY4);
		
		db.execSQL(INSERT_CURRENCY1);
		db.execSQL(INSERT_CURRENCY2);
		db.execSQL(INSERT_CURRENCY3);
		db.execSQL(INSERT_CURRENCY4);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS expenses");
		db.execSQL("DROP TABLE IF EXISTS revenues");
		db.execSQL("DROP TABLE IF EXISTS expense_categories");
		db.execSQL("DROP TABLE IF EXISTS revenue_categories");
		db.execSQL("DROP TABLE IF EXISTS currency");
        // Create tables again
        onCreate(db);

	}
		
	
	public void addExpense(Expense expense) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("category_id", expense.getCategoryId());
	    values.put("currency_id", expense.getCurrencyId());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String date = sdf.format(expense.getDate());
	    values.put("date", date);
	    
	    values.put("ammount", expense.getAmmount());
	    values.put("remarks", expense.getRemarks());
	 
	    // Inserting Row
	    db.insert("expenses", null, values);
	    db.close(); // Closing database connection
	}
	

	
	public void deleteExpense(int id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("expenses", "expense_id" + " = ?",
	            new String[] { String.valueOf(id) });
	    db.close();
	}
	
	public void addExpenseCategory(ExpenseCategory category) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("name", category.getName());	    
	    db.insert("expense_categories", null, values);
	    db.close();
	}
	
	public boolean checkExpenseCategory(String name) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM expense_categories WHERE name=?", new String[] {String.valueOf(name).toLowerCase()});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public ExpenseCategory getExpenseCategory(String name, int category_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    ExpenseCategory expenseCategory = null;
	    
	    if (category_id != -1){
	    	Cursor cursor = db.rawQuery("SELECT * FROM expense_categories WHERE expense_category_id=?", new String[] {String.valueOf(category_id)});
			if (cursor.moveToFirst()){
				expenseCategory = new ExpenseCategory(cursor.getString(1));
				expenseCategory.setExpenseCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
	    
	    else if (name != null){
	    	Cursor cursor = db.rawQuery("SELECT * FROM expense_categories WHERE name=?", new String[] {String.valueOf(name)});
			if (cursor.moveToFirst()){
				expenseCategory = new ExpenseCategory(cursor.getString(1));
				expenseCategory.setExpenseCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
		
		db.close();
	    return expenseCategory;
	}
	
	public List<ExpenseCategory> getAllExpenseCategories(int author_id) {
	    List<ExpenseCategory> expense_categoriesList = new ArrayList<ExpenseCategory>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM expense_categories", null);
	    if (cursor.moveToFirst()) {
	        do {
	        	ExpenseCategory expense_category = new ExpenseCategory(cursor.getString(1));
	        	expense_category.setExpenseCategoryId(Integer.parseInt(cursor.getString(0)));
	        	expense_categoriesList.add(expense_category);
	        } while (cursor.moveToNext());
	        
	        
	    }
	    db.close();
	    return expense_categoriesList;  
	}
	
	public void addRevenueCategory(RevenueCategory category) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("name", category.getName());	    
	    db.insert("revenue_categories", null, values);
	    db.close();
	}

	public void deleteRevenue(int id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("revenues", "revenue_id" + " = ?",
	            new String[] { String.valueOf(id) });
	    db.close();
	}
	
	public boolean checkRevenueCategory(String name) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM revenue_categories WHERE name=?", new String[] {String.valueOf(name).toLowerCase()});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public RevenueCategory getRevenueCategory(String name, int category_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    RevenueCategory revenueCategory = null;
	    
	    if (category_id != -1){
	    	Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories WHERE revenue_category_id=?", new String[] {String.valueOf(category_id)});
			if (cursor.moveToFirst()){
				revenueCategory = new RevenueCategory(cursor.getString(1));
				revenueCategory.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
	    
	    else if (name != null){
	    	Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories WHERE name=?", new String[] {String.valueOf(name)});
			if (cursor.moveToFirst()){
				revenueCategory = new RevenueCategory(cursor.getString(1));
				revenueCategory.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
		
		db.close();
	    return revenueCategory;
	}
	
	public List<RevenueCategory> getAllRevenueCategories() {
	    List<RevenueCategory> revenue_categoriesList = new ArrayList<RevenueCategory>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories", null);

	    if (cursor.moveToFirst()) {
	        do {
	        	RevenueCategory revenue_category = new RevenueCategory(cursor.getString(1));
	        	revenue_category.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
	        	revenue_categoriesList.add(revenue_category);
	        } while (cursor.moveToNext());
	          
	    }
	    db.close();
	    return revenue_categoriesList;  
	}
	
	public List<Expense> getAllExpenses(String date) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM expenses WHERE date(date)>=date(?)", new String[] {date});
	    if (cursor.moveToFirst()) {
	        do {
	        	Expense expense = new Expense();
	        	expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
	        	expense.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	expense.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date _date;
				try {
					_date = dateFormat.parse(cursor.getString(3));
					expense.setDate(_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	        	
	        	expense.setAmmount(Float.parseFloat(cursor.getString(4)));
	        	expense.setRemarks(cursor.getString(5));
	            // Adding contact to list
	            expenseList.add(expense);
	        } while (cursor.moveToNext());
	        
	    }
	 
	    db.close();
	    return expenseList;	    
	}
	
	public List<Expense> getRaportExpenses(String date, int currency_id) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	    Cursor cursor = null;
	    SQLiteDatabase db = this.getWritableDatabase();
	    if(currency_id != -1)
	    	cursor = db.rawQuery("SELECT * FROM expenses WHERE strftime('%Y-%m', date)=strftime('%Y-%m', ?) AND currency_id=?", new String[] {String.valueOf(date), String.valueOf(currency_id)});
	    else   
	    	cursor = db.rawQuery("SELECT * FROM expenses WHERE strftime('%Y-%m', date)=strftime('%Y-%m', ?)", new String[] {String.valueOf(date)});
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	Expense expense = new Expense();
	        	expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
	        	expense.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	expense.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date _date;
				try {
					_date = dateFormat.parse(cursor.getString(3));
					expense.setDate(_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	        	
	        	expense.setAmmount(Float.parseFloat(cursor.getString(4)));
	        	expense.setRemarks(cursor.getString(5));
	            // Adding contact to list
	            expenseList.add(expense);
	        } while (cursor.moveToNext());
	        
	    }
	 
	    db.close();
	    return expenseList;	    
	}
	
	public List<Expense> getLastExpenses(int limit) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	   // Cursor cursor = db.rawQuery("SELECT * FROM expenses", null);
	    Cursor cursor = db.rawQuery("SELECT * FROM expenses ORDER BY date DESC, expense_id DESC LIMIT 5", null);

	    if (cursor.moveToFirst()) {
	        do {
	        	Expense expense = new Expense();
	        	expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
	        	expense.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	expense.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date date;
				try {
					date = dateFormat.parse(cursor.getString(3));
					expense.setDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	        	
	        	expense.setAmmount(Float.parseFloat(cursor.getString(4)));
	        	expense.setRemarks(cursor.getString(5));
	            // Adding contact to list
	            expenseList.add(expense);
	        } while (cursor.moveToNext());
	        
	    }
	 
	    db.close();
	    return expenseList;	    
	}
	
	public void addRevenue(Revenue revenue) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("category_id", revenue.getCategoryId());
	    values.put("currency_id", revenue.getCurrencyId());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = revenue.getDate();
	    values.put("date", dateFormat.format(date));
	    
	    values.put("ammount", revenue.getAmmount());
	    values.put("remarks", revenue.getRemarks());
	 
	    // Inserting Row
	    db.insert("revenues", null, values);
	    db.close(); // Closing database connection
	}
	
	public List<Revenue> getAllRevenues(String date) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenues where date(date)>=date(?)", new String[] {String.valueOf(date)});

	    if (cursor.moveToFirst()) {
	        do {
	        	Revenue revenue = new Revenue();
	        	revenue.setRevenueId(Integer.parseInt(cursor.getString(0)));
	        	revenue.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	revenue.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date _date;
				try {
					_date = dateFormat.parse(cursor.getString(3));
					revenue.setDate(_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	revenue.setAmmount(Float.parseFloat(cursor.getString(4)));
	        	revenue.setRemarks(cursor.getString(5));
	            revenueList.add(revenue);
	        } while (cursor.moveToNext());
	        
	        
	    }
	    db.close();
	    return revenueList;  
	}
	
	public List<Revenue> getLastRevenues(int limit) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenues ORDER BY date, revenue_id DESC LIMIT 5", null);

	    if (cursor.moveToFirst()) {
	        do {
	        	Revenue revenue = new Revenue();
	        	revenue.setRevenueId(Integer.parseInt(cursor.getString(0)));
	        	revenue.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	revenue.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date date;
				try {
					date = dateFormat.parse(cursor.getString(3));
					revenue.setDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
	        	revenue.setAmmount(Float.parseFloat(cursor.getString(4)));
	        	revenue.setRemarks(cursor.getString(5));
	            revenueList.add(revenue);
	        } while (cursor.moveToNext());
	        
	    }
	    db.close();
	    return revenueList;  
	}
	
	public List<Revenue> getRaportRevenues(String date, int currency_id) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	    Cursor cursor = null;
	    SQLiteDatabase db = this.getWritableDatabase();
	    if (currency_id != -1)
	    	cursor = db.rawQuery("SELECT * FROM revenues WHERE strftime('%Y-%m', date)=strftime('%Y-%m', ?) AND currency_id=?", new String[] {String.valueOf(date), String.valueOf(currency_id)});
	    else
	    	cursor = db.rawQuery("SELECT * FROM revenues WHERE strftime('%Y-%m', date)=strftime('%Y-%m', ?)", new String[] {String.valueOf(date)});
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	Revenue revenue = new Revenue();
	        	revenue.setRevenueId(Integer.parseInt(cursor.getString(0)));
	        	revenue.setCategoryId(Integer.parseInt(cursor.getString(1)));
	        	revenue.setCurrencyId(Integer.parseInt(cursor.getString(2)));
	        	
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date _date;
				try {
					_date = dateFormat.parse(cursor.getString(3));
					revenue.setDate(_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	        	
				revenue.setAmmount(Float.parseFloat(cursor.getString(4)));
				revenue.setRemarks(cursor.getString(5));
	            // Adding contact to list
				revenueList.add(revenue);
	        } while (cursor.moveToNext());
	        
	    }
	 
	    db.close();
	    return revenueList;	    
	}
	
	public List<Currency> getAllCurrencies() {
	    List<Currency> currencyList = new ArrayList<Currency>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM currency", null);

	    if (cursor.moveToFirst()) {
	        do {
	        	Currency currency = new Currency();
	        	currency.setCurrencyId(Integer.parseInt(cursor.getString(0)));
	        	currency.setName(cursor.getString(1));
	        	currencyList.add(currency);
	        } while (cursor.moveToNext());
	        
	        
	    }
	    db.close();
	    return currencyList;  
	}
	
	public void addCurrency(Currency currency) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("name", currency.getName());
	 
	    // Inserting Row
	    db.insert("currency", null, values);
	    db.close(); // Closing database connection
	}
	
	public boolean checkCurrency(String name) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM currency WHERE name=?", new String[] {String.valueOf(name).toLowerCase()});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public Currency getCurrency(String name, int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    Currency currency = null;
	    Cursor cursor = null;
	    if(name != null)
	    	cursor = db.rawQuery("SELECT * FROM currency WHERE name=?", new String[] {String.valueOf(name)});
	    else if(id != -1)
	    	cursor = db.rawQuery("SELECT * FROM currency WHERE currency_id=?", new String[] {String.valueOf(id)});
	    	
	    if (cursor.moveToFirst()){
			currency = new Currency();
			currency.setCurrencyId(Integer.parseInt(cursor.getString(0)));
			currency.setName(cursor.getString(1));
		}
		db.close();
	    return currency;
	}
	
	public String getPassword() {
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = null;
	    String password = null;

	    cursor = db.rawQuery("SELECT password FROM settings", null);
	    if (cursor.moveToFirst()){
			password = cursor.getString(0);
		}
		db.close();
	    return password;
	}
}
