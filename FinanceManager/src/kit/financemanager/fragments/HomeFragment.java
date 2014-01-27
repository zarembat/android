package kit.financemanager.fragments;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.db.GetOperationsAsyncTask;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.Revenue;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment{

	Context context;
	TextView months;
	public TextView expenses;
	public TextView revenues;
	public TextView balance;
	int current_user;
	
	private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD = "EUR"; 
    private SharedPreferences preferences;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		current_user = getArguments().getInt("current_user");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		getActivity().getActionBar().setTitle(menus[position]);
		
		context = getActivity().getApplicationContext();
		preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		String currency = preferences.getString(PREFERENCES_TEXT_FIELD, "");
		if(currency.equals("")){
			SharedPreferences.Editor preferencesEditor = preferences.edit();
			 preferencesEditor.putString(PREFERENCES_TEXT_FIELD, "EUR");
			 preferencesEditor.commit();
			 currency = preferences.getString(PREFERENCES_TEXT_FIELD, "");
		}
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;	
		String date = null;
		if (month <10)
			date = year+"-"+"0"+month+"-"+"01";
		else
			date = year+"-"+month+"-"+"01";
				
		String textmonth = new DateFormatSymbols(Locale.ENGLISH).getMonths()[month-1];
		months = (TextView) v.findViewById(R.id.textView_month);
		months.setText(textmonth);
		
		expenses = (TextView) v.findViewById(R.id.text_expenses);
		revenues = (TextView) v.findViewById(R.id.text_revenues);
		balance = (TextView) v.findViewById(R.id.text_balance);
		
		new GetOperationsAsyncTask(getActivity(), this).execute(current_user, date);
		
		return v;
	}
	
	public HashMap<String, Float> sumExpenses(List<Expense> expenseList, List<Currency> currencies){
		
		HashMap<String, Float> expenses_sum = new HashMap<String, Float>();
		
		if(expenseList.size() != 0){
			for (Expense ex : expenseList) {
				int index = ex.getCurrencyId() - 1;
				String currency_name = currencies.get(index).getName();
				if (expenses_sum.get(currency_name) != null)
					expenses_sum.put(currency_name, expenses_sum.get(currency_name) + ex.getAmmount());
				else
					expenses_sum.put(currency_name, ex.getAmmount());
			}
			
			
		return expenses_sum;
	    }
		
		else
			return null;
	}
	
	public String textExpenses(List<Expense> expenseList, List<Currency> currencies){
		
		
		HashMap<String, Float> expenses_sum = sumExpenses(expenseList, currencies);
		
		if(expenses_sum != null){
			
			String exp_sum = "";
			
			for ( String key : expenses_sum.keySet() ) {
				if (exp_sum.equals(""))
					exp_sum = expenses_sum.get(key) + " " + key;
				else
					exp_sum += "\n" + expenses_sum.get(key) + " " + key;
			}
			
		return exp_sum;
	    }
		
		else
			return "0";
	}
	
	public HashMap<String, Float> sumRevenues(List<Revenue> revenueList, List<Currency> currencies){
		
		HashMap<String, Float> revenues_sum = new HashMap<String, Float>();
		
		if(revenueList.size() != 0){
			for (Revenue rv : revenueList) {
				int index = rv.getCurrencyId() - 1;
				String currency_name = currencies.get(index).getName();
				if (revenues_sum.get(currency_name) != null)
					revenues_sum.put(currency_name, revenues_sum.get(currency_name) + rv.getAmmount());
				else
					revenues_sum.put(currency_name, rv.getAmmount());
			}
			
			
		return revenues_sum;
	    }
		
		else
			return null;
	}
	
	public String textRevenues(List<Revenue> revenueList, List<Currency> currencies){
		
		
		HashMap<String, Float> revenues_sum = sumRevenues(revenueList, currencies);
		
		if(revenues_sum != null){
			
			String rev_sum = "";
			
			for ( String key : revenues_sum.keySet() ) {
				if (rev_sum.equals(""))
					rev_sum = revenues_sum.get(key) + " " + key;
				else
					rev_sum += "\n" + revenues_sum.get(key) + " " + key;
			}
			
		return rev_sum;
	    }
		
		else
			return "0";
	}
	
	public HashMap<String, Float> sumBalance(List<Expense> expenseList,List<Revenue> revenueList, List<Currency> currencies){
		
		HashMap<String, Float> expenses_sum = sumExpenses(expenseList, currencies);
		HashMap<String, Float> revenues_sum = sumRevenues(revenueList, currencies);
		HashMap<String, Float> balance_sum = new HashMap<String, Float>();
		
		if(revenues_sum != null && expenses_sum != null){
			for (int i = 0; i<currencies.size(); i++){
				
				String currency_name = currencies.get(i).getName();
				
				if (revenues_sum.get(currency_name) != null && expenses_sum.get(currency_name) != null){
					
					BigDecimal ex = new BigDecimal(expenses_sum.get(currency_name).toString());
					BigDecimal rv = new BigDecimal(revenues_sum.get(currency_name).toString());
					Float sum = Float.parseFloat(rv.subtract(ex).toString());
					
					balance_sum.put(currency_name, sum);
				}
				else if (revenues_sum.get(currency_name) != null)	{
					
					balance_sum.put(currency_name, revenues_sum.get(currency_name));
				}
				
				else if (expenses_sum.get(currency_name) != null)	{
					
					Float expense = expenses_sum.get(currency_name) - 2*expenses_sum.get(currency_name);
					balance_sum.put(currency_name, expense);
				}
				
			}
			return balance_sum;
		}
		
		else if(expenses_sum != null){
			for (int i = 0; i<currencies.size(); i++){
				
				String currency_name = currencies.get(i).getName();
				if (expenses_sum.get(currency_name) != null){
					Float expense = expenses_sum.get(currency_name) - 2*expenses_sum.get(currency_name);
					balance_sum.put(currency_name, expense);
				}
				
			}
			return balance_sum;
		}
		
		else if(revenues_sum != null){
			for (int i = 0; i<currencies.size(); i++){
				
				String currency_name = currencies.get(i).getName();
				if (revenues_sum.get(currency_name) != null)
					balance_sum.put(currency_name, revenues_sum.get(currency_name));
				
			}
			return balance_sum;
		}
		
		else
			return null;
		
	}
	
	public String textBalance(List<Expense> expenseList,List<Revenue> revenueList, List<Currency> currencies){
		
		HashMap<String, Float> balance_sum = sumBalance(expenseList, revenueList, currencies);
		
		if (balance_sum != null){
			String balance = "";
			for (int i = 0; i<currencies.size(); i++){
			
				String currency_name = currencies.get(i).getName();
				if (balance_sum.get(currency_name) != null){
					if (balance.equals(""))
						balance = balance_sum.get(currency_name) + " " + currency_name;
					else
						balance += "\n" + balance_sum.get(currency_name) + " " + currency_name;
				}				
			}
			return balance;
		}
			
		else
			return "0";
		
		
	}
}
