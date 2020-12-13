package net.aydini.modescisc.cif;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import net.aydini.modescisc.cif.util.ApplicationContextHolder;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 * Dec 13, 2020
 */

@SpringBootApplication
public class CifApplication {
    
    @Autowired
    ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(CifApplication.class, args);
	}
	
	 @PostConstruct
	    public void init()
	    {
	        ApplicationContextHolder.setConfigurableApplicationContext(context);
	        initShutdownHook();
	    }

	    protected void initShutdownHook()
	    {
	        context.registerShutdownHook();
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run()
	            {
	                context.close();
	            }
	        });
	    }

}
