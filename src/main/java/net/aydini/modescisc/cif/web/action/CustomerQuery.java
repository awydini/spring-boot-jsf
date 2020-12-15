package net.aydini.modescisc.cif.web.action;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.aydini.modescisc.cif.domain.dto.filter.CustomerFilter;
import net.aydini.modescisc.cif.domain.entity.CustomerEntity;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.service.framework.JPARestriction;
import net.aydini.modescisc.cif.service.impl.CustomerService;
import net.aydini.modescisc.cif.service.restriction.CustomerEntityRestriction;
import net.aydini.modescisc.cif.web.action.framework.AbstractQueryActionBean;
import net.aydini.modescisc.cif.web.action.framework.SpringViewScope;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
@Component("customerQuery")
@ELBeanName("customerQuery")
@SpringViewScope
@Join(path = "/secure/customerList", to = "/secure/customerList.xhtml")
public class CustomerQuery extends AbstractQueryActionBean<CustomerEntity> {

    /**
     * 
     */
    private static final long serialVersionUID = 6135924579580536832L;

    private final CustomerService customerService;

    private CustomerFilter customerFilter = new CustomerFilter();

    @Autowired
    public CustomerQuery(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public AbstractCrudService<CustomerEntity> getService() {
        return customerService;
    }


    public void resetSearch()
    {
        resetPagination("customerListForm:customerListTable");
        this.customerFilter =  new CustomerFilter();
    }

    @Override
    protected JPARestriction getRestriction()
    {
        return new CustomerEntityRestriction(customerFilter);
    }

    public CustomerFilter getCustomerFilter() {
        return customerFilter;
    }

    public void setCustomerFilter(CustomerFilter customerFilter) {
        this.customerFilter = customerFilter;
    }
}
