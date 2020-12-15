package net.aydini.modescisc.cif.util;

import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.Mapper;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class SimpleLineMapper<T> {

    private Mapper<SimpleTokenizer,T> mapper;

     public SimpleLineMapper(Mapper<SimpleTokenizer,T> mapper)
    {
        this.mapper= mapper;
    }

    public T map(SimpleTokenizer tokenizer) throws ServiceException {
        return mapper.map(tokenizer);
    }

}
