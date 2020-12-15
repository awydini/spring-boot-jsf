package net.aydini.modescisc.cif.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.dao.CustomerFileDetailDao;
import net.aydini.modescisc.cif.domain.entity.CustomerEntity;
import net.aydini.modescisc.cif.domain.entity.CustomerFileDetailEntity;
import net.aydini.modescisc.cif.domain.entity.CustomerFileHeaderEntity;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.util.SpringUtils;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */

@Slf4j
@Service
public class CustomerFileDetailService extends AbstractCrudService<CustomerFileDetailEntity>
{

    private final CustomerFileDetailDao customerFileDetailDao;

    private final CustomerService customerService;

    @Autowired
    public CustomerFileDetailService(CustomerFileDetailDao customerFileDetailDao, CustomerService customerService)
    {
        this.customerFileDetailDao = customerFileDetailDao;
        this.customerService = customerService;
    }

    @Override
    public BaseDao<CustomerFileDetailEntity> getDao()
    {
        return customerFileDetailDao;
    }

    public List<CustomerFileDetailEntity> processCustomerFile(CustomerFileHeaderEntity customerFileHeaderEntity)
    {
        Scanner scanner = new Scanner(new ByteArrayInputStream(customerFileHeaderEntity.getFileContent()), "UTF-8");
        List<CustomerFileDetailEntity>  customerFileDetails = new ArrayList<>();
        int lineNumberCounter = 0;
        while (scanner.hasNextLine())
        {
            lineNumberCounter++;
            CustomerFileDetailEntity createCustomerFileDetail = createCustomerFileDetail(scanner.nextLine(),lineNumberCounter,customerFileHeaderEntity);
            customerFileDetails.add(createCustomerFileDetail);
        }
        scanner.close();
        return customerFileDetails;
    }

    @Transactional
    private CustomerFileDetailEntity createCustomerFileDetail(String line,int lineNumber,CustomerFileHeaderEntity customerFileHeaderEntity)
    {
        CustomerFileDetailEntity customerFileDetailEntity = new CustomerFileDetailEntity();
        customerFileDetailEntity.setCustomerFileHeaderEntity(customerFileHeaderEntity);
        customerFileDetailEntity.setRowNumber(lineNumber);
        customerFileDetailEntity.setRowData(line);
        try
        {
            CustomerEntity customerEntity = customerService.persistFromFile(line);
            customerFileDetailEntity.setCustomerEntity(customerEntity);
            customerFileDetailEntity.setSucceed(true);
        }
        catch (ServiceException e)
        {
           log.error("failed to process line : " + line );
           log.error(e.getMessage()  + " " + e.getArgs());
           customerFileDetailEntity.setSucceed(false);
           String errorMessage = e.getMessage() != null ? e.getMessage() : "invalid record";
           customerFileDetailEntity.setErrorDescription(SpringUtils.getMessage(errorMessage ,e.getArgs()));
           
        }
        
        insert(customerFileDetailEntity,true);
        return customerFileDetailEntity;
    }
    
    
    public List<CustomerFileDetailEntity> findAllByHeaderId(Long headerId)
    {
        return customerFileDetailDao.findAllByHeaderId(headerId);
    }

}
