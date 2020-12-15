package net.aydini.modescisc.cif.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.dao.CustomerFileHeaderDao;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileDetailEntity;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileHeaderEntity;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */


@Slf4j
@Service
public class CustomerFileHeaderService extends AbstractCrudService<CustomerFileHeaderEntity>
{

    private final CustomerFileHeaderDao customerFileHeaderDao;
    
    private final CustomerFileDetailService customerFileDetailService;
    
    

    @Autowired
    public CustomerFileHeaderService(CustomerFileHeaderDao customerFileHeaderDao,CustomerFileDetailService customerFileDetailService)
    {
        this.customerFileHeaderDao = customerFileHeaderDao;
        this.customerFileDetailService=customerFileDetailService;
    }



    @Override
    public BaseDao<CustomerFileHeaderEntity> getDao()
    {
        return customerFileHeaderDao;
    }
    
    

    @Transactional
    public void processCustomerFileHeader(CustomerFileHeaderEntity customerFileHeaderEntity  )
    {
        List<CustomerFileDetailEntity> customerFileDetails = customerFileDetailService.processCustomerFile(customerFileHeaderEntity);
        customerFileHeaderEntity.setProcessDate(new Date());
        customerFileHeaderEntity.setTotalRow(customerFileDetails != null ? customerFileDetails.size() : 0);
        update(customerFileHeaderEntity);
    }
}
