package net.aydini.modescisc.cif.web.action.framework;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;
import net.aydini.modescisc.cif.service.framework.AbstractCrudService;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */

public abstract class AbstractHomeActionBean<E extends BaseEntityModel> extends AbstractActionBean {
    /**
     * 
     */
    private static final long serialVersionUID = 931808006119900068L;
    
    
    private static final Logger logger = Logger.getLogger(AbstractHomeActionBean.class.getName());
    private Class<E> entityClass;
    protected E instance;
    private Object id;
    @Autowired
    protected transient MessageSource messageSource;

    public AbstractHomeActionBean() {
    }

    protected E createInstance() {
        if (this.getEntityClass() != null) {
            try {
                return this.getEntityClass().newInstance();
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        } else {
            return null;
        }
    }

    public void clearInstance() {
        this.instance = null;
        this.id = null;
    }

    @Transactional
    public E getInstance() {
        if (this.instance == null) {
            this.initInstance();
        }

        return this.instance;
    }

    protected void initInstance() {
        if (this.isIdDefined()) {
            this.setInstance(this.find());
        } else {
            this.setInstance(this.createInstance());
        }

    }

    public boolean isIdDefined() {
        return this.getId() != null && !"".equals(this.getId());
    }

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public void setInstance(E instance) {
        this.instance = instance;
    }

    public Class<E> getEntityClass() {
        if (this.entityClass == null) {
            Type type = this.getClass().getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                throw new IllegalArgumentException("Could not guess entity class by reflection");
            }

            ParameterizedType paramType = (ParameterizedType)type;
            if (paramType.getActualTypeArguments().length == 2) {
                if (paramType.getActualTypeArguments()[1] instanceof TypeVariable) {
                    throw new IllegalArgumentException("Could not guess entity class by reflection");
                }

                this.entityClass = (Class)paramType.getActualTypeArguments()[1];
            } else {
                this.entityClass = (Class)paramType.getActualTypeArguments()[0];
            }
        }

        return this.entityClass;
    }

    public abstract AbstractCrudService<E> getService();

    public void save() {
        this.getService().insert(this.getInstance());
        this.getCurrentInstance().addMessage((String)null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.createdMessage()));
        this.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    protected String createdMessage() {
        String code = this.getMessageKeyPrefix() + "created";
        return this.messageSource.getMessage(code, (Object[])null, this.getCurrentLocale());
    }

    protected String updatedMessage() {
        String code = this.getMessageKeyPrefix() + "updated";
        return this.messageSource.getMessage(code, (Object[])null, this.getCurrentLocale());
    }

    protected String deletedMessage() {
        String code = this.getMessageKeyPrefix() + "deleted";
        return this.messageSource.getMessage(code, (Object[])null, this.getCurrentLocale());
    }

    protected String getMessageKeyPrefix() {
        String className = this.getEntityClass().getName();
        return className.substring(className.lastIndexOf(46) + 1) + '_';
    }

    public void update() {
        this.getService().update(this.getInstance());
        this.getCurrentInstance().addMessage((String)null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.updatedMessage()));
        this.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void delete() {
        this.getService().deleteSoft((Long)this.getId());
        this.getCurrentInstance().addMessage((String)null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.deletedMessage()));
        this.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public void load() {
        logger.info("load");
    }

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    protected E find() {
        return this.getService().findById(this.getEntityClass(), this.getId());
    }



    protected Validator getValidator() {
        return null;
    }

    public void addErrorMessage(String summary ,String message)
    {
        getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,message));
        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}

