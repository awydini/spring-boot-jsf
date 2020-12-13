package net.aydini.modescisc.cif.conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Configuration
public class Initializer implements ServletContextInitializer
{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", "true");
        servletContext.setInitParameter("javax.faces.FACELETS_BUFFER_SIZE", "65535"); //64KB
        servletContext.setInitParameter("org.ocpsoft.rewrite.annotation.BASE_PACKAGES", "net.aydini.modescisc.cif.web.action");
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.addListener(com.sun.faces.config.ConfigureListener.class);
        servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");
        
        servletContext.setInitParameter("primefaces.THEME", "none");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("primefaces.DIR", "rtl");
    }

}