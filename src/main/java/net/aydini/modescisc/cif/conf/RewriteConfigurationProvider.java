package net.aydini.modescisc.cif.conf;


import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.springframework.stereotype.Component;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Component
public class RewriteConfigurationProvider extends HttpConfigurationProvider
{

    @Override
    public Configuration getConfiguration(ServletContext context)
    {
        return ConfigurationBuilder.begin()
                .addRule(Join.path("/login").to("/login.xhtml"))
                
                ;
    }

    @Override
    public int priority()
    {
        return 0;
    }
}