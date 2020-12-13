package net.aydini.modescisc.cif.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.impl.NationalCodeValidatorService;
import net.aydini.modescisc.cif.util.SpringUtils;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

@Scope("request")
@Component("nationalCodeValidator")
public class NationalCodeValidator implements Validator {

    private final NationalCodeValidatorService nationalCodeValidatorService;

    @Autowired
    public NationalCodeValidator(NationalCodeValidatorService nationalCodeValidatorService) {
        this.nationalCodeValidatorService = nationalCodeValidatorService;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if(value == null )
            return;
        Long customerId = (Long) component.getAttributes().get("customerId");

        String nationalCode = (String) value;

        try
        {
            nationalCodeValidatorService.validate(nationalCode,customerId);
        }
        catch (ServiceException serviceException)
        {
            throw new ValidatorException(createMessage(serviceException));
        }

    }


    private FacesMessage createMessage(ServiceException exception)
    {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setSummary(SpringUtils.getMessage(exception.getMessage(), exception.getArgs()));
        return facesMessage;
    }
}
