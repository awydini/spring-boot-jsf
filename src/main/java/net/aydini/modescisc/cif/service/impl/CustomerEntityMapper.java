package net.aydini.modescisc.cif.service.impl;

import java.util.Date;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.domain.CustomerFileHeaderTitle;
import net.aydini.modescisc.cif.domain.Gender;
import net.aydini.modescisc.cif.domain.entity.CustomerEntity;
import net.aydini.modescisc.cif.exception.CustomerValidationException;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.Mapper;
import net.aydini.modescisc.cif.util.PersianDateUtils;
import net.aydini.modescisc.cif.util.SimpleTokenizer;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

@Slf4j
public class CustomerEntityMapper implements Mapper<SimpleTokenizer, CustomerEntity>
{

    @Override
    public CustomerEntity map(SimpleTokenizer input) throws ServiceException
    {
        validate(input);

        try
        {
            
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setName(input.getStringToken(CustomerFileHeaderTitle.NAME.name()));
            customerEntity.setFamily(input.getStringToken(CustomerFileHeaderTitle.FAMILY.name()));
            customerEntity.setNationalCode(input.getStringToken(CustomerFileHeaderTitle.NATIONAL_CODE.name()));
            customerEntity.setGender(convertGender(input.getStringToken(CustomerFileHeaderTitle.GENDER.name())));
            Date birthDate = PersianDateUtils.getInstance().convertPersianDate(input.getStringToken(CustomerFileHeaderTitle.BIRTH_DATE.name()),"-");
            customerEntity.setBirthDate(birthDate);
            
            return customerEntity;
        }
        catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(),e.getArgs(),e);
            
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(),null,e);
        }
    }
    
    
    private Gender convertGender(String genderValue)
    {
       return  Stream.of(Gender.values()).filter(item-> item.getValue().equals(genderValue)).findAny().orElseThrow(()->new ServiceException(CustomerValidationException.FIELD_IS_NOT_VALID,genderValue));
    }
    
    private void validate(SimpleTokenizer input)
    {
        if(input == null)
            throw new ServiceException("null tokenizer");
        if(input.hasNames() == false)
            throw new ServiceException("invalid tokenizer");
    }

}
