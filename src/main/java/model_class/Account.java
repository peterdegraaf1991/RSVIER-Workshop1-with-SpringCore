package model_class;

public class Account {

	private int id;
	private Customer customer;
	private String email;
	private String password;
	private int accountTypeId;
	private String hash;

	
	public Account() {
	}

	public Account(Customer customer, String email, String password,
			int accountTypeId) {
		this.email = email;
		this.password = password;
		this.accountTypeId = accountTypeId;
		this.customer = customer;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}


	public Customer getCustomer() {
		return customer;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", email=" + email + ", accountTypeId="
				+ accountTypeId + "]";
	}

	// getter for password allowed?
	public String getPassword() {
		return password;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountTypeId;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountTypeId != other.accountTypeId)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
