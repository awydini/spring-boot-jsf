package net.aydini.modescisc.cif.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.aydini.modescisc.cif.domain.entity.CustomerEntity;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 * @param <T>
 */
public interface CustomerDao extends BaseDao<CustomerEntity> {


    @Query(value = " select count (ce) from CustomerEntity ce where ce.nationalCode = :nationalCode and ce.deletedDate is null ")
    public Long countNationalCode(@Param("nationalCode") String nationalCode);


    @Query(value = " select count (ce) from CustomerEntity ce where ce.nationalCode = :nationalCode and ce.id <> :id and ce.deletedDate is null")
    public Long countNationalCode(@Param("nationalCode") String nationalCode,@Param("id") Long id);
}
