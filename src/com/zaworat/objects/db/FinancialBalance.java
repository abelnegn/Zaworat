package com.zaworat.objects.db;

import java.util.Date;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

public class FinancialBalance extends SugarRecord<FinancialBalance>{
	
	private String customerName;
	private long amount;
	private String balanceDate;
	
    public boolean isNew() {
        long count = Select.from(FinancialBalance.class).where(Condition.prop("id").eq(id)).count();
        return count == 0;
    }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}


	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}

	@Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
