package matera.com.hellomicroservices.core.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatePersonRequest {

	@XmlElement(name = "firstName")
	private final String firstName; 
	
	@XmlElement(name = "lastName")
	private final String lastName;
	
	@XmlElement(name = "email")
	private final String email;
	
	@XmlElement(name = "nickName")
	private final String nickName;
	
	@XmlElement(name = "city")
	private final String city;
	
	@XmlElement(name = "state")
	private final String state;
	
	@XmlElement(name = "country")
	private final String country;
	
	@XmlElement(name = "zipCode")
	private final String zipCode;
	
	
	private CreatePersonRequest(Builder builder) {
		
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.nickName = builder.nickName;
		this.email = builder.email;
		this.city = builder.city;
		this.state = builder.state;
		this.country = builder.country;
		this.zipCode = builder.zipCode;
		
	}
	
	@XmlTransient
	public static class Builder {

		private String firstName;
		private String lastName;
		private String nickName;
		private String email;
		private String city;
		private String state;
		private String country;
		private String zipCode;
		
		public CreatePersonRequest build() {
			
			return new CreatePersonRequest(this);
			
		}
		
		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}			
		
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder withNickName(String nickName) {
			this.nickName = nickName;
			return this;
		}	
		
		public Builder withCity(String city) {
			this.city = city;
			return this;
		}
		
		public Builder withState(String state) {
			this.state = state;
			return this;
		}			
		
		public Builder withCountry(String country) {
			this.country = country;
			return this;
		}		
		
		public Builder withZipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}			
		
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

	public String getZipCode() {
		return zipCode;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		CreatePersonRequest other = (CreatePersonRequest) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
	
}
