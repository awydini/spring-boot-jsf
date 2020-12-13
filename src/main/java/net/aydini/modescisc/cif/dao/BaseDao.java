package net.aydini.modescisc.cif.dao;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 */
@NoRepositoryBean
public interface BaseDao<T extends BaseEntityModel> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>{

    @Modifying
    @Query("update #{#entityName} e set e.deletedDate = ?2, e.version= (e.version+1) where e.id= ?1")
    void deleteSoft(Serializable var1, Date var2);
}

