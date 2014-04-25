/*package kit.financemanager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.entities.Expense;
import kit.financemanager.entities.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Context context;
	EditText edit_login;
	EditText edit_password;
	int current_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        context = getApplicationContext();
		
        edit_password  = (EditText) findViewById(R.id.editText_password);
        edit_password.setTextColor(Color.parseColor("#c9c9c9"));
        
	}

	public void onClickLogin(View v) {
 	   
		if (edit_login.getText().toString().length() == 0)
			Toast.makeText(getApplicationContext(), "Login cannot be empty", Toast.LENGTH_SHORT).show();
		else if (edit_password.getText().toString().length() == 0)
			Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
		else{
			DatabaseHandler db = new DatabaseHandler(context);
			User user = db.getUser(edit_login.getText().toString(), edit_password.getText().toString());
			
			if (user == null)
				Toast.makeText(getApplicationContext(), "Wrong data!", Toast.LENGTH_SHORT).show();
			else{
				current_user = user.getUserId();
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("current_user", current_user);
				edit_login.setText("");
				edit_password.setText("");
				startActivity(intent);
			}
		}
  	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.findItem(R.id.action_settings).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//menu.findItem(R.id.action_settings).setVisible(false);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
*/