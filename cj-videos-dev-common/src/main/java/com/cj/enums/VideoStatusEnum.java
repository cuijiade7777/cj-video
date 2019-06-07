package com.cj.enums;

public enum VideoStatusEnum {
	SUCCESS(1),	//发布成功
	FORBID(2);	//发布失败
	
	public final int value;
	
	VideoStatusEnum(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
