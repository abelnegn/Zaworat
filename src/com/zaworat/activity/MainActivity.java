package com.zaworat.activity;

import com.zaworat.fragment.HomeFragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setFragment();
	}

    private void setFragment(){
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        fragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.frame_container,fragment).commit();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
   

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		menu.clear();
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
