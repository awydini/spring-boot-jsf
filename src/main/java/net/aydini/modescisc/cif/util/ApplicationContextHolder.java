package net.aydini.modescisc.cif.util;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class ApplicationContextHolder {

    private static ConfigurableApplicationContext configurableApplicationContext;

    public static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return configurableApplicationContext;
    }

    public static void setConfigurableApplicationContext(ConfigurableApplicationContext applicationContext) {
        if(configurableApplicationContext == null)
            configurableApplicationContext = applicationContext;
    }
}
