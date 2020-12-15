package net.aydini.modescisc.cif.net.aydini.modescisc.cif.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import net.aydini.modescisc.cif.dao.CustomerDao;
import net.aydini.modescisc.cif.exception.NationalCodeValidationException;
import net.aydini.modescisc.cif.service.impl.NationalCodeValidatorService;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class NationalCodeValidatorTests
{
    
    private CustomerDao customerDao = mock(CustomerDao.class);
    private NationalCodeValidatorService nationalCodeValidatorService = new NationalCodeValidatorService(customerDao);
    
    
    public static class InvalidNationalCodeArgumentProvider implements ArgumentsProvider
    {

        private List<String> invalidNationalCodes = new ArrayList<>();
        
        private List<String> getInvalidNationalCodes()
        {
            invalidNationalCodes.add("");
            invalidNationalCodes.add("1111111111"); //10 repeated digits
            invalidNationalCodes.add("012700028"); //10 digit invalid format
            invalidNationalCodes.add("12345679s"); // 10 none numeric character
            invalidNationalCodes.add("00127");     //5 digits
            
            return invalidNationalCodes;
        }
        
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception
        {
            return getInvalidNationalCodes().stream().map(item->Arguments.of(item));
        }
        
    }
    
    
    @ParameterizedTest
    @ArgumentsSource(value =InvalidNationalCodeArgumentProvider.class)
    public void shouldThrowNationalCodValidationExcrptionForInvalid(String invalidNationalCode)
    {
        System.out.println("running test shouldThrowNationalCodValidationExcrptionForInvalid with argument " + invalidNationalCode );
        
        when(customerDao.countNationalCode(invalidNationalCode, null)).thenReturn(0l);
        assertThrows(NationalCodeValidationException.class, ()->nationalCodeValidatorService.validate(invalidNationalCode, null));
        System.out.println("========================================OK=============================================" );
    }
    
    @ParameterizedTest
    @NullSource
    public void shouldThrowNationalCodValidationExcrptionForNullNationalCode(String  nationalCode)
    {
        System.out.println("running test shouldThrowNationalCodValidationExcrptionForNullNationalCode with argument " + nationalCode );
        
        when(customerDao.countNationalCode(nationalCode, null)).thenReturn(0l);
        assertThrows(NationalCodeValidationException.class, ()->nationalCodeValidatorService.validate(nationalCode, null));
        System.out.println("========================================OK=============================================" );
    }
    
    @Test
    public void shouldThrowNationalCodValidationExcrptionForDuplicateNationalCode()
    {
        final String  validNationalCode="0891121048";
        System.out.println("running test shouldThrowNationalCodValidationExcrptionForDuplicateNationalCode with argument  " + validNationalCode  );
        
        when(customerDao.countNationalCode(validNationalCode, 1l)).thenReturn(1l);
        assertThrows(NationalCodeValidationException.class, 
                ()->nationalCodeValidatorService.validate(validNationalCode, 1l)
                ,NationalCodeValidationException.NATIONAL_CODE_IS_DUPLICATED);
        System.out.println("========================================OK=============================================" );
        
    }


}
