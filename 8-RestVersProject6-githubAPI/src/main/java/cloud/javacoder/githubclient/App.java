package cloud.javacoder.githubclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class App {

	private String activeProfile;

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(App.class, args);

		/* HOW APP WORKS:
		* 1. Sql for GithubProject.class(github user & project name) is loaded into H2 from db.migration.sql
		* (you can view them at /projects - Controller uses repo's findAll()
		* 2.
		*
		*
		* */



		// CommanLineRunner's run() goes first here!!

		System.out.printf("1. Change app name in a) 2 places in pom.xml  b) in .iml c) in Main class name d) in app.props\n" +
				"2. Set profiles in Edit config (spring.profiles.active=prod)\n" +
				"3. View -> remove Thymeleaf + templates folder\n" +
				"4. uncomment Security\n" +
				"5. Revise deps\n" +
				"6. LIVE RELOAD: a) REGISTRY: compiler.automake.allow.when.app.running=true & turn on:\n " +
				"   b) Build project automatically (only works when not running or debugging)\n" +
				"   c) LIVE RELOAD extension enabled in Browser! (check Chrome Dev Tools >> Network)\n" +
				"   NOTE: DOESNT WORK IN FIREFOX or .jar. Ctrl + S triggers automake\n" +
				"7. HAL-EXPLORER: localhost:8080 redirects to http://localhost:8080/explorer/index.html#/\n" +
				"8. Commit to Git\n");

		//showValue();

	}

	/*@Bean
	CommandLineRunner doStuff(){
		return args -> {
			System.out.println(Arrays.toString(args)); };
	}*/


	/*@PostConstruct
	public static void showValue(){
		System.out.println("2. I will be displayed last!");
	}*/

}
