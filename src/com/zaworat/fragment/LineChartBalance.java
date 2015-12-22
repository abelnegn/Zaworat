package com.zaworat.fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zaworat.activity.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class LineChartBalance extends Fragment {

	private RelativeLayout graphBalance;
	private LineChart mChart;
	private static final int MENU_ITEM_BACK = 2000;

	// we are going to display pie chart
	private float[] yData = { 5, 10, 15, 30, 40 };
	private String[] xData = { "Sony", "Hiwawie", "LG", "Apple", "Samsung" };

	public LineChartBalance() {
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
		graphBalance = (RelativeLayout) rootView
				.findViewById(R.id.graphBalance);

		mChart = new LineChart(getActivity());

		// add to main layout
		graphBalance.addView(mChart);
		mChart.setDescription("");
		mChart.setNoDataTextDescription("no data for the moment");

		// enable value highlighting
		mChart.setHighlightEnabled(true);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// we want also enable scalling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);

		// enable pinch zoom to avoid scalling x and y axis separately
		mChart.setPinchZoom(true);

		// alternative background color
		mChart.setBackgroundColor(Color.LTGRAY);

		// now, we work on data
		LineData data = new LineData();
		data.setValueTextColor(Color.WHITE);

		// get legend object
		Legend l = mChart.getLegend();

		// customize legend
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.WHITE);

		XAxis xl = mChart.getXAxis();
		xl.setTextColor(Color.WHITE);
		xl.setDrawGridLines(false);
		xl.setAvoidFirstLastClipping(true);

		YAxis yl = mChart.getAxisLeft();
		yl.setTextColor(Color.WHITE);
		yl.setAxisMaxValue(120f);
		yl.setDrawGridLines(true);

		YAxis yl2 = mChart.getAxisRight();
		yl2.setEnabled(false);

		return rootView;
	}

//    @Override
//    public void onResume() {
//       super.onResume();
//		//now we are going to simulate real time data additon
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				//add 100 entries
//				for(int i = 0; i < 100; i++){
//					runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							addEntry();
//						}
//					});
//					
//					//pause between adds
//					try{
//						Thread.sleep(600);
//					}catch(InterruptedException e){
//						//manage error.....
//					}
//				}
//
//			}
//		});
//	}
    
	// we need to create method to add entry to the line chart

	private void addEntry() {
		LineData data = mChart.getData();

		if (data != null) {
			LineDataSet set = data.getDataSetByIndex(0);

			if (set == null) {
				set = createSet();
				data.addDataSet(set);
			}

			// add a new random value
			data.addXValue("");
			data.addEntry(
					new Entry((float) (Math.random() * 75) + 60f, set
							.getEntryCount()), 0);
			
			//notify chart data have changed
			mChart.notifyDataSetChanged();
			
			//limit number of visible entries
			mChart.setVisibleXRange(6);
			//scroll to the last entry
			mChart.moveViewToX(data.getXValCount() - 7);
		}
	}
	
	private LineDataSet createSet(){
		LineDataSet set = new LineDataSet(null, "SPL db");
		set.setDrawCubic(true);
		set.setCubicIntensity(0.2f);
		set.setAxisDependency(AxisDependency.LEFT);
		set.setColor(ColorTemplate.getHoloBlue());
		set.setCircleColor(ColorTemplate.getHoloBlue());
		set.setLineWidth(2f);
		set.setCircleSize(4f);
		set.setFillAlpha(65);
		set.setFillColor(ColorTemplate.getHoloBlue());
		set.setHighLightColor(Color.rgb(244, 117, 17));
		set.setValueTextColor(Color.WHITE);
		set.setValueTextSize(10f);
		
		return set;
	}
}
