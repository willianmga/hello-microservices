package matera.com.hellomicroservices.core.config;

import org.apache.http.client.HttpClient;

import com.google.inject.AbstractModule;

public class HelloMicroservicesHttpClientModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(HttpClient.class)
			.annotatedWith(HelloMicroservices.class)
				.toProvider(HelloMicroservicesHttpClientProvider.class);

	}

}
