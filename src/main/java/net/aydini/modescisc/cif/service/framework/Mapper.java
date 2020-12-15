package net.aydini.modescisc.cif.service.framework;

import net.aydini.modescisc.cif.exception.ServiceException;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public interface Mapper<I,O>
{

    O map(I i) throws ServiceException;
}
