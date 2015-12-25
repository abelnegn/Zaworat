package com.zaworat.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sqlcipher.database.SQLiteDatabase;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.orm.Database;
import com.zaworat.activity.R;
import com.zaworat.util.Constants;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ZaworatBalance extends Fragment{
	private WebView webview;
	private EditText fromDate, toDate;
	private Button btnSelectFrom, btnSelectTo, btnGenerateReport;
	private Database database;
	private static final int MENU_ITEM_BACK = 2000;

	public ZaworatBalance(){}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.zaworat_balance, container, false);

        webview = (WebView) rootView.findViewById(R.id.message_webview);
		fromDate = (EditText)rootView.findViewById(R.id.from_date);
		toDate = (EditText)rootView.findViewById(R.id.to_date);
		btnSelectFrom = (Button)rootView.findViewById(R.id.btn_select_from);
		btnSelectTo = (Button)rootView.findViewById(R.id.btn_select_to);
		btnGenerateReport = (Button)rootView.findViewById(R.id.generate_report);
        
        btnSelectFrom.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
			    DatePickerFragment dpf = new DatePickerFragment().newInstance();
                dpf.setCallBack(onFromDate);
                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
			}
		});
	
        btnSelectTo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
			    DatePickerFragment dpf = new DatePickerFragment().newInstance();
                dpf.setCallBack(onToDate);
                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
			}
		});
		
        btnGenerateReport.setOnClickListener(new OnClickListener() {
        	String ycMessageTable = "";
        	String tableTitleHTMLRow = "";
        	String ycSumHTMLRows = "", ycHTMLRows = "", rowHeader = "", rowDetail ="";
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			@Override
			public void onClick(View arg0) {
				
		    	Date sFromDate, sToDate;
				try {
					sFromDate = dateFormat.parse(fromDate.getText().toString());
					sToDate = dateFormat.parse(toDate.getText().toString());
					
					Cursor cursor = getGroupQuery(sFromDate, sToDate);		
					rowHeader = "<tr bgcolor='#FAAFC0'> <td bgcolor='#FAAFC0' scope='row'> <b> Branch </b></td> <td bgcolor='#FAAFC0' scope='row'> <b> Amount </b> </td>  </tr>";
					tableTitleHTMLRow = "<tr bgcolor='#FFAACC'>  <th colspan='2' bgcolor='#FFAACC' scope='row'> <strong>Zaworat Balance Report</strong> </th>  </tr>";									    	
					cursor.moveToFirst();
					for(int cur = 0; cur < cursor.getCount(); cur++){
					    if (cursor != null) {
						      String sBranch = cursor.getString(cursor.getColumnIndexOrThrow("branch"));
						      String sTotAmount = cursor.getString(cursor.getColumnIndexOrThrow("amount"));						      
						      if(sBranch == null)
						    	  sBranch = "";
						      if(sTotAmount == null)
						    	  sTotAmount = "";
						      
						      rowDetail = "<tr bgcolor='#FAAFC0'> <td bgcolor='#FAAFC0' scope='row'> <b>" + sBranch + "</b> </td> <td bgcolor='#FAAFC0' scope='row'> <b>"+  sTotAmount + "</b> </td>  </tr>";				      						   
						      ycSumHTMLRows += rowDetail;
					    }
					    cursor.moveToNext();
					}
				    ycMessageTable = tableTitleHTMLRow + Constants.messageHeader + rowHeader + ycSumHTMLRows + Constants.messageFooter;
				    
					webview.loadDataWithBaseURL("", ycMessageTable , "text/html", "UTF-8", "");
					webview.getSettings().setBuiltInZoomControls(true);
					webview.getSettings().setSupportZoom(true);
					webview.setFocusableInTouchMode(true);						
				} catch (ParseException e) {
					e.printStackTrace();
				}		    			    	   	
			}
		});
        
		return rootView;
	}
	   
	public Cursor getGroupQuery(Date dateFrom , Date dateTo){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		database = new Database(getActivity());
		SQLiteDatabase db = database.getDB();
		String sDateFrom = dateFormat.format(dateFrom);
		String sDateTo = dateFormat.format(dateTo);
		String selectQuery = "SELECT branch branch, sum(amount) amount " +
				"FROM Zaworat_Balance WHERE balance_Date between ? and ? " +
				"GROUP BY branch";
		Cursor cursor = db.rawQuery(selectQuery, new String[] {sDateFrom, sDateTo});
		
		return cursor;
	}
	
	DatePickerDialog.OnDateSetListener onFromDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        	fromDate.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                    + "/" + String.valueOf(year));
        }
    };
    
	DatePickerDialog.OnDateSetListener onToDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        	toDate.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                    + "/" + String.valueOf(year));
        }
    };
    
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
