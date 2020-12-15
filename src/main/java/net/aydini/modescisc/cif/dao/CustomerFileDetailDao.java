package net.aydini.modescisc.cif.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileDetailEntity;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */
public interface CustomerFileDetailDao extends BaseDao<CustomerFileDetailEntity>
{

    @Query("select cfd  from CustomerFileDetailEntity cfd where  cfd.customerFileHeaderEntity.id =:id")
    public List<CustomerFileDetailEntity> findAllByHeaderId(@Param("id") Long headerId);
}
