package model_class;

import org.springframework.stereotype.Component;

@Component
public class AccountType {

	private int id;
	private String description;

	public AccountType() {
	}

	public AccountType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}