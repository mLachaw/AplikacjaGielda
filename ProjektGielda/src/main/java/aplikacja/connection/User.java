package aplikacja.connection;

public class User {

	private String username;
	private String email;
	private String id_user;
	private String password;


	public User() {

	}

	public User(String username, String password, String id_user, String email) {
		this.username = username;
		this.password = password;
		this.id_user = id_user;
		this.email = email;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}


}