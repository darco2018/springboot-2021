package cloud.javacoder.demo1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
/*
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(   autmatically scans for Beans in the current package (eg @RestController-annotated classes are registered as beans
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)*/

@SpringBootApplication
public class Demo1NameApplication {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Demo1NameApplication.class);
    //replace to log with log4j2:
    // private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(Demo1NameApplication.class);

    public static void main(String[] args) {

        //System.setProperty("spring.devtools.restart.enabled", "false");

        //	or just  ConfigurableApplicationContext
        ApplicationContext appContext = SpringApplication.run(Demo1NameApplication.class, args); // this.getClass()
        appContext.getEnvironment().getActiveProfiles();

        System.out.printf("In Demo1 you will find: " +
                "%n-logging with sl4j + logback, " +
                "%n- possibility to log with log4j2 " +
                "%n-Dev-Tools, " +
                "%n-Actuator, " +
                "%n-Hal-Explorer(browser), " +
                "%n-2 profiles (both set as separate runtimes in Edit Configurations), " +
                "%n-hot reload & project rebuild on CTRL + S%n%n" +
                "%n-------------------------------------------");

        /* NOTE ON TESTS
        * Problem: spring.profiles.default=dev (or prod) doesn't work as expected
        * Tests are failed (also with Maven test) if the ective profile is not set DIRECTLY because
        * values from application-prod or -dev are not resolved.
        *
        * For using Maven goals I have created new Run Configuration in Maven view for the test goal,
        *  where I added -Dspring.profiles.active=prod. then you double click this configuration.
        *  For package goal or others further in hierarchy you must create a new Run Configuration
        * */


       /* ----------------------- ACTUATOR INFO ------------------------------------------------
       Actuator endpoints let you monitor and interact with your application. Spring Boot includes a number of built-in endpoints and lets you add your own.
        /actuator/<endpoint> eg /actuator/health
        Some endpoints: health, mappings, beans, info, configprops, env, httptrace, loggers, metrics, sessions, shutdown, flyway

        EXPOSE ENDPOINTS:
        - management.endpoints.enabled-by-default=true or set to false & management.endpoint.<>.enabled=true
        - The Actuator HTTP Trace and Auditing features are not enabled by default anymore. For web apps add
           management.endpoints.web.exposure.include=*  You can also use ........exclude=health,info
        - CORS disabled by default
          management.endpoints.web.cors.allowed-origins=https://example.com
          management.endpoints.web.cors.allowed-methods=GET,POST
         - Docs more info on
           - how to secure actuator when in production
           - how to add custom endpoints & exposed data
           - how to interact by HTTP get, post, delete
       * Actuator can be used with another dep: HAL explorer
       * localhost:8080/actuator/  /health /info
       *
       */


        /*
            LOGGING INFO
        slf4j-api + logback are by default in springBoot
        Should you not provide a logging configuration to SL4J, it won???t complain. Logging simply becomes a no-op operation.
        root logging level - default is INFO
        Spring Boot automatically configures Log4j if it finds a file named log4j2.xml - must be in resources folder
        */


        logger.trace(">>>>>>>> in main():Trace log message");
        logger.debug(">>>>>>>> in main():Debug log message");
        logger.info(">>>>>>>>  in main():Info log message");
        logger.warn(">>>>>>>>  in main():Warn log message");
        logger.error(">>>>>>>> in main():Error log message");

		System.out.println(logger.getClass()); //org.apache.logging.slf4j.Log4jLogger  or  ch.qos.logback.classic.Logger by default


		/*
		    HAL-EXPLORER INFO    (more notes in Baeldung spring-rest-hal-browser project)
		HAL explorer opens at localhost:8080 & redirects to http://localhost:8080/explorer/index.html#/
		Spring Data REST HAL Browser is deprecated! Prefer the HAL Explorer (artifactId: spring-data-rest-hal-explorer)!

		Including HAL within our REST API makes it much more explorable to users as well as being essentially self-documenting.
        It works by returning data in JSON format which outlines relevant information about the API.
        The HAL browser provides an in-browser GUI to traverse your REST API.*/



		/* ------------------ DEV TOOLS - automatic restart & hot reload -------------------
		NOTE: it may be necessary to dispable Idea's built-in server at localhost:63342. In Settings/Web Browsers and Preview
		
		Hot reload: when app is running, making a code change & pressing Ctrl + S will reflect the change in the browser(after a few about 4, seconds).
		No need to refresh the browser.

		Automatc restart enables to add a change without stopping the server.
		When app is not running, making a code change, results in automatic rebuild.

		These folders will not trigger reload by default:
        /META-INF/maven
        /META-INF/resources
        /resources         -> actually, somehow it is reflected without adding extra paths in application properties
        /static
        /public
        /templates

        You can configure additional folders to scan in application.properties:
        spring.devtools.restart.additional-paths = /path-to-folder
        You can also configure folders to exclude.
                spring.devtools.restart.exclude=static/**,public/**

		when run by calling .jar, they will not be loaded (production mode)

		  -DOESNT WORK IN FIREFOX
		  - These conditions must be met:
			1. LIVE RELOAD extension enabled in Browser!!!! (you can find it in Chrome Dev Tools >> Network
			2. Settings: turn on: Build project automatically (only works when not running or debugging) 
			  Important to turn it on just for Hot reload, not just for automatic restart
			3. REGISTRY: compiler.automake.allow.when.app.running=true (may eventually delete some classes that are required by the application.)

			Trying to reload browser with BUILD (CTRL + 9 - incremental build) in Idea (rather than CTrl + Save) will sometimes result in WHitelabel error
			for a URL that previously rendered correctly. In which case just RERUN the project.

		 */


    }

}
