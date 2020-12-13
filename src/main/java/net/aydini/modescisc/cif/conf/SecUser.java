package net.aydini.modescisc.cif.conf;

import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class SecUser extends org.springframework.security.core.userdetails.User
{

    public SecUser(String username, String password, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Long getId()
    {
        return Long.parseLong(RandomStringUtils.randomNumeric(10));
    }

}
