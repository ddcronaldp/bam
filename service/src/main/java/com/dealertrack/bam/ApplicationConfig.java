package com.dealertrack.bam;

import com.dealer.config.provider.spring.annotation.EnableRemoteConfig;
import com.dealer.config.provider.spring.support.RemoteConfigPropertySourcesPlaceholderConfigurer;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"com.dealertrack.bam"})
@EnableRemoteConfig
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@ImportResource({"classpath:oauth2-core-context.xml"})
@EnableMongoRepositories(basePackages = "com.dealertrack.bam.persistence", mongoTemplateRef = "changesetMongo")
public class ApplicationConfig {

	public static final  String BASE_URL = "/api/v1/";


	@Bean
	public ServletRegistrationBean jerseyServlet() {
		final ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), BASE_URL + "*");
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new RemoteConfigPropertySourcesPlaceholderConfigurer();
	}

}
