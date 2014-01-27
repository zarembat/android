package kit.financemanager.helpers;

import java.io.IOException;

import jxl.write.WriteException;
import kit.financemanager.MainActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class GenerateExcelReportAsyncTask extends AsyncTask<Object, Void, Integer> {

	Activity activity;
	
	public GenerateExcelReportAsyncTask(Activity activity) {
		this.activity = (MainActivity) activity;
	}
	@Override
	protected Integer doInBackground(Object... params) {
		
		WriteExcel test = new WriteExcel();
		
	    test.setContext(activity.getApplicationContext());
	    test.setDate((String)params[0]);
	    test.setUser((Integer)params[1]);
	    test.setExpenses((String)params[2]);
	    test.setRevenues((String)params[3]);
	    test.setBalance((String)params[4]);
	    
	    try {
			test.write();
			return 0;
		} catch (WriteException e) {
			return 1;
		} catch (IOException e) {
			return 2;
		} catch (Exception e) {
			return 3;
		}
		
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (result == 0) {
			Toast.makeText(activity.getApplicationContext(), "Report created!", Toast.LENGTH_SHORT).show();
		}
		else if (result == 1 || result == 2) {
			Toast.makeText(activity.getApplicationContext(), "An error occured when creating the report!", Toast.LENGTH_LONG).show();
		}
		else if (result == 3)		
			Toast.makeText(activity.getApplicationContext(), "Creating a report is possible only for devices with external storage!", Toast.LENGTH_LONG).show();
	}

}
