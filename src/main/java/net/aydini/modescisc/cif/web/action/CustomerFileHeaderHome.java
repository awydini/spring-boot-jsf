package net.aydini.modescisc.cif.web.action;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.faces.application.FacesMessage;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.navigate.Navigate;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileDetailEntity;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileHeaderEntity;
import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.service.impl.CustomerFileDetailService;
import net.aydini.modescisc.cif.service.impl.CustomerFileHeaderService;
import net.aydini.modescisc.cif.util.SpringUtils;
import net.aydini.modescisc.cif.web.action.framework.AbstractHomeActionBean;
import net.aydini.modescisc.cif.web.action.framework.SpringViewScope;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */
@Component("customerFileHeaderHome")
@ELBeanName("customerFileHeaderHome")
@SpringViewScope
@Join(path = "/secure/customerFileEdit", to = "/secure/customerFileHeaderEdit.xhtml")
@Slf4j
public class CustomerFileHeaderHome extends AbstractHomeActionBean<CustomerFileHeaderEntity>
{

    /**
     * 
     */
    private static final long serialVersionUID = 5461465172603829923L;

    private final CustomerFileHeaderService customerFileHeaderService;

    private final CustomerFileDetailService customerFileDetailService;

    private UploadedFile customerInformationFile;
    private List<CustomerFileDetailEntity> details;

    @Autowired
    public CustomerFileHeaderHome(CustomerFileHeaderService customerFileHeaderService, CustomerFileDetailService customerFileDetailService)
    {
        this.customerFileHeaderService = customerFileHeaderService;
        this.customerFileDetailService = customerFileDetailService;
    }

    public AbstractCrudService<CustomerFileHeaderEntity> getService()
    {
        return customerFileHeaderService;
    }

    private void wire()
    {
        if(getInstance().getId()!= null)
            throw new ServiceException("cif.batch.already.processed");
        if (getCustomerInformationFile() == null || getCustomerInformationFile().getContents() == null
                || getCustomerInformationFile().getContents().length == 0)
            throw new ServiceException("Choose_File");
        getInstance().setFileName(getCustomerInformationFile().getFileName());
        getInstance().setFileContent(getCustomerInformationFile().getContents());
        log.info("successfully uploaded");
    }

    public void persist()
    {
        try
        {
            wire();
            save();
            setCustomerFileHeaderId(getInstance().getId());
            RequestContext.getCurrentInstance().update("customerListForm:customerListTable,fileHeaderForm:process");
            
            CompletableFuture.runAsync(()->customerFileHeaderService.processCustomerFileHeader(getInstance()));
        }
        catch (ServiceException e)
        {
            log.error(e.getMessage());
            addErrorMessage("error", SpringUtils.getMessage(e.getMessage(), e.getArgs()));
        }
    }

    public Navigate merge()
    {
        try
        {
            wire();
            update();
            return Navigate.to("/secure/customerFileHeaderList.xhtml");
        }
        catch (ServiceException e)
        {
            log.error(e.getMessage());
            addErrorMessage("error", SpringUtils.getMessage(e.getMessage(), e.getArgs()));
        }
        return null;
    }

    public Navigate remove()
    {
        try
        {
            customerFileHeaderService.deleteSoft(getCustomerFileHeaderId());
            String msg = messageSource.getMessage("Successfully_deleted", null, getCurrentLocale());
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
            getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return Navigate.to("/secure/customerFileHeaderList.xhtml");
        }
        catch (DataIntegrityViolationException e)
        {
            log.error(e.getMessage());
            addErrorMessage("error", SpringUtils.getMessage("DataIntegrityViolationException", null));
            return Navigate.to("/secure/customerEdit.xhtml");
        }
    }

    //

    public StreamedContent getSampleFile()
    {
        return new DefaultStreamedContent(this.getClass().getResourceAsStream("/sample.txt"), "aplication/txt", "sample.txt");

    }

    public UploadedFile getCustomerInformationFile()
    {
        return customerInformationFile;
    }

    public void setCustomerInformationFile(UploadedFile customerInformationFile)
    {
        this.customerInformationFile = customerInformationFile;
    }

    public Long getCustomerFileHeaderId()
    {
        return (Long) getId();
    }

    public void setCustomerFileHeaderId(Long customerFileHeaderId)
    {
        setId((Long) customerFileHeaderId);
    }

    public List<CustomerFileDetailEntity> getDetails()
    {
        details = customerFileDetailService.findAllByHeaderId(getCustomerFileHeaderId());
        return details;
    }
    
    public boolean shouldStop()
    {
        return getInstance().getProcessDate() != null;
    }

}
