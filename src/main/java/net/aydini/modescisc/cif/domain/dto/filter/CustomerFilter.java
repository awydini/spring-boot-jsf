package net.aydini.modescisc.cif.domain.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 13, 2020
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerFilter  implements Filter{

    /**
     * 
     */
    private static final long serialVersionUID = -4243068302231820647L;

    private String name;

    private String family;

    private String nationalCode;

}