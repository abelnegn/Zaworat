package com.zaworat.objects.db;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

public class ZaworatBalance extends SugarRecord<ZaworatBalance> {

	private String branch;
	private long quantity;
	private long amount;
	private String balanceDate;

	public boolean isNew() {
		long count = Select.from(ZaworatBalance.class)
				.where(Condition.prop("id").eq(id)).count();
		return count == 0;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
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
