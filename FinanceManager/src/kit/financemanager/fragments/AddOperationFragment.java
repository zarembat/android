package kit.financemanager.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.helpers.CurrencyFormatInputFilter;
import kit.financemanager.spinner.NothingSelectedSpinnerAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddOperationFragment extends Fragment {

	Spinner spinner_operation;
	Spinner spinner_category;
	Spinner spinner_currency;
	EditText date;
	EditText remarks;
	EditText amount;
	Context context;
	Button add;
	int current_user;
	String _currency;
	
	List<String> expense_categories = new ArrayList<String>();
	List<String> revenue_categories = new ArrayList<String>();
	List<ExpenseCategory> allExpenseCategories = new ArrayList<ExpenseCategory>();
	List<RevenueCategory> allRevenueCategories = new ArrayList<RevenueCategory>();
	
	private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD = "EUR"; 
    private SharedPreferences preferences;
    
	ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		current_user = getArguments().getInt("current_user");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_addoperation, container, false);
		getActivity().getActionBar().setTitle(menus[position]);
		context = getActivity().getApplicationContext();
		preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		_currency = preferences.getString(PREFERENCES_TEXT_FIELD, "");
		
		String[] operations = getActivity().getResources().getStringArray(R.array.operations);
		spinner_operation = (Spinner) v.findViewById(R.id.spinner_operations);
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, operations);
		spinner_operation.setAdapter(adapter);
		
		spinner_category = (Spinner) v.findViewById(R.id.spinner_categories);
		spinner_currency = (Spinner) v.findViewById(R.id.spinner_currency);
		
	    DatabaseHandler db = new DatabaseHandler(context);
		allExpenseCategories = db.getAllExpenseCategories(current_user);
		if(allExpenseCategories != null){
			for (ExpenseCategory ec : allExpenseCategories) 
				expense_categories.add(ec.getName().toString());
	    }
		
		allRevenueCategories = db.getAllRevenueCategories(current_user);
		if(allRevenueCategories != null){
			for (RevenueCategory rc : allRevenueCategories) 
				revenue_categories.add(rc.getName().toString());
	    }
		
		List<Currency> currencyList = db.getAllCurrencies();
		List<String> currencySpinner = new ArrayList<String>();
		for (Currency c : currencyList) {
			currencySpinner.add(c.getName().toString());
		}
		
	    spinner_currency = (Spinner) v.findViewById(R.id.spinner_optional_currency);
		adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, currencySpinner);
		spinner_currency.setAdapter(adapter);	
		ArrayAdapter myAdapter = (ArrayAdapter) spinner_currency.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdapter.getPosition(_currency);
		spinner_currency.setSelection(spinnerPosition);
		
		db.close();
		
		spinner_operation.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long arg3) {
                
                Object item = parent.getItemAtPosition(position);
                if (item.toString().equals("Expense")) {
                	
            		adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, expense_categories);
            		spinner_category.setAdapter(adapter);
                }
                if (item.toString().equals("Revenue")) {
                	
            		adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, revenue_categories);
            		spinner_category.setAdapter(adapter);
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
		});
		
		amount  = (EditText) v.findViewById(R.id.editText_amount);
		amount.setTextColor(Color.parseColor("#c9c9c9"));
		
		date  = (EditText) v.findViewById(R.id.editText_date);
		date.setTextColor(Color.parseColor("#c9c9c9"));
		
		remarks  = (EditText) v.findViewById(R.id.editText_remarks);
		remarks.setTextColor(Color.parseColor("#c9c9c9"));
		
		date.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            //To show current date in the datepicker
	            Calendar mcurrentDate=Calendar.getInstance();
	            int mYear=mcurrentDate.get(Calendar.YEAR);
	            int mMonth=mcurrentDate.get(Calendar.MONTH);
	            int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

	            DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(),2, new OnDateSetListener() {                  
	                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

	                	int   day  = datepicker.getDayOfMonth();
	                	int   month= datepicker.getMonth();
	                	int   year = datepicker.getYear();

	                	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                	String formatedDate = new SimpleDateFormat("yyyy-MM-dd").format(datepicker.getCalendarView().getDate());
	                	String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

	    		    	if (formatedDate.compareTo(today) > 0)
	    		    		Toast.makeText(context, "Cannot choose date after " + today, Toast.LENGTH_SHORT).show();
	    		    	else
	    		    		date.setText(formatedDate.toString());
	                }
	            }, mYear, mMonth, mDay);

	            
	            mDatePicker.setTitle("Select date");                
	            mDatePicker.show();  }
	    });
		
		add = (Button) v.findViewById(R.id.add_operation);
		add.setOnClickListener(new View.OnClickListener() {
			   
		    public void onClick(View view) {
		    	
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    		Date _date = null;
				try {
					_date = dateFormat.parse(date.getText().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					    		
		    	if (amount.getText().toString().trim().length() == 0)
		    		Toast.makeText(context, "Amount cannot be empty!", Toast.LENGTH_SHORT).show();
		    	else if (date.getText().toString().trim().length() == 0)
		    		Toast.makeText(context, "Date cannot be empty!", Toast.LENGTH_SHORT).show();
		    	else if (remarks.getText().toString().trim().length() == 0)
		    		Toast.makeText(context, "Remarks cannot be empty!", Toast.LENGTH_SHORT).show();
		    	else{
		    		DatabaseHandler db = new DatabaseHandler(context);
		    		
		    		if (spinner_operation.getSelectedItem().toString().equals("Expense")){
		    			Expense expense = new Expense();
			    		expense.setAmmount(Float.parseFloat(amount.getText().toString()));

						expense.setDate(_date);
						
						Currency currency = null;
						currency = db.getCurrency(spinner_currency.getSelectedItem().toString(), current_user, -1);
						expense.setCurrencyId(currency.getCurrencyId());
			    		
			    		ExpenseCategory expenseCategory = db.getExpenseCategory(spinner_category.getSelectedItem().toString(), -1, current_user);
			    		expense.setCategoryId(expenseCategory.getExpenseCategoryId());
			    		
			    		expense.setRemarks(remarks.getText().toString());
			    		expense.setAuthorId(current_user);
			    		db.addExpense(expense);
			    		Toast.makeText(context, "Expense added!", Toast.LENGTH_SHORT).show();
		    		}
		    		
		    		if (spinner_operation.getSelectedItem().toString().equals("Revenue")){
		    			Revenue revenue = new Revenue();
		    			revenue.setAmmount(Float.parseFloat(amount.getText().toString()));

		    			revenue.setDate(_date);
		    			
		    			Currency currency = null;
						currency = db.getCurrency(spinner_currency.getSelectedItem().toString(), current_user, -1);
						revenue.setCurrencyId(currency.getCurrencyId());
			    		
			    		RevenueCategory revenueCategory = db.getRevenueCategory(spinner_category.getSelectedItem().toString(), -1, current_user);
			    		revenue.setCategoryId(revenueCategory.getRevenueCategoryId());
			    		
			    		revenue.setRemarks(remarks.getText().toString());
			    		revenue.setAuthorId(current_user);
			    		db.addRevenue(revenue);
			    		Toast.makeText(context, "Revenue added!", Toast.LENGTH_SHORT).show();
		    		}
		    		
		    		amount.setText("");
		    		remarks.setText("");
		    		date.setText("");
		    	}
		    	
		    }
		});
				
		amount.setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
		
		
		return v;
	}
}
