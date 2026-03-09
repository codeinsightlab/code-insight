package lab.codeinsight.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point for CodeInsight backend.
 */
@SpringBootApplication
public class CodeInsightBackendApplication {

	/**
	 * Starts the backend application context.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CodeInsightBackendApplication.class, args);
	}
}
