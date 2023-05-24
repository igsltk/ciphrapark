package org.ggpi.ciphrapark;

public class AppConfig {
	protected String dbHost = "";
	protected String dbPort = "";
	protected String dbUser = "";
	protected String dbPass = "";
	protected String dbName = "";
	
	public static final String USER_TABLE = "users";
	public static final String USERS_ID = "id";
	public static final String USERS_LOGIN = "login";
	public static final String USERS_PASS = "password";
	public static final String USERS_NAME = "name";
	public static final String USERS_LASTNAME = "lastname";
	public static final String USERS_EMAIL = "email";
	public static final String USERS_PHONE = "phone";
	public static final String USERS_PARKS = "parks";
	public static final String USERS_BALANCE = "balance";

	public static final String PARK_TABLE = "parks";
	public static final String PARKS_ID = "id";
	public static final String PARKS_OWNER = "owner";
	public static final String PARKS_OWNERID = "ownerid";
	public static final String PARKS_EXPIREDATE = "expiredate";
	public static final String PARKS_PRICE = "price";
	public static final String PARKS_SPECIALS = "specials";
	public static final String PARKS_QUEUE = "queue";
}