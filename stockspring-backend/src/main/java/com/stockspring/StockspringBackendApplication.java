package com.stockspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the StockSpring backend application.
 * <p>
 * This is the entry point of the StockSpring application, which is built using the
 * Spring Boot framework. The {@code @SpringBootApplication} annotation enables:
 * <ul>
 *   <li>Component scanning</li>
 *   <li>Spring Boot auto-configuration</li>
 *   <li>Configuration management</li>
 * </ul>
 * </p>
 * <p>
 * Running this class starts the embedded application server and initializes the Spring context.
 * </p>
 *
 * <h2>Usage</h2>
 * To run the application, execute the {@code main} method either directly in your IDE
 * or from the command line using the following command:
 * <pre>{@code
 * mvn spring-boot:run
 * }</pre>
 */
@SpringBootApplication
public class StockspringBackendApplication {

	/**
	 * The main method serves as the entry point for the Spring Boot application.
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(StockspringBackendApplication.class, args);
	}

}
