package net.aydini.modescisc.cif.service.framework;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public interface JPARestriction {
    Specification countSpec(CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root root);

    Specification listSpec(CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root root);
}
