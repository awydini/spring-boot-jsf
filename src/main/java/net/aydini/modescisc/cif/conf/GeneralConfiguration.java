package net.aydini.modescisc.cif.conf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.ocpsoft.rewrite.servlet.impl.RewriteServletContextListener;
import org.ocpsoft.rewrite.servlet.impl.RewriteServletRequestListener;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import net.aydini.modescisc.cif.web.action.framework.ViewScope;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Configuration
public class GeneralConfiguration {



    @Bean
    public CustomScopeConfigurer customScopeConfigurer()
    {
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("view",viewScope());
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.setScopes(scopes);
        return customScopeConfigurer;
    }

    @Bean
    public ViewScope viewScope()
    {
        return new ViewScope();
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx)
    {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames)
            {

            }

        };
    }

    @Bean
    public RewriteServletRequestListener rewriteServletRequestListener()
    {
        return new RewriteServletRequestListener();
    }

    @Bean
    public RewriteServletContextListener rewriteServletContextListener()
    {
        return new RewriteServletContextListener();
    }

    @Bean
    public FilterRegistrationBean prettyFilter()
    {
        FilterRegistrationBean prettyFilter = new FilterRegistrationBean(new RewriteFilter());
        prettyFilter.setFilter(new RewriteFilter());
        prettyFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
        prettyFilter.addUrlPatterns("/*");
        return prettyFilter;
    }

    @Bean
    public FilterRegistrationBean characterEncodingFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharacterEncodingFilter("UTF-8", true, true));
        registration.addUrlPatterns("/*");
        registration.setName("characterEncodingFilter");
        return registration;
    }

    @Bean
    public FilterRegistrationBean fileUploadFilter()
    {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FileUploadFilter());
        registration.setServletNames(Arrays.asList("Faces Servlet"));
        registration.setName("fileUploadFilter");
        return registration;
    }




    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
        servletRegistrationBean.setName("Faces Servlet");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

   

}
