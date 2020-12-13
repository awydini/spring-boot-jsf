package net.aydini.modescisc.cif.domain.entity.framework;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 */


@MappedSuperclass
public abstract class BaseEntityModel extends AbstractEntityModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2704095076651709280L;
    @Column(
            name = "deleted_date"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @Column(
            name = "created_date"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(
            name = "updated_date"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(
            name = "version",
            columnDefinition = " integer DEFAULT 0 "
    )
    @Version
    private int version;

    public BaseEntityModel() {
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return this.deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

