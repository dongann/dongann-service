/*
 * file    SeqEnum.java
 * author  northwind
 * date    2018.3.22
 */

package com.dongann.app.enums;

public enum SeqEnum {
	JOB_NO_SEQ("JOB_NO_SEQ"),
	PAYMENT_NO_SEQ("PAYMENT_NO_SEQ"),
	PAYMENT_ITEM_NO_SEQ("PAYMENT_ITEM_NO_SEQ"),
	INVOICE_RED_NO_SEQ("INVOICE_RED_NO_SEQ"),
	INVOICE_HEADER_NO_SEQ("INVOICE_HEADER_NO_SEQ"),
	INVOICE_ITEM_NO_SEQ("INVOICE_ITEM_NO_SEQ"),
	INVOICE_NO_SEQ("INVOICE_NO_SEQ"),
	BCC_NO_SEQ("BCC_NO_SEQ"),
	REFUND_NO_SEQ("REFUND_NO_SEQ"),
	ORDER_NO_SEQ("ORDER_NO_SEQ"),
	PAY_NO_SEQ("PAY_NO_SEQ");
	private String title;
	private SeqEnum(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
