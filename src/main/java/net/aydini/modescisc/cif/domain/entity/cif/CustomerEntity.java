package net.aydini.modescisc.cif.domain.entity.cif;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.aydini.modescisc.cif.domain.Gender;
import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends BaseEntityModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5167548546234437890L;

    private String name;

    private String family;

    private String nationalCode;

    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public String getFullName()
    {
        return getName() +" " + getFamily();
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }
}
