package net.aydini.modescisc.cif.web.action;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import net.aydini.modescisc.cif.web.action.framework.AbstractActionBean;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

@Component("securityBean")
@Scope(WebApplicationContext.SCOPE_SESSION)
@ELBeanName("securityBean")
public class SecurityBean extends AbstractActionBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6323363071319312873L;

    public  Authentication getAuthentication() {
        return SecurityContextHolder.getContext() == null ? null : SecurityContextHolder.getContext().getAuthentication();
    }

    public  boolean isAuthenticated() {
        return SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public  boolean isLoggedIn() {
        return isAuthenticated() ? SecurityContextHolder.getContext().getAuthentication().isAuthenticated() : false;
    }

    public  boolean isAnonymous() {
        return isLoggedIn() && getAuthentication() instanceof AnonymousAuthenticationToken;
    }


}
