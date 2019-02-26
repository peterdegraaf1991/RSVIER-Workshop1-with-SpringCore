package model_class;

public class Address {
	private int id;
	private int customerId;
	private int houseNumber;
	private int addressTypeId;
	private String street;
	private String houseExtension;
	private String zipCode;
	private String city;

	public Address() {
	}

	public Address(int customerId, int houseNumber, int addressTypeId,
			String street, String houseExtension, String zipCode, String city) {
		this.customerId = customerId;
		this.houseNumber = houseNumber;
		this.addressTypeId = addressTypeId;
		this.street = street;
		this.houseExtension = houseExtension;
		this.zipCode = zipCode;
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public int getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(int addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseExtension() {
		return houseExtension;
	}

	public void setHouseExtension(String houseExtension) {
		this.houseExtension = houseExtension;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	// Start of AddressBuilder
	/*
	 * private Address(Builder builder) { this.customerId = builder.customerId;
	 * this.addressTypeId = builder.addressTypeId; this.id = builder.id;
	 * this.street = builder.street; this.houseNumber = builder.houseNumber;
	 * this.houseExtension = builder.houseExtension; this.zipCode =
	 * builder.zipCode; this.city = builder.city; }
	 * 
	 * @Override public String toString() { return id + " " + customerId + " " +
	 * addressTypeId + " " + ((street != null) ? street + " ": "") +
	 * (Integer.toString(houseNumber) !="0" ? Integer.toString(houseNumber) +
	 * " ": "") + ((houseExtension != null) ? houseExtension + " ": "") +
	 * ((zipCode != null) ? zipCode + " ": "") + ((city != null) ? city + " ":
	 * "");
	 * 
	 * }
	 * 
	 * public static class Builder { private int id; private int houseNumber;
	 * private int customerId; private int addressTypeId; private String street;
	 * private String houseExtension; private String zipCode; private String
	 * city;
	 * 
	 * public Builder customerId(int customerId) { this.customerId = customerId;
	 * return this; }
	 * 
	 * public Builder addressTypeId(int addressTypeId) { this.addressTypeId =
	 * addressTypeId; return this; }
	 * 
	 * public Builder id(int id) { this.id = id; return this; }
	 * 
	 * public Builder street(String street) { this.street = street; return this;
	 * }
	 * 
	 * public Builder houseNumber(int houseNumber) { this.houseNumber =
	 * houseNumber; return this; }
	 * 
	 * public Builder houseExtension(String houseExtension) {
	 * this.houseExtension = houseExtension; return this; }
	 * 
	 * public Builder zipCode(String zipCode) { this.zipCode = zipCode; return
	 * this; }
	 * 
	 * public Builder city(String city) { this.city = city; return this; }
	 * 
	 * 
	 * public Address build() { return new Address(this); } }
	 */
}
// End of AddressBuilder
// Start of AddressBuilder Implementation
// Address address = new Address.Builder()
// .addressId(11111)
// .addressType("AddressTypeInput")
// .customerId(333333)
// .street("Dokter van Stratenweg")
// .houseNumber(327)
// .houseExtension("")
// .zipCode("4205LE")
// .city("Gorinchem")
// .country("Nederland")
// .build();
// System.out.println(address);
//
//
// }
// }
// End of AddressBuilder implementation