package kit.financemanager.helpers;

import java.util.HashMap;
import java.util.List;

import kit.financemanager.MainActivity;
import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.ExpenseCategory;
import kit.financemanager.entities.Revenue;
import kit.financemanager.entities.RevenueCategory;
import kit.financemanager.fragments.StatisticsFragment;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CreatePieChartAsyncTask extends AsyncTask<Object, Void, String> {

	MainActivity activity;
	StatisticsFragment fragment;
	
	List<Expense> expensesL;
	List<Revenue> revenuesL;
	List<ExpenseCategory> expenseCategoriesL;
	List<RevenueCategory> revenueCategoriesL;
	
	String currency;
	
	public CreatePieChartAsyncTask(Activity activity, StatisticsFragment fragment) {
		this.activity = (MainActivity) activity;
		this.fragment = fragment;
	}
	
	@Override
	protected void onPostExecute(String operations) {
		super.onPostExecute(operations);
		
if (operations.equals("Balance")){
			

			Float expense_sum = (float) 0;
			for (Expense ex : expensesL)
				expense_sum += ex.getAmmount();

			Float revenue_sum = (float) 0;
			for (Revenue rv : revenuesL)
				revenue_sum += rv.getAmmount();

			if (!(expense_sum == 0 && revenue_sum == 0)) {
				double[] values = new double[2];
				values[0] = expense_sum;
				values[1] = revenue_sum;

				String[] labels = new String[2];
				labels[0] = "Expenses";
				labels[1] = "Revenues";

				fragment.mRenderer = new DefaultRenderer();
				fragment.mSeries = new CategorySeries("Opeartions");

				for (int i = 0; i < 2; i++) {
					fragment.mSeries.add(labels[i], values[i]);
					SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
					renderer.setColor(StatisticsFragment.COLORS[(fragment.mSeries
							.getItemCount() - 1)
							% StatisticsFragment.COLORS.length]);
					renderer.setDisplayChartValues(true);
					fragment.mRenderer.addSeriesRenderer(renderer);
				}

				fragment.mChartView = ChartFactory.getPieChartView(
						activity.getApplicationContext(), fragment.mSeries,
						fragment.mRenderer);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				fragment.mChartView.setLayoutParams(params);
				fragment.mChartView.setBackgroundResource(R.drawable.backgr);
				fragment.layout.removeAllViews();
				fragment.layout.addView(fragment.mChartView);
			} else {
				fragment.layout.removeAllViews();
				Toast.makeText(
						activity.getApplicationContext(),
						"No operations in this month with currency " + currency,
						Toast.LENGTH_LONG).show();
			}

		}

		else if (operations.equals("Expenses")) {

			HashMap<String, Float> expenses = new HashMap<String, Float>();
			for (Expense ex : expensesL) {
				String category = expenseCategoriesL
						.get(ex.getCategoryId() - 1).getName();
				if (expenses.get(category) == null)
					expenses.put(category, ex.getAmmount());
				else
					expenses.put(category,
							expenses.get(category) + ex.getAmmount());
			}

			if (expensesL.size() != 0) {

				double[] values = new double[expenses.size()];
				String[] labels = new String[expenses.size()];

				int i = 0;
				for (String key : expenses.keySet()) {
					values[i] = expenses.get(key);
					labels[i] = key;
					i++;
				}

				fragment.mRenderer = new DefaultRenderer();
				fragment.mSeries = new CategorySeries("Opeartions");

				for (i = 0; i < expenses.size(); i++) {
					fragment.mSeries.add(labels[i], values[i]);
					SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
					renderer.setColor(StatisticsFragment.COLORS[(fragment.mSeries
							.getItemCount() - 1)
							% StatisticsFragment.COLORS.length]);
					renderer.setDisplayChartValues(true);
					fragment.mRenderer.addSeriesRenderer(renderer);
				}

				fragment.mChartView = ChartFactory.getPieChartView(
						activity.getApplicationContext(), fragment.mSeries,
						fragment.mRenderer);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				fragment.mChartView.setLayoutParams(params);
				fragment.mChartView.setBackgroundResource(R.drawable.backgr);
				fragment.layout.removeAllViews();
				fragment.layout.addView(fragment.mChartView);
			} else {
				fragment.layout.removeAllViews();
				Toast.makeText(activity.getApplicationContext(),
						"No expenses in this month with currency " + currency,
						Toast.LENGTH_LONG).show();
			}

		}

		else if (operations.equals("Revenues")) {

			HashMap<String, Float> revenues = new HashMap<String, Float>();
			for (Revenue rv : revenuesL) {
				String category = revenueCategoriesL
						.get(rv.getCategoryId() - 1).getName();
				if (revenues.get(category) == null)
					revenues.put(category, rv.getAmmount());
				else
					revenues.put(category,
							revenues.get(category) + rv.getAmmount());
			}

			if (revenuesL.size() != 0) {

				double[] values = new double[revenues.size()];
				String[] labels = new String[revenues.size()];

				int i = 0;
				for (String key : revenues.keySet()) {
					values[i] = revenues.get(key);
					labels[i] = key;
					i++;
				}

				fragment.mRenderer = new DefaultRenderer();
				fragment.mSeries = new CategorySeries("Opeartions");

				for (i = 0; i < revenues.size(); i++) {
					fragment.mSeries.add(labels[i], values[i]);
					SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
					renderer.setColor(StatisticsFragment.COLORS[(fragment.mSeries
							.getItemCount() - 1)
							% StatisticsFragment.COLORS.length]);
					renderer.setDisplayChartValues(true);
					fragment.mRenderer.addSeriesRenderer(renderer);
				}

				fragment.mChartView = ChartFactory.getPieChartView(
						activity.getApplicationContext(), fragment.mSeries,
						fragment.mRenderer);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				fragment.mChartView.setLayoutParams(params);
				fragment.mChartView.setBackgroundResource(R.drawable.backgr);
				fragment.layout.removeAllViews();
				fragment.layout.addView(fragment.mChartView);
			} else {
				fragment.layout.removeAllViews();
				Toast.makeText(activity.getApplicationContext(),
						"No revenues in this month with currency " + currency,
						Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@Override
	protected String doInBackground(Object... params) {

		currency = (String) params[0];
		int current_user = (Integer) params[1];
		String date = (String) params[2];
		String operations = (String) params[3];
		
		DatabaseHandler db = new DatabaseHandler(activity.getApplicationContext());
		Currency _currency = db.getCurrency(currency, current_user, -1);
		int currency_id = _currency.getCurrencyId();
		
		if (operations.equals("Revenues")) {
			revenuesL = db.getRaportRevenues(current_user,date, currency_id);
			revenueCategoriesL = db.getAllRevenueCategories(current_user);
		}
		else if (operations.equals("Expenses")) {
			expensesL = db.getRaportExpenses(current_user,date, currency_id);
			expenseCategoriesL = db.getAllExpenseCategories(current_user);
		}
		else if (operations.equals("Balance")) {
			expensesL = db.getRaportExpenses(current_user,date, currency_id);
			revenuesL = db.getRaportRevenues(current_user,date, currency_id);
		}
		
		return operations;
	}

}
