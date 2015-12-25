package com.zaworat.fragment;

import java.util.ArrayList;
import net.sqlcipher.database.SQLiteDatabase;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.orm.Database;
import com.zaworat.activity.R;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class PerformanceMonth extends Fragment {
	private static final int MENU_ITEM_BACK = 2000;
	private RelativeLayout graphBalance;
	private BarChart mChart;	
	private Database database;
	
	public PerformanceMonth() {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.balance_by_graph, container,
				false);
		
		//addToTable();
		graphBalance = (RelativeLayout) rootView
				.findViewById(R.id.graphBalance);
		
		mChart = new BarChart(getActivity());
		
		// add to main layout
		graphBalance.addView(mChart);
		mChart.setDescription("");
		mChart.setNoDataTextDescription("no data for the moment");
		mChart.animateY(5000);
		mChart.setBackgroundColor(Color.LTGRAY);
		
		addData();
		return rootView;		
	}
	
//	private void addData(){
//		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
//		entries.add(new BarEntry(40, 0));
//		entries.add(new BarEntry(80, 1));
//		entries.add(new BarEntry(60, 2));
//		entries.add(new BarEntry(120, 3));
//		entries.add(new BarEntry(180, 4));
//		entries.add(new BarEntry(90, 5));
//		
//		BarDataSet dataset = new BarDataSet(entries, "Amount in Birr");
////		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
//		
//		ArrayList<String> labels = new ArrayList<String>();
//		labels.add("jan"); 
//		labels.add("feb"); 
//		labels.add("mar"); 
//		labels.add("apr"); 
//		labels.add("May");
//		labels.add("jun");
//		
//		BarData data = new BarData(labels, dataset);
//		mChart.setData(data);
//		mChart.setDescription("Zaworat balance");
//	
//	}

	private String getMonth(String sDate){
		String sMonth = "";
		if(sDate.equals("01"))
			sMonth = "Jan";
		else if(sDate.equals("02"))
			sMonth = "Feb";
		else if(sDate.equals("03"))
			sMonth = "Mar";
		else if(sDate.equals("04"))
			sMonth = "Apr";
		else if(sDate.equals("05"))
			sMonth = "May";
		else if(sDate.equals("06"))
			sMonth = "Jun";
		else if(sDate.equals("07"))
			sMonth = "Jul";
		else if(sDate.equals("08"))
			sMonth = "Aug";
		else if(sDate.equals("09"))
			sMonth = "Sep";
		else if(sDate.equals("10"))
			sMonth = "Oct";
		else if(sDate.equals("11"))
			sMonth = "Nov";
		else if(sDate.equals("12"))
			sMonth = "Dec";
		
		return sMonth;
	}
	
	private void addData(){
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
		ArrayList<String> labels = new ArrayList<String>();
		Cursor cursor = getGroupQuery("kality");
		cursor.moveToFirst();
		for(int cur = 0; cur < cursor.getCount(); cur++){
			String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
			float fAmount = cursor.getFloat(cursor.getColumnIndexOrThrow("amount"));
			entries.add(new BarEntry(fAmount, cur));
			labels.add(getMonth(month)); 
			
			cursor.moveToNext();
		}
		
		BarDataSet dataset = new BarDataSet(entries, "Kality Branch");
		BarData data = new BarData(labels, dataset);
		mChart.setData(data);
		mChart.setDescription("Zaworat balance");
	}
	
	public Cursor getGroupQuery(String branch){
		database = new Database(getActivity());
		SQLiteDatabase db = database.getDB();
		String selectQuery = "SELECT substr(balance_Date,5,2) month, sum(amount) amount " +
				"FROM Zaworat_Balance WHERE branch = ? " +
				"GROUP BY month";
		Cursor cursor = db.rawQuery(selectQuery, new String[] {branch});
		
		return cursor;
	}
	
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuItem mItemSearchClient = menu.add(Menu.NONE, MENU_ITEM_BACK, Menu.NONE, "Back");
        mItemSearchClient.setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_backward)
        .colorRes(R.color.black)
        .actionBarSize());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mItemSearchClient.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == MENU_ITEM_BACK) {
			FragmentManager fragmentManager = getFragmentManager();
			HomeFragment gbFragment = new HomeFragment();
			fragmentManager.beginTransaction().replace(R.id.frame_container, gbFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onResume() {
       super.onResume();

       getView().setFocusableInTouchMode(true);
       getView().requestFocus();
       getView().setOnKeyListener(new View.OnKeyListener() {
          @Override
          public boolean onKey(View v, int keyCode, KeyEvent event) {

              if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
    			FragmentManager fragmentManager = getFragmentManager();
    			HomeFragment fragment = new HomeFragment();
    			fragmentManager.beginTransaction()
    					.replace(R.id.frame_container, fragment).commit();
                   return true;
               }
               return false;
           }
       });
    }
}
