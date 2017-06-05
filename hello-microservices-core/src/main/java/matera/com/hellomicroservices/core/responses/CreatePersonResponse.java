package matera.com.hellomicroservices.core.responses;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatePersonResponse {

	@XmlElement(name = "personID")
	private final UUID id;
	
	@XmlElement(name = "message")
	private final String message;
	
	private CreatePersonResponse(Builder builder) {
		this.id = builder.id;
		this.message = builder.message;
	}
	
	@XmlTransient
	public static class Builder {

		public UUID id;
		public String message;
		
		public CreatePersonResponse build() {
			
			return new CreatePersonResponse(this);
			
		}
		
		public Builder withID(UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}		
		
	}

	public UUID getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		CreatePersonResponse other = (CreatePersonResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
}
