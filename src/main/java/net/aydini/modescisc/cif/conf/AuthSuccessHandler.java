package net.aydini.modescisc.cif.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        if (authentication != null && authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            boolean anonymous = principal instanceof String || principal.equals("anonymousUser");
            if (!anonymous)
            {
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
