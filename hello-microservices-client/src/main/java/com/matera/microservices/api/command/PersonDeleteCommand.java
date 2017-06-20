package com.matera.microservices.api.command;

import java.net.URI;
import java.util.UUID;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;

import com.matera.microservices.config.HelloMicroservicesGroupKey;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;

public class PersonDeleteCommand extends HystrixCommand<Integer> {

	private static final HystrixCommand.Setter SETTER;
	private static final DynamicStringProperty HELLO_MIDDLE_HOST;	
	
	private HttpClient client;
	private UUID id;
	
	static {
		
		DynamicPropertyFactory config = DynamicPropertyFactory.getInstance();                                  
		HELLO_MIDDLE_HOST = config.getStringProperty("hellomicroservices.middle.host", 
								"http://hellomicroservicesmiddle:8080/hellomicroservicesmiddle/persons/{id}");
		
		SETTER = Setter.withGroupKey(HelloMicroservicesGroupKey.MIDDLE)
				.andCommandKey(HystrixCommandKey.Factory.asKey(PersonDeleteCommand.class.getSimpleName()));		
		
	}	

	public PersonDeleteCommand(HttpClient client, UUID id) {
		
		super(SETTER);
		
		this.client = client;
		this.id = id;
		
	}

	public Integer run() throws Exception {
		
		URI uri = UriBuilder.fromPath(HELLO_MIDDLE_HOST.get()).build(id);
		
		HttpDelete request = new HttpDelete(uri);
        HttpResponse response = client.execute(request);
		
        return response.getStatusLine().getStatusCode();
			
	}

}
