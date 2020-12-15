package net.aydini.modescisc.cif.domain;
/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 */
public enum  Gender {
    MALE("M"),
    FEMALE("F");
    
    
    private String value;

    private Gender(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    
}
