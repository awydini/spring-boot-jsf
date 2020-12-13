package net.aydini.modescisc.cif.service.framework;

import org.springframework.data.jpa.domain.Specification;

import net.aydini.modescisc.cif.domain.dto.filter.Filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */
public abstract class RestrictionTemplate<E extends Filter> implements JPARestriction{

    @Override
    public Specification countSpec(CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root root) {
        return (rootEntity, query, builder) -> applyFilter(rootEntity, query, criteriaBuilder);
    }

    @Override
    public Specification listSpec(CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery, Root root) {
        return (rootEntity, query, builder) -> applyFilter(rootEntity, query, criteriaBuilder);
    }

    protected abstract Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder);

}
