package matera.com.hellomicroservices.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.inject.Provider;

public class HelloMicroservicesHttpClientProvider implements Provider<HttpClient> {

	@Override
	public HttpClient get() {
		
		return HttpClientBuilder.create().build();
		
	}

}
