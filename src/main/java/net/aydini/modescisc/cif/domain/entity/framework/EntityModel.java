package net.aydini.modescisc.cif.domain.entity.framework;

import java.io.Serializable;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 * @param <ID>
 */
public interface EntityModel<ID extends Serializable> extends Serializable {
    ID getId();
}