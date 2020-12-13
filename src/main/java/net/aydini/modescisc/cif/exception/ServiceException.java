package net.aydini.modescisc.cif.exception;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -9213806001418976345L;
    
    
    private Object[] args;
    public ServiceException() {
    }

    public ServiceException(String message,Object[] args, Throwable cause) {
        super(message, cause);
        this.args=args;
    }

    public ServiceException(String message,Object[] args)
    {
        super(message);
        this.args=args;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public Object[] getArgs() {
        return args;
    }
}