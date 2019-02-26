package model_class;

public class AddressType {

	private String description;
	private int id;

	public AddressType(String description) {
		this.description = description;
	}

	public AddressType() {
		// TODO Auto-generated constructor stub
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
