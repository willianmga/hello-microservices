package matera.com.hellomicroservices.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;

public class HelloMicroservicesJacksonModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(ObjectMapper.class)
			.annotatedWith(HelloMicroservices.class)
				.toProvider(HelloMicroservicesObjectMapperProvider.class);

	}

}
