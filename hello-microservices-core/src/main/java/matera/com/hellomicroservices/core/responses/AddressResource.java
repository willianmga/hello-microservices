package matera.com.hellomicroservices.core.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressResource {
	
	@XmlElement(name = "city")
	private String city;
	
	@XmlElement(name = "state")
	private String state;
	
	@XmlElement(name = "country")
	private String country;
	
	@XmlElement(name = "zipCode")
	private String zipCode;
	
	private AddressResource(Builder builder) {
		
		this.city = builder.city;
		this.state = builder.state;
		this.country = builder.country;
		this.zipCode = builder.zipCode;
		
	}
	
	private AddressResource() {
		super();
	}	
	
	@XmlTransient
	public static class Builder {

		public String city;
		public String state;
		public String country;
		public String zipCode;
		
		public AddressResource build() {
			
			return new AddressResource(this);
			
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		AddressResource other = (AddressResource) obj;
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
