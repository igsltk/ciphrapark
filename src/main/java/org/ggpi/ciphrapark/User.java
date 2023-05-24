package org.ggpi.ciphrapark;

public class User {
	private String Id;
	private String Name;
	private String LastName;
	private String Login;
	private String Password;
	private String Email;
	private String Phone;
	private String Parks;
	private String Balance;

	public User() {}

	public User(String id, String name, String lastname,
			String login, String pass, String email,
			String phone, String parks, String balance) {
		this.Id = id;
		this.Name = name;
		this.LastName = lastname;
		this.Login = login;
		this.Password = pass;
		this.Email = email;
		this.Phone = phone;
		this.Parks = parks;
		this.Balance = balance;
	}

	public void setId(String a) { this.Id = a; }
	public void setName(String a) { this.Name = a; }
	public void setLastName(String a) { this.LastName = a; }
	public void setLogin(String a) { this.Login = a; }
	public void setPassword(String a) { this.Password = a; }
	public void setEmail(String a) { this.Email = a; }
	public void setPhone(String a) { this.Phone = a; }
	public void setParks(String a) { this.Parks = a; }
	public void setBalance(String a) { this.Balance = a; }

	public String getId() { return Id; }
	public String getName() { return Name; }
	public String getLastName() { return LastName; }
	public String getLogin() { return Login; }
	public String getPassword() { return Password; }
	public String getEmail() { return Email; }
	public String getPhone() { return Phone; }
	public String getParks() { return Parks; }
	public String getBalance() { return Balance; }
}