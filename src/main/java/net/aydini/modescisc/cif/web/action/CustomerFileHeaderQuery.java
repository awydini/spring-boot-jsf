package net.aydini.modescisc.cif.web.action;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.aydini.modescisc.cif.domain.dto.filter.CustomerFileFilter;
import net.aydini.modescisc.cif.domain.entity.cif.CustomerFileHeaderEntity;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.service.framework.JPARestriction;
import net.aydini.modescisc.cif.service.impl.CustomerFileHeaderService;
import net.aydini.modescisc.cif.service.restriction.CustomerFileHeaderEntityRestriction;
import net.aydini.modescisc.cif.web.action.framework.AbstractQueryActionBean;
import net.aydini.modescisc.cif.web.action.framework.SpringViewScope;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Component("customerFileHeaderQuery")
@ELBeanName("customerFileHeaderQuery")
@SpringViewScope
@Join(path = "/secure/customerFileList", to = "/secure/customerFileHeaderList.xhtml")
public class CustomerFileHeaderQuery extends AbstractQueryActionBean<CustomerFileHeaderEntity> {


    /**
     * 
     */
    private static final long serialVersionUID = 2755405077258548185L;

    private final CustomerFileHeaderService customerFileHeaderService;
    
    private CustomerFileFilter filter = new CustomerFileFilter();


    @Autowired
    public CustomerFileHeaderQuery(CustomerFileHeaderService customerFileHeaderService) {
        this.customerFileHeaderService = customerFileHeaderService;
    }

    @Override
    public AbstractCrudService<CustomerFileHeaderEntity> getService() {
        return customerFileHeaderService;
    }


    public void resetSearch()
    {
        resetPagination("customerFileListForm:customerFileListTable");
        filter = new CustomerFileFilter();
    }

    @Override
    protected JPARestriction getRestriction()
    {
        return new CustomerFileHeaderEntityRestriction(filter);
    }

    public CustomerFileFilter getFilter()
    {
        return filter;
    }

    public void setFilter(CustomerFileFilter filter)
    {
        this.filter = filter;
    }

    
    
}
