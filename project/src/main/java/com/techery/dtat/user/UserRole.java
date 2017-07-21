package com.techery.dtat.user;

public enum UserRole {
	DEFAULT("default_user"),
	INVALID("invalid_user"),
	USER_WITH_NO_RDS("user_with_no_rds"),
	USER_ZH_HK("user_zh_hk"),
	DTG_VETERAN("DTG_Veteran"),
	PLATINUM_USER("platinum_user");
	private String key;
	UserRole(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
}
