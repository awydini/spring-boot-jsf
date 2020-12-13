package net.aydini.modescisc.cif.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.dao.CustomerDao;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerEntity;
import net.aydini.modescisc.cif.exception.CustomerValidationException;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Service
@Slf4j
public class CustomerCrudService extends AbstractCrudService<CustomerEntity> {

    private final CustomerDao customerDao;

    private final NationalCodeValidatorService nationalCodeValidatorService;

    @Autowired
    public CustomerCrudService(CustomerDao customerDao,NationalCodeValidatorService nationalCodeValidatorService) {
        this.customerDao = customerDao;
        this.nationalCodeValidatorService=nationalCodeValidatorService;
    }


    @Override
    public BaseDao<CustomerEntity> getDao() {
        return customerDao;
    }


    @Override
    public void insert(CustomerEntity customerEntity)
    {
        try
        {
            validate(customerEntity);
            super.insert(customerEntity,true);
        }
        catch (ServiceException exception)
        {
            log.error("error while inserting customer : " + customerEntity + " cuase : " + exception.getMessage());
            throw new ServiceException(exception.getMessage(),exception.getArgs(),exception);
        }
    }

    private void validate(CustomerEntity customerEntity)
    {
        if(customerEntity == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"CUSTOMER"});
        if(StringUtils.isEmpty(customerEntity.getName()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"FIRST_NAME"});
        if(StringUtils.isEmpty(customerEntity.getFamily()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"LAST_NAME"});
        if(customerEntity.getBirthDate() == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"Birth_Date"});
        if(StringUtils.isEmpty(customerEntity.getNationalCode()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"National_No"});
        if(customerEntity.getGender() == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID,new Object[]{"Birth_Date"});
        nationalCodeValidatorService.validate(customerEntity.getNationalCode(),customerEntity.getId());
    }
}
