package kit.financemanager.fragments;

import java.util.ArrayList;
import java.util.List;

import kit.financemanager.R;
import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Currency;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	Spinner spinner;
	EditText new_password;
	EditText confirm_password;
	Button save;
	Context context;
	String currency;
	
	private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD = "EUR"; 
    private SharedPreferences preferences;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int position = getArguments().getInt("position");
		String[] menus = getResources().getStringArray(R.array.menus);
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		getActivity().getActionBar().setTitle(menus[position]);

		context = getActivity().getApplicationContext();
		preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		currency = preferences.getString(PREFERENCES_TEXT_FIELD, "");
		
		DatabaseHandler db = new DatabaseHandler(context);
		List<Currency> currencyList = db.getAllCurrencies();
		List<String> currencySpinner = new ArrayList<String>();
		for (Currency c : currencyList) {
			currencySpinner.add(c.getName().toString());
		}
		
	    spinner = (Spinner) v.findViewById(R.id.spinner_currency);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, currencySpinner);
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);	
		ArrayAdapter myAdapter = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdapter.getPosition(currency);
		spinner.setSelection(spinnerPosition);
		
		new_password  = (EditText) v.findViewById(R.id.editText_newPassword);
		new_password.setTextColor(Color.parseColor("#c9c9c9"));
		
		confirm_password  = (EditText) v.findViewById(R.id.editText_confirmNewPassword);
		confirm_password.setTextColor(Color.parseColor("#c9c9c9"));
		
		save = (Button) v.findViewById(R.id.settings_save_changes);
		save.setOnClickListener(new View.OnClickListener() {
		   
		    public void onClick(View view) {
		    	changePassword();
		    	changeCurrency();
		    }
		});
		
		return v;
	}
	
	public void changePassword(){
		
		//new password
		if (new_password.getText().toString().trim().length() != 0){
			
			if(confirm_password.getText().toString().equals(new_password.getText().toString())){
				//DODAÆ ZMIENIANIE HAS£A
				Toast.makeText(context, "Password changed!", Toast.LENGTH_SHORT).show();
				new_password.setText("");
				confirm_password.setText("");
			}
			else
				Toast.makeText(context, "Please confirm a new password correctly", Toast.LENGTH_SHORT).show();
		}
		
		else{
			if(confirm_password.getText().toString().trim().length() != 0)
				Toast.makeText(context, "Please fullfil also 'new password' field to change your password", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	
	public void changeCurrency(){
		if(!spinner.getSelectedItem().equals(currency)){
			SharedPreferences.Editor preferencesEditor = preferences.edit();
		    String _currency = spinner.getSelectedItem().toString();
		    preferencesEditor.putString(PREFERENCES_TEXT_FIELD, _currency);
		    preferencesEditor.commit();
		    currency = _currency;
		    Toast.makeText(context, "Currency changed!", Toast.LENGTH_SHORT).show();
		}		
	}
}
