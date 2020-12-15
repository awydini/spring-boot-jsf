package net.aydini.modescisc.cif.web.action;

import javax.faces.application.FacesMessage;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.domain.Gender;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerEntity;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.service.impl.CustomerService;
import net.aydini.modescisc.cif.util.SpringUtils;
import net.aydini.modescisc.cif.web.action.framework.AbstractHomeActionBean;
import net.aydini.modescisc.cif.web.action.framework.SpringViewScope;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Component("customerHome")
@ELBeanName("customerHome")
@SpringViewScope
@Join(path = "/secure/customerEdit", to = "/secure/customerEdit.xhtml")
public class CustomerHome extends AbstractHomeActionBean<CustomerEntity>
{

    /**
     * 
     */
    private static final long serialVersionUID = -7570746194832889030L;
    private final CustomerService customerService;

    @Autowired
    public CustomerHome(CustomerService customerService) {
        this.customerService = customerService;
    }


    private Long customerId;



    public AbstractCrudService<CustomerEntity> getService()
    {
        return customerService;
    }

    public void load() {

    }

    private void wire()
    {

    }

    public Navigate persist()
    {
        try {
            wire();
            customerService.insert(getInstance());
            return Navigate.to("/secure/customerList.xhtml");
        }catch (ServiceException e)
        {
            addErrorMessage("error",SpringUtils.getMessage(e.getMessage(), e.getArgs()));
        }
        return null;
    }



    public Navigate merge() {
        try {
            wire();
            update();
            return Navigate.to("/secure/customerList.xhtml");
        }catch (ServiceException e)
        {
            addErrorMessage("error",SpringUtils.getMessage(e.getMessage(), e.getArgs()));
        }
        return null;
    }

    public Navigate remove()
    {
        try
        {
            customerService.deleteSoft(getCustomerId());
            String msg = messageSource.getMessage("Successfully_deleted", null, getCurrentLocale());
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
            getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return Navigate.to("/secure/customerList.xhtml");
        }
        catch (DataIntegrityViolationException e)
        {
            addErrorMessage("error",SpringUtils.getMessage("DataIntegrityViolationException", null));
            return Navigate.to("/secure/customerEdit.xhtml").with("customerId", getCustomerId());
        }
    }



    //
    public Long getCustomerId() {
        return (Long) getId();
    }

    public void setCustomerId(Long customerId) {
        super.setId(customerId);
    }

    public Gender[] getGenders()
    {
        return Gender.values();
    }
}
