package net.aydini.modescisc.cif.service.restriction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import net.aydini.modescisc.cif.domain.dto.filter.CustomerFilter;
import net.aydini.modescisc.cif.service.framework.RestrictionTemplate;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class CustomerEntityRestriction extends RestrictionTemplate<CustomerFilter> {


    private CustomerFilter customerFilter;

    public CustomerEntityRestriction(CustomerFilter customerFilter) {
        this.customerFilter = customerFilter;
    }

    @Override
    protected Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder)
    {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        predicateList.add(criteriaBuilder.isNull(rootEntity.get("deletedDate")));
        if(StringUtils.isNotEmpty(customerFilter.getName()))
        {
            predicateList.add(criteriaBuilder.like(rootEntity.get("name"), "%"+customerFilter.getName()+ "%"));
        }

        if(StringUtils.isNotEmpty(customerFilter.getNationalCode()))
        {
            predicateList.add(criteriaBuilder.equal(rootEntity.get("nationalCode"), customerFilter.getNationalCode()));
        }

        if(StringUtils.isNotEmpty(customerFilter.getFamily()))
        {
            predicateList.add(criteriaBuilder.like(rootEntity.get("family"), "%"+customerFilter.getFamily()+ "%"));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}