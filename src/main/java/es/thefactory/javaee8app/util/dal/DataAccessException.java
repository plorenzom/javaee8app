package es.thefactory.javaee8app.util.dal;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
public class DataAccessException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param cause
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
