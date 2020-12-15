package net.aydini.modescisc.cif.domain.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 15, 2020
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerFileFilter  implements Filter{


    /**
     * 
     */
    private static final long serialVersionUID = -8223468824238649442L;

    private String fileName;

}