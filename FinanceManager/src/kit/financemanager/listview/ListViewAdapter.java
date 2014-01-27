package kit.financemanager.listview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kit.financemanager.R;
import kit.financemanager.navdrawer.NavDrawerItem;

public class ListViewAdapter extends BaseAdapter {

	Context context;
	private ArrayList<ListViewItem> listViewItems;

    public ListViewAdapter(Context context, ArrayList<ListViewItem> listViewItems) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listViewItems = listViewItems;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
    	return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, null);
        }
          
        TextView txtAmount = (TextView) convertView.findViewById(R.id.amount);
        TextView txtCategory = (TextView) convertView.findViewById(R.id.category);
        TextView txtRemarks = (TextView) convertView.findViewById(R.id.remarks);
        TextView txtDate = (TextView) convertView.findViewById(R.id.date);
                 
        txtAmount.setText(listViewItems.get(position).getAmount());
        txtAmount.setTextColor(Color.parseColor(listViewItems.get(position).getColor()));
        
        txtCategory.setText(listViewItems.get(position).getCategory());
        txtRemarks.setText(listViewItems.get(position).getRemarks());
        txtDate.setText(listViewItems.get(position).getDate());
         
        return convertView;
    }

}
