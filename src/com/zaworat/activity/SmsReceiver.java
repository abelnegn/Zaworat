package com.zaworat.activity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.zaworat.objects.db.CustomerBalance;
import com.zaworat.objects.db.FinancialBalance;
import com.zaworat.objects.db.ZaworatBalance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += msgs[i].getMessageBody().toString();
			}
			saveData(str);
		}
	}

	private void saveData(String sms){
		String keyWord = sms.substring(0, 4);
		String sData = sms.substring(4);
		if (keyWord.equals("zbal")) {
			saveZaworatBalance(sData);
		}else if(keyWord.equals("cbal")){
			saveCustomerBalance(sData);
		}else if(keyWord.equals("fbal")){
			saveFinancialBalance(sData);
		}
	}
	
	private void saveZaworatBalance(String zaworatData) {		
		ZaworatBalance newZBalance = new ZaworatBalance();
		try {
			JSONObject jsonObject = new JSONObject(zaworatData);
					
			newZBalance.setAmount(jsonObject.getLong("amount"));
			newZBalance.setBalanceDate(jsonObject
					.getString("balanceDate"));
			newZBalance.setBranch(jsonObject.getString("branch"));
			newZBalance.setQuantity(jsonObject.getLong("quantity"));

			
			List<ZaworatBalance> zbList = Select.from(ZaworatBalance.class).where(Condition.prop("balance_Date").
					eq(jsonObject.getString("balanceDate"))).and(Condition.prop("branch").
							eq(jsonObject.getString("branch"))).list();
			if(zbList.size() > 0){
				zbList.get(0).setAmount(jsonObject.getLong("amount"));
				zbList.get(0).setQuantity(jsonObject.getLong("quantity"));
				
				zbList.get(0).save();
			}else{
				newZBalance.save();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveCustomerBalance(String customerData) {
		CustomerBalance newCBalance = new CustomerBalance();
		try {
			JSONObject jsonObject = new JSONObject(customerData);
			newCBalance.setCustomerName(jsonObject.getString("customerName"));
			newCBalance.setAmount(jsonObject.getLong("amount"));
			newCBalance.setBalanceDate(jsonObject
					.getString("balanceDate"));

			List<CustomerBalance> cbList = Select.from(CustomerBalance.class).where(Condition.prop("balance_Date").
					eq(jsonObject.getString("balanceDate"))).and(Condition.prop("customer_Name").
							eq(jsonObject.getString("customerName"))).list();
			
			if(cbList.size() > 0){
				cbList.get(0).setAmount(jsonObject.getLong("amount"));					
				cbList.get(0).save();
			}else{
				newCBalance.save();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveFinancialBalance(String financialData) {
		FinancialBalance newFBalance = new FinancialBalance();
		try {
			JSONObject jsonObject = new JSONObject(financialData);
			newFBalance.setCustomerName(jsonObject
					.getString("customerName"));
			newFBalance.setAmount(jsonObject.getLong("amount"));
			newFBalance.setBalanceDate(jsonObject
					.getString("balanceDate"));

			List<FinancialBalance> fbList = Select.from(FinancialBalance.class).where(Condition.prop("balance_Date").
					eq(jsonObject.getString("balanceDate"))).and(Condition.prop("customer_Name").
							eq(jsonObject.getString("customerName"))).list();
			
			if(fbList.size() > 0){
				fbList.get(0).setAmount(jsonObject.getLong("amount"));					
				fbList.get(0).save();
			}else{
				newFBalance.save();				
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
