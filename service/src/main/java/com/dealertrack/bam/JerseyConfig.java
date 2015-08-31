package com.dealertrack.bam;

import com.dealer.metrics.jersey2.Jersey2MetricsApplicationEventListener;
import com.dealer.metrics.jersey2.Jersey2MetricsRequestEventListener;
import com.dealertrack.bam.resource.*;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {

		register(RequestContextFilter.class);
		register(OauthResource.class);

		// You need to register the rest resource impl's here:

		/*
		 * Enable the logging filter to see the HTTP response for each request.
		 */
		register(LoggingFilter.class);
		register(SpringComponentProvider.class);
		register(Jersey2MetricsApplicationEventListener.class);
		register(Jersey2MetricsRequestEventListener.class);
	}
}
