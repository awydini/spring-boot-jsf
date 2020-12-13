package net.aydini.modescisc.cif.conf;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(udetailService()).passwordEncoder(passwordEncoder()).and().eraseCredentials(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService udetailService()
    {
        return new InMemoryUserDetailsManager(new SecUser("admin", "123",Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    @Bean
    public AuthSuccessHandler authSuccessHandler()
    {
        AuthSuccessHandler auth = new AuthSuccessHandler();
        auth.setDefaultTargetUrl("/secure/customerList");
        auth.setAlwaysUseDefaultTargetUrl(false);
        auth.setTargetUrlParameter("url");
        return auth;
    }

    @Bean
    public AuthLogoutSuccessHandler authLogoutSuccessHandler()
    {
        AuthLogoutSuccessHandler auth = new AuthLogoutSuccessHandler();
        auth.setRedirectStrategy(new JsfRedirectStrategy());
        auth.setDefaultTargetUrl("/login");
        auth.setAlwaysUseDefaultTargetUrl(false);
        auth.setTargetUrlParameter("url");
        return auth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors();
        http.csrf().disable()
                .formLogin()
                .loginProcessingUrl("/loginProcess").loginPage("/login").successForwardUrl("/home").failureUrl("/login?error=true")
                .defaultSuccessUrl("/secure/customerList", false).usernameParameter("username").passwordParameter("password")
                .successHandler(authSuccessHandler()).and().logout().logoutSuccessHandler(authLogoutSuccessHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home").and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers("/resources/**", "/webjars/**", "/login", "/api/v1/*").permitAll()

                .and().authorizeRequests()
                
                .antMatchers("/member*/**", "/secure*/**").authenticated()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/swagger-ui.html").authenticated()
                .and().headers().frameOptions().sameOrigin()
                .and().anonymous().key("webportal").principal("webportal").and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/resources/**");
    }

    //

    

}
