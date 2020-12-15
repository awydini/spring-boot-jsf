package net.aydini.modescisc.cif.net.aydini.modescisc.cif.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.aydini.modescisc.cif.domain.CustomerFileHeaderTitle;
import net.aydini.modescisc.cif.domain.Gender;
import net.aydini.modescisc.cif.domain.entity.CustomerEntity;
import net.aydini.modescisc.cif.service.impl.CustomerEntityMapper;
import net.aydini.modescisc.cif.util.SimpleLineMapper;
import net.aydini.modescisc.cif.util.SimpleTokenizer;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class CustomerEntityMapperTests
{
    
    private SimpleLineMapper<CustomerEntity> mapper;
    private SimpleTokenizer tokenizer;
    private String line;
    private final String name = "Aydin";
    private final String family = "Nasrollahpour";
    private final String NationalCode = "0012700022";
    private final Gender gender = Gender.MALE;
    private  final String birthDate = "1369-08-25";
    private final String comma = ",";
    
    
    @BeforeEach
    public  void init()
    {
        line = name+comma+family+comma+NationalCode+comma+gender.getValue()+comma+birthDate;
        mapper = new SimpleLineMapper<CustomerEntity>(new CustomerEntityMapper());
        String[] headers = new String[CustomerFileHeaderTitle.values().length];
        tokenizer =new SimpleTokenizer(line, ",");
        tokenizer.setNames(Stream.of(CustomerFileHeaderTitle.values()).map(item->item.name()).collect(Collectors.toList()).toArray(headers));
    }

    
    @Test
    public void testCustomrEntityMapper()
    {
        System.out.println("running test testCustomrEntityMapper  "  );
        CustomerEntity map = mapper.map(tokenizer);
        assertEquals(name, map.getName());
        assertEquals(family, map.getFamily());
        assertEquals(NationalCode, map.getNationalCode());
        assertEquals(gender, map.getGender());
        
        System.out.println("========================================OK=============================================" );
    }
}
