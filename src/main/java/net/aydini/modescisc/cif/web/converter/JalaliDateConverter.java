package net.aydini.modescisc.cif.web.converter;


import java.time.LocalDate;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;

import net.aydini.modescisc.cif.util.PersianDateUtils;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Component("jalaliConverter")
public class JalaliDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equals("") )
        {
            return null;
        }
        else
        {
            try
            {
               return LocalDate.parse(value.substring(0,10));
            }
            catch (Exception exception)
            {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Date"));
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if(value instanceof Date)
        {
        	return PersianDateUtils.getInstance().getPersianDate((Date)value);
        }
        return null;
    }
}
