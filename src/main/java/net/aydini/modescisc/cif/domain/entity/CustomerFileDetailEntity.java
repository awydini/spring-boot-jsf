package net.aydini.modescisc.cif.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 * Dec 14, 2020
 */
@Entity
@Table(name = "customer_file_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFileDetailEntity extends BaseEntityModel
{
    /**
     * 
     */
    private static final long serialVersionUID = -2957321317437687674L;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerFileHeaderEntity customerFileHeaderEntity;

    @OneToOne(fetch = FetchType.LAZY)
    private CustomerEntity customerEntity;

    private Integer rowNumber;

    private String rowData;

    private Boolean succeed;

    private String errorDescription;
}