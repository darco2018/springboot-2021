package cld.jcoder.demo;

import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
// The official documentation advises that we isolate configuration properties into separate POJOs.
//  it is essential that we declare setters for each of the properties.
/*Note: If we don't use @Configuration in the POJO, then we need to add @EnableConfigurationProperties(ConfigProperties.class)
in the main Spring application class to bind the properties into the POJO.

As of Spring Boot 2.2, Spring finds and registers @ConfigurationProperties classes via classpath scanning.
Therefore, there is no need to annotate such classes with @Component (and other meta-annotations like @Configuration)
 or even use the @EnableConfigurationProperties:*/

/*You can easily generate your own configuration metadata file from items annotated with @ConfigurationProperties
		by using the spring-boot-configuration-processor jar.
		The jar includes a Java annotation processor which is invoked as your project is compiled.
 */

// http://localhost:8080/actuator/env/github.token : password or token will not be exposed by value but by *****

@ConfigurationProperties("github") // github namespace of the application (package ?!)
@Validated // makes validation for the pattern below. If not valid, you'll see it in the console
public class GithubProperties {
    // this comment will be visible at http://localhost:8080/actuator/env/github.token
    /**
     * Github private access API token "user:token"
     */
    @Pattern(regexp = "\\w+:\\w+")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
