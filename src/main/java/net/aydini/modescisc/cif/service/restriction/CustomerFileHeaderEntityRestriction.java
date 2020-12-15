package net.aydini.modescisc.cif.service.restriction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import net.aydini.modescisc.cif.domain.dto.filter.CustomerFileFilter;
import net.aydini.modescisc.cif.service.framework.RestrictionTemplate;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class CustomerFileHeaderEntityRestriction extends RestrictionTemplate<CustomerFileFilter> {

    
    private CustomerFileFilter filter;
    
    


    public CustomerFileHeaderEntityRestriction(CustomerFileFilter filter)
    {
       this.filter = filter;
    }




    @Override
    protected Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder)
    {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        predicateList.add(criteriaBuilder.isNull(rootEntity.get("deletedDate")));
        
        if(StringUtils.isNotEmpty(filter.getFileName()))
            predicateList.add(criteriaBuilder.like(rootEntity.get("fileName"),"%" +filter.getFileName() + "%"));
        
        
        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}