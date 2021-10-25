package cld.jcoder.demo.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // https://stackoverflow.com/questions/49847791/java-spring-security-user-withdefaultpasswordencoder-is-deprecated
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder().username("user").password("password")
                        .authorities("ROLE_USER").build(),
                User.withDefaultPasswordEncoder().username("admin").password("admin")
                        .authorities("ROLE_ACTUATOR", "ROLE_ADMIN", "ROLE_USER").build());
    }

    // the method below is not pickec up by springboot, as its not the original configure (I changed thre name!_
    // the default configure method copied from docs:
    protected void configureDefaults(HttpSecurity http) throws Exception {
        ((HttpSecurity)
                ((HttpSecurity)
                        (   (ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().anyRequest()    )
                .authenticated().and()).
                formLogin().and()).
                httpBasic();
//---------- end default
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated() // you must be logged in to all ACTUATOR endpoints /health & /info but not /env (must enable it in app.props)
                .anyRequest().permitAll().and()  // no login required to others
                .formLogin().and()
                .httpBasic();
    }

    // the overwridden version:
    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http.csrf().disable().authorizeRequests()
                // only ADMIN role to /admin
                .mvcMatchers("/admin").hasRole("ADMIN")
                //
                .requestMatchers(EndpointRequest.to("info", "health")).permitAll() // makes these 2 endpoints public
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR") //access to all the others requires the role of ACTUATOR
                // allow static resources to everyone:
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/events/**").hasRole("USER")
                // everything else is allowed:
                .antMatchers("/**").permitAll()
                // basic authentication for everything:
                .and().httpBasic();
    }
}
