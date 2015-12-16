package com.zaworat.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SafeUIBlockingUtility {

    public String utilityTitle = "Updating";
    public String utilityMessage = "Infonegari is Updating your data...";

    private ProgressDialog progressDialog;

    private Context context;

    public SafeUIBlockingUtility(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(utilityMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(utilityTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    
    public SafeUIBlockingUtility(Context context, String utilityTitle, String utilityMessage){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(utilityMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(utilityTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void safelyBlockUI(){
        progressDialog.show();
    }

    public void safelyUnBlockUI(){
        progressDialog.dismiss();
    }

    public String getUtilityTitle() {
		return utilityTitle;
	}

	public void setUtilityTitle(String utilityTitle) {
		this.utilityTitle = utilityTitle;
	}

	public String getUtilityMessage() {
		return utilityMessage;
	}

	public void setUtilityMessage(String utilityMessage) {
		this.utilityMessage = utilityMessage;
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void safelyUnblockUIForFailure(String tag, String message){

        progressDialog.dismiss();
        Toast.makeText(context, "Some Problem Executing Request", Toast.LENGTH_SHORT).show();
        Log.i(tag,message);

    }

}
