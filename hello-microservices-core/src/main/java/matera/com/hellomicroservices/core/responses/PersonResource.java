package matera.com.hellomicroservices.core.responses;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonResource {

	@XmlElement(name = "uuid")
	private final UUID uuid;
	
	@XmlElement(name = "firstName")
	private final String firstName;
	
	@XmlElement(name = "lastName")
	private final String lastName;
	
	@XmlElement(name = "email")
	private final String email;
	
	@XmlElement(name = "nickName")
	private final String nickName;
	
	@XmlElement(name = "address")
	private final AddressResource address;
	
	private PersonResource(Builder builder) {
		this.uuid = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.nickName = builder.nickName;
		this.address = builder.address;
	}
	
	@XmlTransient
	public static class Builder {

		private UUID id;
		private String firstName;
		private String lastName;
		private String email;
		private String nickName;
		private AddressResource address;
		
		public PersonResource build() {
			
			return new PersonResource(this);
			
		}
		
		public Builder withUUID(UUID id) {
			this.id = id;
			return this;
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
		
		public Builder withAddress(AddressResource address) {
			this.address = address;
			return this;
		}		
		
	}

	public UUID getUuid() {
		return uuid;
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

	public AddressResource getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		PersonResource other = (PersonResource) obj;
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
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
}
