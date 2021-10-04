package aplikacja.connection;

public class UserStock {

	private String shortid;
	private String amount;
	private String price;

	public UserStock() {

	}

	public UserStock(String shortid, String amount, String price) {
		this.shortid = shortid;
		this.amount = amount;
		this.price = price;

	}

	public String getShortid() {
		return shortid;
	}

	public void setShortid(String shortid) {
		this.shortid = shortid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}