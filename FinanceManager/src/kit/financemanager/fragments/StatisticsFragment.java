package kit.financemanager.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.helpers.CreatePieChartAsyncTask;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class StatisticsFragment extends Fragment {

	Context context;
	int current_user;
	public LinearLayout layout;
	Spinner months_spinner;
	Spinner operations_spinner;
	Spinner currency_spinner;
	Button create;
	
	public GraphicalView mChartView = null;
	GraphicalView mChartView1 = null;
	public DefaultRenderer mRenderer;
	public CategorySeries mSeries;
	public static int[] COLORS = new int[] {
	    Color.GREEN, Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.RED, Color.DKGRAY, Color.BLACK};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		current_user = getArguments().getInt("current_user");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_statistics, container, false);
		getActivity().getActionBar().setTitle(menus[position]);
		context = getActivity().getApplicationContext();
		
		layout = (LinearLayout) v.findViewById(R.id.chartsRelativeLayout);
		months_spinner = (Spinner) v.findViewById(R.id.spinner_month);
		operations_spinner = (Spinner) v.findViewById(R.id.spinner_operation);
		currency_spinner = (Spinner) v.findViewById(R.id.spinner_currency);
		create = (Button) v.findViewById(R.id.createPieChart);
		
		String[] operationsArray = {"Balance", "Expenses", "Revenues"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, operationsArray);
		operations_spinner.setAdapter(adapter);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		List<String> months = new ArrayList<String>();
				
		String text = null;
		for (int i = month; i>0; i--){
			if (i <10)
				text = year+"-"+"0"+i;
			else
				text = year+"-"+i;
			months.add(text);
		}
				
		for (int i = 12; i>month; i--){
			if (i <10)
				text = (year-1)+"-"+"0"+i;
			else
				text = (year-1)+"-"+i;
			months.add(text);
		}
			
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, months);
		months_spinner.setAdapter(adapter);
		
		DatabaseHandler db = new DatabaseHandler(context);
		List<Currency> currencies = db.getAllCurrencies();
		List<String> currenciesList = new ArrayList<String>();
		for(Currency curr: currencies)
			currenciesList.add(curr.getName());
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, currenciesList);
		currency_spinner.setAdapter(adapter);
		
		create.setOnClickListener(new View.OnClickListener() {
			   
		    public void onClick(View view) {
		    	String operations = operations_spinner.getSelectedItem().toString();
		    	String date = months_spinner.getSelectedItem().toString()+"-01";
		    	String currency = currency_spinner.getSelectedItem().toString();
		    	
		    	createPieChart(operations, date, currency);
		    }
		});
		
		return v;
	}
	
	public void createPieChart(String operations, String date, String currency){
		
		new CreatePieChartAsyncTask(getActivity(), this).execute(currency, current_user, date, operations);
	}
}
