package matera.com.hellomicroservices.core.responses;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PeopleResource {
	
	@XmlElement(name = "people")
	private List<PersonResource> people;
	
	private PeopleResource(Builder builder) {
		this.people = builder.people;
	}
	
	private PeopleResource() {
		super();
	}
	
	public static class Builder {

		public List<PersonResource> people;
		
		public PeopleResource build() {
			
			return new PeopleResource(this);
			
		}
		
		public Builder withPeople(List<PersonResource> people) {
			this.people = people;
			return this;
		}
		
	}
	
	public List<PersonResource> getPeopleResource() {
		return people;
	}

}
