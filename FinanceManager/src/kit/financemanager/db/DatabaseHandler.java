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
import kit.financemanager.entities.User;
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
		
		String CREATE_USERS_TABLE =
				"CREATE TABLE users (" +
				"user_id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"login VARCHAR(50) NOT NULL," +
				"password VARCHAR(50) NOT NULL" +
				");";
		String CREATE_EXPENSES_TABLE = "CREATE TABLE expenses (" +
						"expense_id INTEGER PRIMARY KEY AUTOINCREMENT," +
						"category_id INTEGER NOT NULL," +
						"currency_id INTEGER NOT NULL," +
						"date DATETIME NOT NULL," +
						"ammount FLOAT NOT NULL," +
						"remarks VARCHAR(50)," +
						"author_id INTEGER NOT NULL," +
						"FOREIGN KEY(category_id) REFERENCES expense_categories(expense_category_id)" +
						"FOREIGN KEY(currency_id) REFERENCES currency(currency_id)" +
						"FOREIGN KEY(author_id) REFERENCES users(user_id)" +
					");";
		String CREATE_EXPENSE_CATEGORIES_TABLE = "CREATE TABLE expense_categories (" +
				"expense_category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name VARCHAR(100) NOT NULL," +
				"author_id INTEGER DEFAULT 0," +
				"FOREIGN KEY(author_id) REFERENCES users(user_id)" +
			");";
		String CREATE_REVENUES_TABLE = "CREATE TABLE revenues (" +
				"revenue_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"category_id INTEGER NOT NULL," +
				"currency_id INTEGER NOT NULL," +
				"date DATETIME NOT NULL," +
				"ammount FLOAT NOT NULL," +
				"remarks VARCHAR(50)," +
				"author_id INTEGER NOT NULL," +
				"FOREIGN KEY(category_id) REFERENCES revenue_categories(revenue_category_id)" +
				"FOREIGN KEY(currency_id) REFERENCES currency(currency_id)" +
				"FOREIGN KEY(author_id) REFERENCES users(user_id)" +
			");";
		String CREATE_REVENUE_CATEGORIES_TABLE = "CREATE TABLE revenue_categories (" +
					"revenue_category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name VARCHAR(100) NOT NULL," +
					"author_id INTEGER DEFAULT 0," +
					"FOREIGN KEY(author_id) REFERENCES users(user_id)" +
				");";
		String CREATE_CURRENCY_TABLE = "CREATE TABLE currency (" +
				"currency_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name VARCHAR(100) NOT NULL," +
				"author_id INTEGER DEFAULT 0," +
				"FOREIGN KEY(author_id) REFERENCES users(user_id)" +
			");";
		
		String 	INSERT_EXPENSE_CATEGORY1 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Food',0)";
		String 	INSERT_EXPENSE_CATEGORY2 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Car - fuel',0)";
		String 	INSERT_EXPENSE_CATEGORY3 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Car - other',0)";
		String 	INSERT_EXPENSE_CATEGORY4 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Hobby',0)";
		String 	INSERT_EXPENSE_CATEGORY5 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Entertienment',0)";
		String 	INSERT_EXPENSE_CATEGORY6 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Computer and RTV',0)";
		String 	INSERT_EXPENSE_CATEGORY7 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Home',0)";
		String 	INSERT_EXPENSE_CATEGORY8 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Cloths and footwear',0)";
		String 	INSERT_EXPENSE_CATEGORY9 = "INSERT INTO expense_categories (name,author_id)"+
				"VALUES ('Computer and RTV',0)";
		
		
		String 	INSERT_REVENUE_CATEGORY1 = "INSERT INTO revenue_categories (name,author_id)"+
				"VALUES ('Job',0)";
		String 	INSERT_REVENUE_CATEGORY2 = "INSERT INTO revenue_categories (name,author_id)"+
				"VALUES ('Scholarship',0)";
		String 	INSERT_REVENUE_CATEGORY3 = "INSERT INTO revenue_categories (name,author_id)"+
				"VALUES ('Pension',0)";
		String 	INSERT_REVENUE_CATEGORY4 = "INSERT INTO revenue_categories (name,author_id)"+
				"VALUES ('Gift',0)";
		
		String 	INSERT_CURRENCY1 = "INSERT INTO currency (name,author_id)"+
				"VALUES ('EUR',0)";
		String 	INSERT_CURRENCY2 = "INSERT INTO currency (name,author_id)"+
				"VALUES ('USD',0)";
		String 	INSERT_CURRENCY3 = "INSERT INTO currency (name,author_id)"+
				"VALUES ('GBP',0)";
		String 	INSERT_CURRENCY4 = "INSERT INTO currency (name,author_id)"+
				"VALUES ('PLN',0)";
		
		db.execSQL(CREATE_USERS_TABLE);
		db.execSQL(CREATE_EXPENSES_TABLE);
		db.execSQL(CREATE_EXPENSE_CATEGORIES_TABLE);
		db.execSQL(CREATE_REVENUE_CATEGORIES_TABLE);
		db.execSQL(CREATE_REVENUES_TABLE);
		db.execSQL(CREATE_CURRENCY_TABLE);
		
		db.execSQL(INSERT_EXPENSE_CATEGORY1);
		db.execSQL(INSERT_EXPENSE_CATEGORY2);
		db.execSQL(INSERT_EXPENSE_CATEGORY3);
		db.execSQL(INSERT_EXPENSE_CATEGORY4);
		db.execSQL(INSERT_EXPENSE_CATEGORY5);
		db.execSQL(INSERT_EXPENSE_CATEGORY6);
		db.execSQL(INSERT_EXPENSE_CATEGORY7);
		db.execSQL(INSERT_EXPENSE_CATEGORY8);
		
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

		db.execSQL("DROP TABLE IF EXISTS users");
		db.execSQL("DROP TABLE IF EXISTS expenses");
		db.execSQL("DROP TABLE IF EXISTS revenues");
		db.execSQL("DROP TABLE IF EXISTS expense_categories");
		db.execSQL("DROP TABLE IF EXISTS revenue_categories");
		db.execSQL("DROP TABLE IF EXISTS currency");
        // Create tables again
        onCreate(db);

	}
	
	public void addUser(User user) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("login", user.getLogin());
	    values.put("password", user.getPassword());
	 
	    // Inserting Row
	    db.insert("users", null, values);
	    db.close(); // Closing database connection
	}
	
	public User getUser(String login, String password) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    User user = null;
		Cursor cursor = db.rawQuery("SELECT * FROM users WHERE login=? and password=?", new String[] {String.valueOf(login), String.valueOf(password)});
		if (cursor.moveToFirst()){
			user = new User(cursor.getString(1), cursor.getString(2));
			user.setUserId(Integer.parseInt(cursor.getString(0)));
		}
		db.close();
	    return user;
	}
	
	public boolean checkUser(String login) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM users WHERE login=?", new String[] {String.valueOf(login)});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public List<User> getAllUsers() {
	    List<User> userList = new ArrayList<User>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM users", null);

	    if (cursor.moveToFirst()) {
	        do {
	        	User user = new User(cursor.getString(1), cursor.getString(2));
	        	user.setUserId(Integer.parseInt(cursor.getString(0)));
	            userList.add(user);
	        } while (cursor.moveToNext());
	        
	    }
	    db.close();
	    return userList;
	    
	}
	
	public void updateUserPassword(String password, int id){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("password",password);

		db.update("users", cv, "user_id" + "=" + id, null);
		db.close();
	}	
	
	public void addExpense(Expense expense) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("category_id", expense.getCategoryId());
	    values.put("currency_id", expense.getCurrencyId());
	    values.put("author_id", expense.getAuthorId());
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
	    values.put("author_id", category.getAuthorId());
	    db.insert("expense_categories", null, values);
	    db.close();
	}
	
	public boolean checkExpenseCategory(String name, int author_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM expense_categories WHERE name=? and author_id=?", new String[] {String.valueOf(name).toLowerCase(), String.valueOf(author_id)});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public ExpenseCategory getExpenseCategory(String name, int category_id, int author_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    ExpenseCategory expenseCategory = null;
	    
	    if (category_id != -1){
	    	Cursor cursor = db.rawQuery("SELECT * FROM expense_categories WHERE expense_category_id=?", new String[] {String.valueOf(category_id)});
			if (cursor.moveToFirst()){
				expenseCategory = new ExpenseCategory(cursor.getString(1),Integer.parseInt(cursor.getString(2)));
				expenseCategory.setExpenseCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
	    
	    else if (name != null){
	    	Cursor cursor = db.rawQuery("SELECT * FROM expense_categories WHERE name=? and (author_id=? or author_id=0)", new String[] {String.valueOf(name), String.valueOf(author_id)});
			if (cursor.moveToFirst()){
				expenseCategory = new ExpenseCategory(cursor.getString(1),Integer.parseInt(cursor.getString(2)));
				expenseCategory.setExpenseCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
		
		db.close();
	    return expenseCategory;
	}
	
	public List<ExpenseCategory> getAllExpenseCategories(int author_id) {
	    List<ExpenseCategory> expense_categoriesList = new ArrayList<ExpenseCategory>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM expense_categories where author_id=? or author_id=0", new String[] {String.valueOf(author_id)});
	    if (cursor.moveToFirst()) {
	        do {
	        	ExpenseCategory expense_category = new ExpenseCategory(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
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
	    values.put("author_id", category.getAuthorId());
	    db.insert("revenue_categories", null, values);
	    db.close();
	}

	public void deleteRevenue(int id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete("revenues", "revenue_id" + " = ?",
	            new String[] { String.valueOf(id) });
	    db.close();
	}
	
	public boolean checkRevenueCategory(String name, int author_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM revenue_categories WHERE name=? and author_id=?", new String[] {String.valueOf(name).toLowerCase(), String.valueOf(author_id)});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public RevenueCategory getRevenueCategory(String name, int category_id, int author_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    RevenueCategory revenueCategory = null;
	    
	    if (category_id != -1){
	    	Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories WHERE revenue_category_id=?", new String[] {String.valueOf(category_id)});
			if (cursor.moveToFirst()){
				revenueCategory = new RevenueCategory(cursor.getString(1),Integer.parseInt(cursor.getString(2)));
				revenueCategory.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
	    
	    else if (name != null){
	    	Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories WHERE name=? and (author_id=? or author_id=0)", new String[] {String.valueOf(name), String.valueOf(author_id)});
			if (cursor.moveToFirst()){
				revenueCategory = new RevenueCategory(cursor.getString(1),Integer.parseInt(cursor.getString(2)));
				revenueCategory.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
			}
	    }
		
		db.close();
	    return revenueCategory;
	}
	
	public List<RevenueCategory> getAllRevenueCategories(int author_id) {
	    List<RevenueCategory> revenue_categoriesList = new ArrayList<RevenueCategory>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenue_categories where author_id=? or author_id=0", new String[] {String.valueOf(author_id)});

	    if (cursor.moveToFirst()) {
	        do {
	        	RevenueCategory revenue_category = new RevenueCategory(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
	        	revenue_category.setRevenueCategoryId(Integer.parseInt(cursor.getString(0)));
	        	revenue_categoriesList.add(revenue_category);
	        } while (cursor.moveToNext());
	          
	    }
	    db.close();
	    return revenue_categoriesList;  
	}
	
	public List<Expense> getAllExpenses(int author_id, String date) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM expenses WHERE author_id=? AND date(date)>=date(?)", new String[] {String.valueOf(author_id), date});
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
	
	public List<Expense> getRaportExpenses(int author_id, String date, int currency_id) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	    Cursor cursor = null;
	    SQLiteDatabase db = this.getWritableDatabase();
	    if(currency_id != -1)
	    	cursor = db.rawQuery("SELECT * FROM expenses WHERE author_id=? AND strftime('%Y-%m', date)=strftime('%Y-%m', ?) AND currency_id=?", new String[] {String.valueOf(author_id), String.valueOf(date), String.valueOf(currency_id)});
	    else   
	    	cursor = db.rawQuery("SELECT * FROM expenses WHERE author_id=? AND strftime('%Y-%m', date)=strftime('%Y-%m', ?)", new String[] {String.valueOf(author_id), String.valueOf(date)});
	    
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
	
	public List<Expense> getLastExpenses(int author_id) {
	    List<Expense> expenseList = new ArrayList<Expense>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	   // Cursor cursor = db.rawQuery("SELECT * FROM expenses", null);
	    Cursor cursor = db.rawQuery("SELECT * FROM expenses where author_id=? ORDER BY date DESC, expense_id DESC LIMIT 5", new String[] {String.valueOf(author_id)});

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
	    values.put("author_id", revenue.getAuthorId());
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = revenue.getDate();
	    values.put("date", dateFormat.format(date));
	    
	    values.put("ammount", revenue.getAmmount());
	    values.put("remarks", revenue.getRemarks());
	 
	    // Inserting Row
	    db.insert("revenues", null, values);
	    db.close(); // Closing database connection
	}
	
	public List<Revenue> getAllRevenues(int author_id, String date) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenues where author_id=? and date(date)>=date(?)", new String[] {String.valueOf(author_id), String.valueOf(date)});

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
	
	public List<Revenue> getLastRevenues(int author_id) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery("SELECT * FROM revenues where author_id=? ORDER BY date, revenue_id DESC LIMIT 5", new String[] {String.valueOf(author_id)});

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
	
	public List<Revenue> getRaportRevenues(int author_id, String date, int currency_id) {
	    List<Revenue> revenueList = new ArrayList<Revenue>();
	    Cursor cursor = null;
	    SQLiteDatabase db = this.getWritableDatabase();
	    if (currency_id != -1)
	    	cursor = db.rawQuery("SELECT * FROM revenues WHERE author_id=? AND strftime('%Y-%m', date)=strftime('%Y-%m', ?) AND currency_id=?", new String[] {String.valueOf(author_id), String.valueOf(date), String.valueOf(currency_id)});
	    else
	    	cursor = db.rawQuery("SELECT * FROM revenues WHERE author_id=? AND strftime('%Y-%m', date)=strftime('%Y-%m', ?)", new String[] {String.valueOf(author_id), String.valueOf(date)});
	    
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
	        	currency.setAuthorId(Integer.parseInt(cursor.getString(2)));
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
	    values.put("author_id", currency.getAuthorId());
	 
	    // Inserting Row
	    db.insert("currency", null, values);
	    db.close(); // Closing database connection
	}
	
	public boolean checkCurrency(String name, int author_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT LOWER(name) FROM currency WHERE name=? and author_id=?", new String[] {String.valueOf(name).toLowerCase(), String.valueOf(author_id)});
		if (cursor.moveToFirst()){
			db.close();
			return true;
		}
		db.close();
	    return false;
	}
	
	public Currency getCurrency(String name, int author_id, int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    Currency currency = null;
	    Cursor cursor = null;
	    if(name != null)
	    	cursor = db.rawQuery("SELECT * FROM currency WHERE name=? and (author_id=? or author_id=0)", new String[] {String.valueOf(name), String.valueOf(author_id)});
	    else if(id != -1)
	    	cursor = db.rawQuery("SELECT * FROM currency WHERE currency_id=?", new String[] {String.valueOf(id)});
	    	
	    if (cursor.moveToFirst()){
			currency = new Currency();
			currency.setCurrencyId(Integer.parseInt(cursor.getString(0)));
			currency.setName(cursor.getString(1));
			currency.setAuthorId(Integer.parseInt(cursor.getString(2)));
		}
		db.close();
	    return currency;
	}
}
