package com.zaworat.fragment;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.zaworat.activity.R;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment{
    private static final int MENU_ITEM_LOGIN = 2000;
    		
	public HomeFragment(){}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        
        Button btn_zBalance = (Button) rootView.findViewById(R.id.btn_notify);
        
        Button btn_cBalance = (Button) rootView.findViewById(R.id.btn_add_list);
        
        Button btn_perf_month = (Button) rootView.findViewById(R.id.btn_short_sms);
        
        Button btn_stock_balance = (Button) rootView.findViewById(R.id.btn_language);
        
        Button btn_perf_branch = (Button) rootView.findViewById(R.id.btn_download);
        
        Button btn_emergency_call = (Button) rootView.findViewById(R.id.btn_emergency_call);
        
        //Short Notification button click
        btn_zBalance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				ZaworatBalance gbFragment = new ZaworatBalance();
				fragmentManager.beginTransaction().replace(R.id.frame_container, gbFragment).commit();
			}
		});

        //Add customer balance button click
        btn_cBalance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				CustomerBalance lcFragment = new CustomerBalance();
				fragmentManager.beginTransaction().replace(R.id.frame_container, lcFragment).commit();				
			}
		});
        
        //performance by month button click
        btn_perf_month.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				PerformanceMonth allFragment = new PerformanceMonth();
				fragmentManager.beginTransaction().replace(R.id.frame_container, allFragment).commit();	
			}
		});

        //Stock balance button click
        btn_stock_balance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				StockBalance allFragment = new StockBalance();
				fragmentManager.beginTransaction().replace(R.id.frame_container, allFragment).commit();	
			}
		});

        //performance by branch button click
        btn_perf_branch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				PerformanceByBranch gbFragment = new PerformanceByBranch();
				fragmentManager.beginTransaction().replace(R.id.frame_container, gbFragment).commit();
			}
		});
        
        // Listening Emergency Call button click
        btn_emergency_call.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {

			}
		});
        
		return rootView;
    }
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuItem mItemSearchClient = menu.add(Menu.NONE, MENU_ITEM_LOGIN, Menu.NONE, "Login");
        mItemSearchClient.setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_power_off)
        .colorRes(R.color.black)
        .actionBarSize());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mItemSearchClient.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == MENU_ITEM_LOGIN) {

        }
        return super.onOptionsItemSelected(item);
    }
    
}
