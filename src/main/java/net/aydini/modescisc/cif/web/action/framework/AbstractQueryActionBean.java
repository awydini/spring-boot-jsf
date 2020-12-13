package net.aydini.modescisc.cif.web.action.framework;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.util.CollectionUtils;

import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;
import net.aydini.modescisc.cif.service.framework.JPARestriction;
import net.aydini.modescisc.cif.service.framework.SortProperty;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */
public abstract class AbstractQueryActionBean<E extends BaseEntityModel> extends AbstractActionBean
{

    /**
     * 
     */
    private static final long serialVersionUID = -2485426853231359185L;

    private static final Logger logger = Logger.getLogger(AbstractQueryActionBean.class.getName());

    private ExtendedLazyDataModel<E> resultList;
    private Long resultCount;
    private E selectedEntity;
    private Set<E> selectedEntityList = new HashSet<E>();
    private Class<E> entityClass;


    public Set<E> getSelectedEntityList()
    {
        return selectedEntityList;
    }

    public void setSelectedEntityList(ArrayList<E> selectedEntityList)
    {
        // DO NOTHING
    }

    public void onRowSelectByCheckbox(SelectEvent event)
    {
        E selectedCompte = (E) event.getObject();
        getSelectedEntityList().add(selectedCompte);
    }

    public void onRowUnSelectByCheckbox(UnselectEvent event)
    {
        E unselectedCompte = (E) event.getObject();
        getSelectedEntityList().remove(unselectedCompte);
    }


    public abstract AbstractCrudService<E> getService();

    public E getSelectedEntity()
    {
        return selectedEntity;
    }

    public void setSelectedEntity(E selectedEntity)
    {
        this.selectedEntity = selectedEntity;
    }

    public void load()
    {
        logger.info("load");
    }

    @PostConstruct
    public void init()
    {
        logger.info("init");
        getEntityClass();
        initResultList();
    }

    protected void initResultList()
    {
        resultList = new ExtendedLazyDataModel<E>() {

            @Override
            protected List<E> lazyLoad(int first, int pageSize,
                                       List<SortProperty> sortFields, Map<String, Object> filters)
            {
                if (CollectionUtils.isEmpty(sortFields))
                    sortFields = getSortProperties();
                return getService().load(first, pageSize, sortFields, filters, getEntityClass(), getRestriction());
            }

            @Override
            protected Number rowCount(Map<String, Object> filters)
            {
                resultCount = getService().count(getEntityClass(), getRestriction());
                return resultCount;
            }

        };
    }

    protected List<SortProperty> getSortProperties()
    {
        return null;
    }

    public ExtendedLazyDataModel<E> getResultList()
    {
        return resultList;
    }

    public Long getResultCount()
    {
        return resultCount;
    }

    public Class<E> getEntityClass()
    {
        if (entityClass == null)
        {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType)
            {
                ParameterizedType paramType = (ParameterizedType) type;
                if (paramType.getActualTypeArguments().length == 2)
                {
                    if (paramType.getActualTypeArguments()[1] instanceof TypeVariable)
                    {
                        throw new IllegalArgumentException("Could not guess entity class by reflection");
                    }
                    else
                    {
                        entityClass = (Class<E>) paramType.getActualTypeArguments()[1];
                    }
                }
                else
                {
                    entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
                }
            }
            else
            {
                throw new IllegalArgumentException("Could not guess entity class by reflection");
            }
        }
        return entityClass;
    }

    protected JPARestriction getRestriction()
    {
        return null;
    }

    private PaginationHelper<E> pagination;

    public PaginationHelper<E> getPagination()
    {

        if (pagination == null)
        {

            pagination = new PaginationHelper<E>(getListPageSize()) {
                @Override
                public int getItemsCount()
                {
                    if(resultCount == null)
                        resultCount = getService().count(getEntityClass(), getRestriction());
                    return resultCount.intValue();
                }

                @Override
                public List<E> createPageDataModel()
                {
                    if(entityResultList == null)
                        entityResultList = getService().load(getPageFirstItem(), getPageSize(), getSortProperties(), null, getEntityClass(),
                                getRestriction());
                    return entityResultList;
                }
            };
        }
        return pagination;
    }


    protected int getListPageSize()
    {
        return 10;
    }

    private List<E> entityResultList;

    public List<E> getEntityResultList()
    {
        if (entityResultList == null)
            entityResultList = getPagination().createPageDataModel();
        return entityResultList;
    }

    public String next()
    {
        getPagination().nextPage();
        recreateModel();
        return null;
    }

    public String last()
    {
        getPagination().lastPage();
        recreateModel();
        return null;
    }

    public String first()
    {
        getPagination().firstPage();
        recreateModel();
        return null;
    }

    protected void recreateModel()
    {
        entityResultList = null;
        resultCount = null;
    }

    public String previous()
    {
        getPagination().previousPage();
        recreateModel();
        return null;
    }

}
