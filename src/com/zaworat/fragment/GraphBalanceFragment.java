package com.zaworat.fragment;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.zaworat.activity.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GraphBalanceFragment extends Fragment{
	private RelativeLayout graphBalance;
	private PieChart mChart;
	private static final int MENU_ITEM_BACK = 2000;

	// we are going to display pie chart
	private float[] yData = { 5, 10, 15, 30, 40 };
	private String[] xData = { "Sony", "Hiwawie", "LG", "Apple", "Samsung" };

	public GraphBalanceFragment(){}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.balance_by_graph, container, false);
        graphBalance = (RelativeLayout) rootView.findViewById(R.id.graphBalance);
        
		mChart = new PieChart(getActivity());

		// add pie chart to main layout
		graphBalance.addView(mChart);
		graphBalance.setBackgroundColor(Color.parseColor("#55656C"));

		// Configure pie chart
		mChart.setUsePercentValues(true);
		mChart.setDescription("Smart phone market share");

		// Enable hole and configure
		mChart.setDrawHoleEnabled(true);
		mChart.setHoleColorTransparent(true);
		mChart.setHoleRadius(7);
		mChart.setTransparentCircleRadius(10);

		// enable rotation of the chart by touch
		mChart.setRotationAngle(0);
		mChart.setRotationEnabled(true);

		// set achart value selected listener
		mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				if (e == null)
					return;
				Toast.makeText(getActivity(),
						xData[e.getXIndex()] + " = " + e.getVal() + "%",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected() {

			}
		});
		
		//add data
		addData();
		
		//customize legend
		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7);
		l.setYEntrySpace(5);
		
		return rootView;
	}

	private void addData(){
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		for(int i =0; i<yData.length; i++){
			yVals1.add(new Entry(yData[i], i));
		}
		
		ArrayList<String> xVals1 = new ArrayList<String>();
		for(int i=0; i < xData.length; i++)
			xVals1.add(xData[i]);
		
		//create pie data set
		PieDataSet dataSet = new PieDataSet(yVals1, "Market shares");
		dataSet.setSliceSpace(3);
		dataSet.setSelectionShift(5);
		
		//Add many colors
		ArrayList<Integer> colors = new ArrayList<Integer>();
		for(int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);
		
		for(int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);
		
		for(int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);
		
		for(int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);
		
		for(int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);
		
		colors.add(ColorTemplate.getHoloBlue());
		dataSet.setColors(colors);
		
		//initialize pie data
		PieData data = new PieData(xVals1, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.GRAY);
		
		mChart.setData(data);
		
		//undo all highlights
		mChart.highlightValues(null);
		
		//update pie chart
		mChart.invalidate();		
		
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
