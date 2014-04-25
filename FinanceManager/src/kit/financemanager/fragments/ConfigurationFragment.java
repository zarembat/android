package kit.financemanager.fragments;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.helpers.CurrencyFormatInputFilter;
import kit.financemanager.helpers.Filter;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationFragment extends Fragment {

	EditText category_expence;
	EditText category_revenue;
	EditText currency;
	Button save;
	Context context;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_configuration, container, false);
		getActivity().getActionBar().setTitle(menus[position]);

		context = getActivity().getApplicationContext();
		
		category_expence  = (EditText) v.findViewById(R.id.editText_newCategoryExpense);
		category_expence.setTextColor(Color.parseColor("#c9c9c9"));
		
		category_revenue  = (EditText) v.findViewById(R.id.editText_newCategoryRevenue);
		category_revenue.setTextColor(Color.parseColor("#c9c9c9"));
		
		currency  = (EditText) v.findViewById(R.id.editText_newCurrency);
		currency.setTextColor(Color.parseColor("#c9c9c9"));
		currency.setFilters(new InputFilter[] {new Filter()});
		
		save = (Button) v.findViewById(R.id.save_changes);
		save.setOnClickListener(new View.OnClickListener() {
		   
		    public void onClick(View view) {
		    	addExpenseCategory();
		    	addRevenueCategory();
		    	addNewCurrency();
		    }
		});
		
		return v;
	}
	
public void addExpenseCategory(){
		
		if (category_expence.getText().toString().trim().length() != 0){
			DatabaseHandler db = new DatabaseHandler(context);
			if(!db.checkExpenseCategory(category_expence.getText().toString())){
				ExpenseCategory expenseCategory = new ExpenseCategory(category_expence.getText().toString());
				db.addExpenseCategory(expenseCategory);
				Toast.makeText(context, "Expense category added!", Toast.LENGTH_SHORT).show();
				category_expence.setText("");
				db.close();
			}
			else
				Toast.makeText(context, "Such expense category exists!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void addRevenueCategory(){
			
		if (category_revenue.getText().toString().trim().length() != 0){
			DatabaseHandler db = new DatabaseHandler(context);
			if(!db.checkRevenueCategory(category_revenue.getText().toString())){
				RevenueCategory revenueCategory = new RevenueCategory(category_revenue.getText().toString());
				db.addRevenueCategory(revenueCategory);
				Toast.makeText(context, "Revenue category added!", Toast.LENGTH_SHORT).show();
				db.close();
				category_revenue.setText("");
			}
			else
				Toast.makeText(context, "Such revenue category exists!", Toast.LENGTH_SHORT).show();
		}
	}

	public void addNewCurrency(){
		
		if (currency.getText().toString().trim().length() != 0){
			DatabaseHandler db = new DatabaseHandler(context);
			if(!db.checkCurrency(currency.getText().toString())){
				Currency new_currency = new Currency();
				new_currency.setName(currency.getText().toString());
				db.addCurrency(new_currency);
				Toast.makeText(context, "Currency added!", Toast.LENGTH_SHORT).show();
				currency.setText("");
				db.close();
			}
			else
				Toast.makeText(context, "Such currency exists!", Toast.LENGTH_SHORT).show();
		}
	}
	
}
