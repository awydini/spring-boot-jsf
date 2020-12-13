package net.aydini.modescisc.cif.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.aydini.modescisc.cif.dao.CustomerDao;
import net.aydini.modescisc.cif.exception.NationalCodeValidationException;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Service
public class NationalCodeValidatorService {

    private final static int NATIONAL_CODE_LENGTH = 10;
    private final CustomerDao customerDao;


    @Autowired
    public NationalCodeValidatorService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    public void validate(String nationalCode ,Long customerId)
    {
        if(StringUtils.isEmpty(nationalCode))
            throw new NationalCodeValidationException(NationalCodeValidationException.FIELD_IS_NOT_VALID, new Object[]{"NATIONAL_CODE"});
        if(nationalCode.length() != NATIONAL_CODE_LENGTH)
            throw new NationalCodeValidationException(NationalCodeValidationException.NATIONAL_CODE_LENGTH_ERROR, new Object[]{"NATIONAL_CODE"});
        if(!StringUtils.isNumeric(nationalCode))
            throw new NationalCodeValidationException(NationalCodeValidationException.NATIONAL_CODE_IS_NOT_NUMERIC,null);

        checkRepeatedDigits(nationalCode);
        checkFormat(nationalCode);
        checkIfDuplicated(nationalCode,customerId);
    }

    private void checkIfDuplicated(String nationalCode , Long customrId)
    {
        Long countOfNationalCode = null;
        if(customrId == null)
            countOfNationalCode = customerDao.countNationalCode(nationalCode);
        else
            countOfNationalCode = customerDao.countNationalCode(nationalCode,customrId);
        if(countOfNationalCode.longValue() > 0)
            throw new NationalCodeValidationException(NationalCodeValidationException.NATIONAL_CODE_IS_DUPLICATED,null);
    }


    private void checkRepeatedDigits(String nationalCode)
    {
        long longValue = Long.parseLong(nationalCode);
        if ((longValue % 1111111111 == 0) || longValue ==0)
            throw new NationalCodeValidationException(NationalCodeValidationException.FIELD_IS_NOT_VALID, new Object[]{nationalCode});
    }

    private void checkFormat(String nationalCode)
    {
        List<Integer> codes = tokenizeCodeToArray(nationalCode);
        int n = calculateCheckSum(codes);
        int res = n % 11;
        int resultCalculated = 0;
        switch (res)
        {
            case 1:
                resultCalculated = res;
                break;

            default:
                resultCalculated = 11 - res;
                break;
        }

        if (resultCalculated != codes.get(9))
        throw new NationalCodeValidationException(NationalCodeValidationException.NATIONAL_CODE_FORMAT_ERROR,null);
    }

    private int calculateCheckSum(List<Integer> codes)
    {
        int n = 0;
        for (int i = 0; i < 10 - 1; i++)
        {
            n += codes.get(i) * (10 - i);
        }
        return n;
    }

    private List<Integer> tokenizeCodeToArray(String value)
    {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < value.length(); i++)
        {
            result.add(Character.getNumericValue(value.charAt(i)));
        }
        return result;
    }

}
