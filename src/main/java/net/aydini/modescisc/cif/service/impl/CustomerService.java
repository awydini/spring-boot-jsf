package net.aydini.modescisc.cif.service.impl;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.dao.CustomerDao;
import net.aydini.modescisc.cif.domain.CustomerFileHeaderTitle;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerEntity;
import net.aydini.modescisc.cif.exception.CustomerValidationException;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.util.SimpleLineMapper;
import net.aydini.modescisc.cif.util.SimpleTokenizer;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */
@Service
@Slf4j
public class CustomerService extends AbstractCrudService<CustomerEntity>
{

    private final CustomerDao customerDao;

    private final NationalCodeValidatorService nationalCodeValidatorService;

    @Autowired
    public CustomerService(CustomerDao customerDao, NationalCodeValidatorService nationalCodeValidatorService)
    {
        this.customerDao = customerDao;
        this.nationalCodeValidatorService = nationalCodeValidatorService;
    }

    @Override
    public BaseDao<CustomerEntity> getDao()
    {
        return customerDao;
    }

    @Override
    public void insert(CustomerEntity customerEntity)
    {
        try
        {
            validate(customerEntity);
            super.insert(customerEntity, true);
        }
        catch (ServiceException exception)
        {
            log.error("error while inserting customer : " + customerEntity + " cuase : " + exception.getMessage());
            throw new ServiceException(exception.getMessage(), exception.getArgs(), exception);
        }
    }

    private void validate(CustomerEntity customerEntity)
    {
        if (customerEntity == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "CUSTOMER" });
        if (StringUtils.isEmpty(customerEntity.getName()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "FIRST_NAME" });
        if (StringUtils.isEmpty(customerEntity.getFamily()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "LAST_NAME" });
        if (customerEntity.getBirthDate() == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "Birth_Date" });
        if (StringUtils.isEmpty(customerEntity.getNationalCode()))
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "National_No" });
        if (customerEntity.getGender() == null)
            throw new CustomerValidationException(CustomerValidationException.FIELD_IS_NOT_VALID, new Object[] { "Birth_Date" });
        nationalCodeValidatorService.validate(customerEntity.getNationalCode(), customerEntity.getId());
    }

    
    @Transactional(value = TxType.REQUIRES_NEW)
    public CustomerEntity persistFromFile(String line)
    {
        try
        {
            SimpleLineMapper<CustomerEntity> mapper = new SimpleLineMapper<CustomerEntity>(new CustomerDtoMapper());
            SimpleTokenizer tokenizer = getSimpleTokenizer(line);
            CustomerEntity customerEntity = mapper.map(tokenizer);
            insert(customerEntity);
            flush();
            return customerEntity;
        }
        catch (ServiceException e)
        {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e.getArgs(), e);
        }
    }

    private SimpleTokenizer getSimpleTokenizer(String line)
    {
        try
        {
            SimpleTokenizer tokenizer = new SimpleTokenizer(line, ",");
            tokenizer.setNames(getCustomerFileHeaderTitle());

            return tokenizer;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), new Object[] {}, e);
        }
    }

    private String[] getCustomerFileHeaderTitle()
    {
        int arrayLength = CustomerFileHeaderTitle.values().length;
        String[] headers = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++)
            headers[i] = CustomerFileHeaderTitle.values()[i].name();
        return headers;
    }

}
