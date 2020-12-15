package net.aydini.modescisc.cif.domain.entity.cif;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
@Table(name = "customer_file_header")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFileHeaderEntity extends BaseEntityModel 
{
    /**
     * 
     */
    private static final long serialVersionUID = -384512463068579213L;
    
    
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "file_content", length = 2147483647)
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileContent; 
    
    private Integer totalRow;
    
    private String fileName;
    
    private Date processDate; 
    
    
    public boolean isProcessed()
    {
        return processDate != null;
    }
    
}
