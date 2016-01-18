package com.hello.mypackage;

import java.math.BigDecimal;
import java.util.Date;

public class StockOutHistory {
	private String file_name;
	private Date upload_date;
	private BigDecimal record_amount;
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public BigDecimal getRecord_amount() {
		return record_amount;
	}
	public void setRecord_amount(BigDecimal record_amount) {
		this.record_amount = record_amount;
	}
	
}
