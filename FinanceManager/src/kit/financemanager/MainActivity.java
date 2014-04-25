package kit.financemanager;

import java.util.ArrayList;

import kit.financemanager.db.DatabaseHandler;
import kit.financemanager.fragments.AddOperationFragment;
import kit.financemanager.fragments.ConfigurationFragment;
import kit.financemanager.fragments.HomeFragment;
import kit.financemanager.fragments.LastOperationsFragment;
import kit.financemanager.fragments.RaportFragment;
import kit.financemanager.fragments.SettingsFragment;
import kit.financemanager.fragments.StatisticsFragment;
import kit.financemanager.navdrawer.NavDrawerItem;
import kit.financemanager.navdrawer.NavDrawerListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle = "";
	
	private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    
    Context context;
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Show the Up button in the action bar.
		setupActionBar();
		context = getApplicationContext();
		
		DatabaseHandler db = new DatabaseHandler(context);
		String password = db.getPassword();
		if (password != null)
			checkPassword(password);
		db.close();

        navMenuTitles = getResources().getStringArray(R.array.menus);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        navMenuIcons.recycle();
        
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle("Finanse manager");
				invalidateOptionsMenu();
			}

		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				displayView(position);
			}
		});
		
		if (savedInstanceState == null) {
            displayView(0);
        }
	}
	
	private void displayView(int position) {

		String[] menuItems = getResources().getStringArray(R.array.menus);
		mTitle = menuItems[position];
		
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new HomeFragment();
            break;
        case 1:
            fragment = new LastOperationsFragment();
            break;
        case 2:
            fragment = new AddOperationFragment();
            break;
        case 3:
            fragment = new RaportFragment();
            break;
        case 4:
            fragment = new StatisticsFragment();
            break;
        case 5:
            fragment = new ConfigurationFragment();
            break;
        case 6:
            fragment = new SettingsFragment();
            break;
 
        default:
            break;
        }
 
        if (fragment != null) {
        	
        	FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
    		
			Bundle data = new Bundle();
			data.putInt("position", position);
			fragment.setArguments(data);
			
			mDrawerLayout.closeDrawer(mDrawerList);
            
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
        
    }
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	

	/** Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.findItem(R.id.action_settings).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*@Override
	public void onBackPressed() {
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    alert.setTitle("Logout");
	    alert.setMessage("Are you sure you want to logout?");
	    alert.setNegativeButton("No", null);
	    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	                MainActivity.super.onBackPressed();
	            }
	        }).create().show();
	}*/
	
	public void checkPassword(final String password){
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);

		LayoutInflater li = LayoutInflater.from(this);
		View view = li.inflate(R.layout.dialog_password, null);
		
		final EditText edit_password = (EditText) view
				.findViewById(R.id.editText_password);
		edit_password.setTextColor(Color.parseColor("#c9c9c9"));
		
	    alert.setView(view);
	    
	    final AlertDialog alertDialog = alert.create();
	    alertDialog.show();
	    alertDialog.setCanceledOnTouchOutside(false);
	    
        Button positive = (Button) alertDialog.findViewById(R.id.confirm);
	    Button negative = (Button) alertDialog.findViewById(R.id.cancel);
	    
	    positive.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View v) {
    			
    			DatabaseHandler db = new DatabaseHandler(context);
				if (edit_password.getText().toString() == password){
    				db.close();
    				alertDialog.cancel();
				}
				else
					Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
					
				db.close();

    		}
	   });
	    
	   negative.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				MainActivity.super.onBackPressed();
			}
	    });
    
	}
}
