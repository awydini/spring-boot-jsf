package net.aydini.modescisc.cif.net.aydini.modescisc.cif.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import net.aydini.modescisc.cif.util.SimpleTokenizer;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

public class SimpleTokenizerTests {

    private static String inputString;
    private static String[] names;
    private SimpleTokenizer tokenizer;


    public static enum Names
    {
        FROM_TO,DEATH_DATE,MASKED_NATIONAL_CODE,BIRTH_DATE,FAMILY;
    }

    @BeforeAll
    public static void setUp()
    {
        inputString = "2018090920200909,20190811,003****297,13251229 ,family";

        int arrayLength = Names.values().length;
        names = new String[arrayLength];
        for(int i = 0 ; i< arrayLength ; i++)
            names[i]=Names.values()[i].name();
    }

    @BeforeEach
    public void beforeEch()
    {
        tokenizer = new SimpleTokenizer(inputString,",");
    }

    @Test
    public void nextElementTest()
    {
        System.out.println("running test nextElementTest  "  );
        System.out.println("=======================================================================================" );
        
        assertEquals(5,tokenizer.countTokens());
        assertEquals("2018090920200909",tokenizer.nextElement());
        assertEquals("20190811",tokenizer.nextElement());
        assertEquals("003****297",tokenizer.nextElement());
        assertEquals("13251229",tokenizer.nextElement());
        assertEquals("family",tokenizer.nextElement());
    }

    @Test
    public void shouldThrowNSEExceptionOnNextElement()
    {
        System.out.println("running test shouldThrowNSEExceptionOnNextElement  "  );
        System.out.println("=======================================================================================" );
        for (int i = 0 ; i < tokenizer.countTokens() ; i++)
            tokenizer.nextElement();
        assertThrows(NoSuchElementException.class,()->tokenizer.nextElement());
    }


    @Test
    public void tokenNamesTest()
    {

        System.out.println("running test tokenNamesTest  "  );
        System.out.println("=======================================================================================" );
        tokenizer.setNames(names);
        assertEquals("2018090920200909",tokenizer.getStringToken(Names.FROM_TO.name()));
        assertEquals("13251229",tokenizer.getStringToken(Names.BIRTH_DATE.name()));
        assertEquals("family",tokenizer.getStringToken(Names.FAMILY.name()));
        assertEquals("20190811",tokenizer.getStringToken(Names.DEATH_DATE.name()));
        assertEquals("003****297",tokenizer.getStringToken(Names.MASKED_NATIONAL_CODE.name()));
    }

    @Test
    public void shouldThrowsNSEExceptionOnNoneExistingToken()
    {
        System.out.println("running test shouldThrowsNSEExceptionOnNoneExistingToken  "  );
        System.out.println("=======================================================================================" );
        tokenizer.setNames(names);
        assertThrows(NoSuchElementException.class,()->tokenizer.getStringToken("none"));
    }


    @ParameterizedTest
    @NullSource
    public void nullAndEmptyInputConstructorTest(String arg)
    {
        System.out.println("running test nullAndEmptyInputConstructorTest  "  );
        System.out.println("=======================================================================================" );
        assertThrows(NullPointerException.class,()->new SimpleTokenizer(arg));
    }


    @Test
    public void shouldHaveMoreElementsTest()
    {
        System.out.println("running test shouldHaveMoreElementsTest  "  );
        System.out.println("=======================================================================================" );
        for(int i = 0 ; i < tokenizer.countTokens() ; i++)
        {
            assertTrue(tokenizer.hasMoreElements());
            tokenizer.nextElement();
        }
        assertFalse(tokenizer.hasMoreElements());
    }
}