package com.dealertrack.bam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.dealer.metrics.spring.DdcMetricsConfiguration;

@Import({ApplicationConfig.class, DdcMetricsConfiguration.class})
@EnableAutoConfiguration
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
