package games.lmdbg.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the application
 */
@SpringBootApplication
public class LegendaryStatsServerApplication {

	/**
	 * Start the web serve
	 * 
	 * @param args Ignored
	 */
	public static void main(String[] args) {
		SpringApplication.run(LegendaryStatsServerApplication.class, args);
	}

}
