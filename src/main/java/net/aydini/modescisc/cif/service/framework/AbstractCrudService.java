package net.aydini.modescisc.cif.service.framework;

import org.springframework.transaction.annotation.Transactional;

import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;

import java.util.Date;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */
@Transactional
public abstract class AbstractCrudService<E extends BaseEntityModel> extends AbstractService<E> {
    public AbstractCrudService() {
    }

    @Transactional
    public void deleteSoft(Long id) {
        ((BaseDao<E>)this.getDao()).deleteSoft(id, new Date());
    }
}
