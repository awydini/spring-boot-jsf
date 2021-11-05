package net.aydini.modescisc.cif.service.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.aydini.modescisc.cif.dao.BaseDao;
import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;
import net.aydini.modescisc.cif.exception.ServiceException;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public abstract class AbstractService<E extends BaseEntityModel>
{

    @PersistenceContext(unitName = "entityManagerFactory")
    protected EntityManager entityManager;
    public static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(AbstractService.class.getName());

    public AbstractService()
    {
    }

    public abstract BaseDao<E> getDao();

    protected Map<String, String> getHints()
    {
        return null;
    }

    protected Sort applySort(List<SortProperty> sortFields)
    {
        if (CollectionUtils.isEmpty(sortFields))
        {
            return null;
        }
        else
        {
            List<Sort.Order> orders = new ArrayList<>();
            Iterator<SortProperty> var3 = sortFields.iterator();

            while (var3.hasNext())
            {
                SortProperty sp = (SortProperty) var3.next();
                if (SortOrder.ASCENDING.equals(sp.getSortOrder()))
                {
                    orders.add(new Sort.Order(Sort.Direction.ASC, sp.getName()));
                }
                else
                {
                    orders.add(new Sort.Order(Sort.Direction.DESC, sp.getName()));
                }
            }

            return null;
        }
    }

    @Transactional(rollbackFor = { Exception.class, ServiceException.class })
    public void insert(E entity, boolean flush) throws ServiceException
    {
        boolean success = true;

        try
        {
            this.beforeInsert(entity);
            this.getDao().save(entity);
            if (flush)
            {
                this.getDao().flush();
            }
        }
        catch (ServiceException var10)
        {
            success = false;
            throw var10;
        }
        catch (DataAccessException | TransactionException | ConstraintViolationException var11)
        {
            success = false;
            throw new ServiceException(var11);
        }
        catch (Exception var12)
        {
            success = false;
            throw new ServiceException(var12);
        }
        finally
        {
            this.afterInsert(entity, success);
        }

    }

    @Transactional(rollbackFor = { Exception.class, ServiceException.class })
    public void insert(E entity) throws ServiceException
    {
        this.insert(entity, true);
    }

    public void delete(Long id)
    {
        this.getDao().deleteById(id);
    }

    @Transactional(rollbackFor = { Exception.class, ServiceException.class })
    public void update(E entity, boolean flush) throws ServiceException
    {
        boolean success = true;

        try
        {
            this.beforeUpdate(entity);
            this.getDao().save(entity);
            if (flush)
            {
                this.getDao().flush();
            }
        }
        catch (ServiceException var10)
        {
            success = false;
            throw var10;
        }
        catch (DataAccessException | TransactionException | ConstraintViolationException var11)
        {
            success = false;
            throw new ServiceException(var11);
        }
        catch (Exception var12)
        {
            success = false;
            throw new ServiceException(var12);
        }
        finally
        {
            this.afterUpdate(entity, success);
        }

    }

    public void flush()
    {
        this.getDao().flush();
    }

    @Transactional(rollbackFor = { Exception.class, ServiceException.class })
    public void update(E entity) throws ServiceException
    {
        this.update(entity, true);
    }

    protected void afterUpdate(E entity, boolean success)
    {
    }

    protected void beforeUpdate(E entity)
    {
    }

    protected void afterInsert(E entity, boolean success)
    {
    }

    protected void beforeInsert(E entity)
    {
    }

    @Transactional(readOnly = true)
    public E findById(Class<E> clz, Object id)
    {
        return this.getEntityManager().find(clz, id);
    }

    public Iterable<E> findAll()
    {
        return this.getDao().findAll();
    }

    public Iterable<E> findAll(Specification<E> spec)
    {
        return this.getDao().findAll(spec);
    }

    protected EntityManager getEntityManager()
    {
        return this.entityManager;
    }

    @Transactional(readOnly = true)
    public Page<E> load(Pageable pageable, Class<E> clz, JPARestriction restriction)
    {
        return this.load(pageable, clz, restriction, (Sort) null);
    }

    @Transactional(readOnly = true)
    public Page<E> load(Pageable pageable, Class<E> clz, JPARestriction restriction, Sort sort)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(clz);
        Root<E> root = criteria.from(clz);
        criteria.select(root);
        if (restriction != null)
        {
            Specification listSpec = restriction.listSpec(builder, criteria, root);
            if (listSpec != null)
            {
                Predicate predicate = listSpec.toPredicate(root, criteria, builder);
                if (predicate != null)
                {
                    criteria.where(predicate);
                }
            }
        }

        if (sort != null)
        {
            List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
            sort.forEach((s) ->
            {
                if (StringUtils.isNotEmpty(s.getProperty()))
                {
                    if (s.isAscending())
                    {
                        orderList.add(builder.asc(root.get(s.getProperty())));
                    }
                    else
                    {
                        orderList.add(builder.desc(root.get(s.getProperty())));
                    }
                }

            });
            if (!CollectionUtils.isEmpty(orderList))
            {
                criteria.orderBy(orderList);
            }
        }

        TypedQuery<E> createQuery = this.entityManager.createQuery(criteria);
        createQuery.setFirstResult((int) pageable.getOffset());
        if (pageable.getPageSize() > 0)
        {
            createQuery.setMaxResults(pageable.getPageSize());
        }

        List<E> result = createQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () ->
        {
            return this.count(clz, restriction);
        });
    }

    @Transactional(readOnly = true)
    public Long count(Class<E> clz, JPARestriction restriction)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<E> root = criteria.from(clz);
        criteria.select(builder.count(root));
        if (restriction != null)
        {
            Specification listSpec = restriction.countSpec(builder, criteria, root);
            if (listSpec != null)
            {
                Predicate predicate = listSpec.toPredicate(root, criteria, builder);
                if (predicate != null)
                {
                    criteria.where(predicate);
                }
            }
        }

        return (Long) this.entityManager.createQuery(criteria).getSingleResult();
    }

    @Transactional(readOnly = true)
    public List<E> load(int first, int pageSize, List<SortProperty> sortFields, Map<String, Object> filters, Class<E> clz,
            JPARestriction restriction)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(clz);
        Root<E> root = criteria.from(clz);
        criteria.select(root);
        if (restriction != null)
        {
            Specification listSpec = restriction.listSpec(builder, criteria, root);
            if (listSpec != null)
            {
                Predicate predicate = listSpec.toPredicate(root, criteria, builder);
                if (predicate != null)
                {
                    criteria.where(predicate);
                }
            }
        }

        if (!CollectionUtils.isEmpty(sortFields))
        {
            List<javax.persistence.criteria.Order> orderList = new ArrayList();
            sortFields.forEach((item) ->
            {
                if (SortOrder.ASCENDING.equals(item.getSortOrder()))
                {
                    orderList.add(builder.asc(root.get(item.getName())));
                }
                else
                {
                    orderList.add(builder.desc(root.get(item.getName())));
                }

            });
            criteria.orderBy(orderList);
        }

        TypedQuery<E> createQuery = this.entityManager.createQuery(criteria);
        createQuery.setFirstResult(first);
        if (pageSize > 0)
        {
            createQuery.setMaxResults(pageSize);
        }

        List<E> result = createQuery.getResultList();
        return result;
    }

    public E readForUpdate(Class<E> clz, Long id, String jsonContent)
    {
        E entityOp = this.findById(clz, id);
        if (entityOp == null)
        {
            throw new ServiceException("EntityNotFound", null);
        }
        else
        {
            Object entity = entityOp;

            try
            {
                mapper.readerForUpdating(entity).readValue(jsonContent);
            }
            catch (IOException var7)
            {
                logger.info(var7.getMessage());
            }

            return entityOp;
        }
    }
}
