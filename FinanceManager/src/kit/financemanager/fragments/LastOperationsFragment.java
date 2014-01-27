package kit.financemanager.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.listview.ListViewAdapter;
import kit.financemanager.listview.ListViewItem;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LastOperationsFragment extends Fragment {

	ArrayList<ListViewItem> listViewItem;
	ListView listview;
	Context context;
	ListViewAdapter adapter;
	int current_user;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		current_user = getArguments().getInt("current_user");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_lastoper, container, false);
		getActivity().getActionBar().setTitle(menus[position]);
		
		context = getActivity().getApplicationContext();
		listview = (ListView) v.findViewById(R.id.listView1);
		
			
		listViewItem = new ArrayList<ListViewItem>();
		DatabaseHandler db = new DatabaseHandler(context);
		List<Currency> currencies = db.getAllCurrencies();
		List<Expense> expenseList = db.getLastExpenses(current_user);
		List<Revenue> revenueList = db.getLastRevenues(current_user);
		int counter = 0;
		if(expenseList != null){
			
			for (Expense ex : expenseList) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = ex.getDate();
				
				ExpenseCategory expenseCategory = db.getExpenseCategory((String)null, ex.getCategoryId(), current_user);
				String currency = currencies.get(ex.getCurrencyId()-1).getName();
						
				if (counter == 0)
					listViewItem.add(new ListViewItem(ex.getExpenseId(), "-" + ex.getAmmount() + currency, "#f73030", expenseCategory.getName(), ex.getRemarks(), dateFormat.format(date)));
					
				else{
					boolean added = false;
					for (int i = 0; i<listViewItem.size(); i++){
						if (dateFormat.format(date).compareTo(listViewItem.get(i).getDate()) >= 0){
							listViewItem.add(i, new ListViewItem(ex.getExpenseId(), "-" + ex.getAmmount() + currency, "#f73030", expenseCategory.getName(), ex.getRemarks(), dateFormat.format(date)));
							added = true;
							break;
						}
					}
					if (!added)
						listViewItem.add(new ListViewItem(ex.getExpenseId(), "-" + ex.getAmmount() + currency, "#f73030", expenseCategory.getName(), ex.getRemarks(), dateFormat.format(date)));
						
				}
				counter++;
				
			}
			
		}
		
		if(revenueList != null){
			for (Revenue rv : revenueList) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = rv.getDate();
				
				RevenueCategory revenueCategory = db.getRevenueCategory((String)null, rv.getCategoryId(), current_user);
				String currency = currencies.get(rv.getCurrencyId()-1).getName();
				
				if (counter == 0)
					listViewItem.add(new ListViewItem(rv.getRevenueId(), "" + rv.getAmmount() + currency, "#5cb65e", revenueCategory.getName(), rv.getRemarks(), dateFormat.format(date)));
				else{
					boolean added = false;
					for (int i = 0; i<listViewItem.size(); i++){
						if (dateFormat.format(date).compareTo(listViewItem.get(i).getDate()) >= 0){
							listViewItem.add(i, new ListViewItem(rv.getRevenueId(), "" + rv.getAmmount() + currency, "#5cb65e", revenueCategory.getName(), rv.getRemarks(), dateFormat.format(date)));
							added = true;
							break;
						}
					}
					if (!added)
						listViewItem.add(new ListViewItem(rv.getRevenueId(), "" + rv.getAmmount() + currency, "#5cb65e", revenueCategory.getName(), rv.getRemarks(), dateFormat.format(date)));
						
				}
				counter++;
				//listViewItem.add(new ListViewItem("-" + ex.getAmmount(), "#f73030", expenseCategory.getName(), ex.getRemarks(), dateFormat.format(date)));
			}
		}
		
		if (counter != 0){
			for(int i = listViewItem.size() - 1; i>=0; i--){
				if (i > 9)
					listViewItem.remove(i);
			}
				
			adapter = new ListViewAdapter(getActivity().getApplicationContext(),
					listViewItem);
			listview.setAdapter(adapter);
		}
		
		listview.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override 
		    public void onItemClick(AdapterView<?> parent, View arg1,int position, long arg3)
		    { 
		    	
		    	final int index = position;
		    	final String[] sign = listViewItem.get(index).getAmount().split("(?!^)");
		    	
		    	final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		    	alert.setTitle("Delete opeartion");
		    	alert.setMessage("Are you sure you want to delete this operation?");
		        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) { 
		            	DatabaseHandler db = new DatabaseHandler(context);
		            	
		            	if (sign[0].equals("-"))
		            		db.deleteExpense(listViewItem.get(index).getId());
		            	else
		            		db.deleteRevenue(listViewItem.get(index).getId());
					    	
		            	listViewItem.remove(index);
				    	adapter = new ListViewAdapter(getActivity().getApplicationContext(),
								listViewItem);
						listview.setAdapter(adapter);
		            }
		         })
		        .setNegativeButton("No", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) { 
		                // do nothing
		            }
		         });
		         alert.show();
		    	
		    	
		        
		    }
		});
		
		
		return v;
	}
}
