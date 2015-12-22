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
        
        
        Button btn_notify = (Button) rootView.findViewById(R.id.btn_notify);
        
        Button btn_add_list = (Button) rootView.findViewById(R.id.btn_add_list);
        
        Button btn_short_sms = (Button) rootView.findViewById(R.id.btn_short_sms);
        
        Button btn_language = (Button) rootView.findViewById(R.id.btn_language);
        
        Button btn_download = (Button) rootView.findViewById(R.id.btn_download);
        
        Button btn_emergency_call = (Button) rootView.findViewById(R.id.btn_emergency_call);
        
        //Short Notification button click
        btn_notify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				GraphBalanceFragment gbFragment = new GraphBalanceFragment();
				fragmentManager.beginTransaction().replace(R.id.frame_container, gbFragment).commit();
			}
		});

        //Add List button click
        btn_add_list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				LineChartBalance lcFragment = new LineChartBalance();
				fragmentManager.beginTransaction().replace(R.id.frame_container, lcFragment).commit();				
			}
		});
        
        //Short sms button click
        btn_short_sms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				AllBalanceFragment allFragment = new AllBalanceFragment();
				fragmentManager.beginTransaction().replace(R.id.frame_container, allFragment).commit();	
			}
		});

        //Listening Language button click
        btn_language.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {

			}
		});

        //Listening Download button click
        btn_download.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {

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
