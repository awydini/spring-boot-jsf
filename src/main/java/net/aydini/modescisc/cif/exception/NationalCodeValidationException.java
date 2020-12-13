package net.aydini.modescisc.cif.exception;
/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

public class NationalCodeValidationException extends CustomerValidationException {

    /**
     * 
     */
    private static final long serialVersionUID = -1438190518855240279L;

    public static final String FIELD_IS_NOT_VALID="field.is.not.valid.error";

    public static final String NATIONAL_CODE_IS_NOT_NUMERIC="cif.nationalCode.is.not.numeric";

    public static final String NATIONAL_CODE_IS_DUPLICATED="cif.nationalCode.is.duplicated";

    public static final String NATIONAL_CODE_FORMAT_ERROR="cif.nationalCode.format.error";

    public static final String NATIONAL_CODE_LENGTH_ERROR="cif.nationalCode.length.error";



    public NationalCodeValidationException(String message, Object[] args, Throwable cause) {
        super(message,args, cause);
    }

    public NationalCodeValidationException(String message, Object[] args) {
        super(message,args);
    }

    public NationalCodeValidationException(Throwable cause) {
        super(cause);
    }
}