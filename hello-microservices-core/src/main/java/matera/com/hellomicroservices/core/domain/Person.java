package matera.com.hellomicroservices.core.domain;

public class Person {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String nickName;
	private Address adress;
	
	public Person(String firstName,String lastName , String email, String nickName, Address adress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.nickName = nickName;
		this.adress = adress;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getNickName() {
		return nickName;
	}

	public Address getAdress() {
		return adress;
	}

}
