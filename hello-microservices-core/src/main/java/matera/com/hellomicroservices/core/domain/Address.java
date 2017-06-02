package matera.com.hellomicroservices.core.domain;

public class Address {
	
	private Long id;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	
	public Address(String city, String state, String country,String zipcode) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getZipcode() {
		return zipcode;
	}

}
