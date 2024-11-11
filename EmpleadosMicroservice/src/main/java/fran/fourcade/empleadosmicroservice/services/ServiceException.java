package fran.fourcade.empleadosmicroservice.services;

public class ServiceException extends Throwable {
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
