package kit.financemanager.db;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import kit.financemanager.MainActivity;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.Revenue;
import kit.financemanager.fragments.RaportFragment;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class GetOperationsForReportAsyncTask extends AsyncTask<Object, Void, Void>{

	MainActivity activity;
	RaportFragment fragment;
	List<Expense> expensesL;
	List<Revenue> revenuesL;
	List<Currency> currenciesL;
	
	public GetOperationsForReportAsyncTask(Activity activity, RaportFragment fragment) {
		this.activity = (MainActivity) activity;
		this.fragment = fragment;
	}
	
	@Override
	protected Void doInBackground(Object... params) {
		int userId = (Integer) params[0];
		String date = (String) params[1];
		DatabaseHandler db = new DatabaseHandler(activity.getApplicationContext());
        expensesL = db.getRaportExpenses(userId, date, -1);
        revenuesL = db.getRaportRevenues(userId, date, -1);
        currenciesL = db.getAllCurrencies();      
        return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		fragment.expenses.setText(textExpenses(expensesL,currenciesL));
		fragment.revenues.setText(textRevenues(revenuesL, currenciesL));
		fragment.balance.setText(textBalance(expensesL,revenuesL, currenciesL));
		fragment._balalance = textBalance(expensesL,revenuesL, currenciesL);
		fragment._expenses = textExpenses(expensesL,currenciesL);
		fragment._revenues = textRevenues(revenuesL, currenciesL);
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
