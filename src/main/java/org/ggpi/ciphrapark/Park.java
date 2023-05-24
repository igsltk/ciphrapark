package org.ggpi.ciphrapark;

public class Park {
	private String Id;
	private String Owner;
	private String OwnerId;
	private String ExpireDate;
	private String Price;
	private String Specials;
	private String Queue;

	public Park() {}

	public Park(String id, String owner, String ownerid,
			String expiredate, String price, String specials, String queue) {
		this.Id = id;
		this.Owner = owner;
		this.OwnerId = ownerid;
		this.ExpireDate = expiredate;
		this.Price = price;
		this.Specials = specials;
		this.Queue = queue;
	}

	public void setId(String a) { this.Id = a; }
	public void setOwner(String a) { this.Owner = a; }
	public void setOwnerId(String a) { this.OwnerId = a; }
	public void setExpireDate(String a) { this.ExpireDate = a; }
	public void setPrice(String a) { this.Price = a; }
	public void setSpecials(String a) { this.Specials = a; }
	public void setQueue(String a) { this.Queue = a; }

	public String getId() { return Id; }
	public String getOwner() { return Owner; }
	public String getOwnerId() { return OwnerId; }
	public String getExpireDate() { return ExpireDate; }
	public String getPrice() { return Price; }
	public String getSpecials() { return Specials; }
	public String getQueue() { return Queue; }
}