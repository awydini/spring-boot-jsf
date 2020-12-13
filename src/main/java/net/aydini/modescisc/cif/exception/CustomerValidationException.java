package net.aydini.modescisc.cif.exception;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class CustomerValidationException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 734863417345203315L;
    
    public static final String FIELD_IS_NOT_VALID="field.is.not.valid.error";


    public CustomerValidationException(String message,Object[] args, Throwable cause) {
        super(message,args, cause);
    }

    public CustomerValidationException(String message,Object[] args) {
        super(message,args);
    }

    public CustomerValidationException(Throwable cause) {
        super(cause);
    }
}