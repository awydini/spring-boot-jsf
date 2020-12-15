package net.aydini.modescisc.cif.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.aydini.modescisc.cif.domain.Gender;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto
{

    private String name;

    private String family;

    private String nationalCode;

    private Gender gender;

    private Date birthDate;

}
