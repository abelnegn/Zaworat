package com.zaworat.activity;

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

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GraphBalance extends Activity {

	private RelativeLayout graphBalance;
	private PieChart mChart;

	// we are going to display pie chart
	private float[] yData = { 5, 10, 15, 30, 40 };
	private String[] xData = { "Sony", "Hiwawie", "LG", "Apple", "Samsung" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance_by_graph);

		graphBalance = (RelativeLayout) findViewById(R.id.graphBalance);
		mChart = new PieChart(this);

		// add pie chart to main layout
		graphBalance.addView(mChart);
		graphBalance.setBackgroundColor(Color.LTGRAY);

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
				Toast.makeText(GraphBalance.this,
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
