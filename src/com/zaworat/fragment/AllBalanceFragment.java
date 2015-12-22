package com.zaworat.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sqlcipher.database.SQLiteDatabase;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.orm.Database;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.zaworat.activity.R;
import com.zaworat.objects.db.ZaworatBalance;

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

public class AllBalanceFragment extends Fragment {
	private static final int MENU_ITEM_BACK = 2000;
	private RelativeLayout graphBalance;
	private BarChart mChart;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public AllBalanceFragment() {
		
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

	private void addData(){
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
		ArrayList<String> labels = new ArrayList<String>();
		try {
			Cursor cursor = getGroupQuery("kality");
			for(int cur = 0; cur < cursor.getCount(); cur++){
				String sfds = cursor.getString(cursor.getColumnIndexOrThrow("BALANCE_DATE"));
				float fAmount = cursor.getFloat(cursor.getColumnIndexOrThrow("AMOUNT"));
				entries.add(new BarEntry(fAmount, cur));
				//labels.add(cursor.getString(cursor.getColumnIndexOrThrow("week"))); 
				
				cursor.moveToNext();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BarDataSet dataset = new BarDataSet(entries, "Kality Branch");
		BarData data = new BarData(labels, dataset);
		mChart.setData(data);
		mChart.setDescription("Zaworat balance");
	}
	
	public Cursor getGroupQuery(String branch) throws ParseException{
		Database database = new Database(getActivity());
		SQLiteDatabase db = database.getDB();
//		String selectQuery = "SELECT strftime('%W', balance_Date) week, sum(amount) amount " +
//				"FROM Zaworat_Balance WHERE branch = ? GROUP BY week";
		
		List<ZaworatBalance> zbList = Select.from(ZaworatBalance.class).where(Condition.prop("balance_Date").
				eq("2015-12-21")).and(Condition.prop("branch").
						eq("kality")).list();
		
//		Iterator<ZaworatBalance> zbList = ZaworatBalance.find(ZaworatBalance.class, null, null, "balance_date", null, null).iterator();
		
		List<ZaworatBalance> zbbList = Select.from(ZaworatBalance.class).list();
		String selectQuery = "SELECT * FROM Zaworat_Balance";
//		
		Cursor cursor = db.rawQuery(selectQuery, null);
//		Cursor cursor = db.rawQuery(selectQuery, new String[] {branch});

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
