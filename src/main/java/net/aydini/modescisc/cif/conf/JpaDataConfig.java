package net.aydini.modescisc.cif.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */


@Configuration
@EntityScan({ "net.aydini.modescisc.cif.domain.entity" })
@EnableJpaRepositories({ "net.aydini.modescisc.cif.dao" })
@PropertySource({ "classpath:database.properties" })
@EnableJpaAuditing
public class JpaDataConfig
{


}
